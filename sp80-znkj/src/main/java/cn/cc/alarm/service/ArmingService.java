package cn.cc.alarm.service;

import cn.cc.alarm.ArmingKeys;
import cn.cc.alarm.TimeSlotUtils;
import cn.cc.alarm.bitmap.Bitmaps;
import cn.cc.alarm.repo.ArmingRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZoneId;
import java.util.BitSet;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 布防判定与撤防服务。
 *
 * 设计要点（为什么这么做）：
 * 1) 判定路径只做 O(1) 内存位操作：使用“周位图(336位) + 今日撤防掩码(48位)”合并得出结果；
 * 2) 热路径尽量命中本地缓存（设备→组、组周位图、今日掩码），未命中时退回 Redis 并回填；
 * 3) 今日撤防掩码带 TTL（到午夜），多次撤防做并集，第二天自动恢复周计划；
 * 4) 配置变更走写通：先写 MySQL，再写 Redis，同时刷新本地缓存，保证读路径稳定且低延迟。
 *
 * 重要约束：
 * - 时区统一使用 Asia/Shanghai；
 * - 周位图字节序与 java.util.BitSet 的 toByteArray()/valueOf() 一致；
 * - Redis 键规范见 ArmingKeys；
 * - 设备仅能归属一个布防组；
 * - 若读取链路均未命中（极端情况），保守返回不布防(false) 可根据业务需要调整。
 */
@Service
public class ArmingService {

	private final RedisTemplate<String, byte[]> redisBytes;
	private final RedisTemplate<String, String> redisString;
	private final ArmingRepository repo;

	private final ConcurrentHashMap<Long, Integer> deviceToGroupLocal = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<Integer, BitSet> groupWeekLocal = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<Integer, BitSet> groupTodayMaskLocal = new ConcurrentHashMap<>();

	private final ZoneId zone = ZoneId.of("Asia/Shanghai");

	public ArmingService(RedisTemplate<String, byte[]> redisBytes,
						 RedisTemplate<String, String> redisString,
						 ArmingRepository repo) {
		this.redisBytes = redisBytes;
		this.redisString = redisString;
		this.repo = repo;
	}

	/**
	 * 判定设备的事件是否需要上报。
	 *
	 * 输入：设备ID
	 * 输出：true=当前处于“有效布防”状态；false=撤防或未知
	 *
	 * 判定步骤：
	 * 1) 通过本地缓存/Redis/DB 获取 deviceId 对应的 groupId；
	 * 2) 按当前时间计算 daySlot(0..47) 与 weekOffset(0..335)；
	 * 3) 读取组的周位图 weekBits[weekOffset]；若为0，则直接返回 false；
	 * 4) 读取今日掩码 todayMask[daySlot]；若为1，则返回 false；否则 true。
	 */
	public boolean shouldReport(long deviceId) {
		Integer gid = deviceToGroupLocal.get(deviceId);
		if (gid == null) {
			gid = readDeviceGroup(deviceId);
			if (gid == null) {
				return false;
			}
			deviceToGroupLocal.put(deviceId, gid);
		}

		BitSet weekBits = groupWeekLocal.computeIfAbsent(gid, this::loadWeekBits);
		if (weekBits == null) {
			return false;
		}

		int daySlot = TimeSlotUtils.currentDaySlot(zone);
		int weekOffset = TimeSlotUtils.currentWeekOffset(zone);
		boolean baseArmed = weekBits.get(weekOffset);
		if (!baseArmed) {
			return false;
		}

		BitSet todayMask = groupTodayMaskLocal.computeIfAbsent(gid, this::loadTodayMask);
		return todayMask == null || !todayMask.get(daySlot);
	}

	/**
	 * 按“今日规则”执行撤防：
	 * - 若当前在布防段：撤防包含当前槽位的连续布防区间；
	 * - 若当前在撤防段：撤防今天的下一个连续布防区间；
	 * 生成的掩码与 Redis 中既有掩码做并集（OR），TTL 设置为“距午夜剩余时长”。
	 *
	 * 输入：groupId
	 * 输出：无（副作用更新 Redis 与本地缓存）
	 */
	public boolean defuseToday(long groupId) {
		int dayIdx = java.time.ZonedDateTime.now(zone).getDayOfWeek().getValue() % 7;
		int daySlotNow = TimeSlotUtils.currentDaySlot(zone);
		BitSet weekBits = groupWeekLocal.computeIfAbsent((int) groupId, this::loadWeekBits);
		BitSet dayBits = Bitmaps.sliceDay(weekBits, dayIdx);
		BitSet mask = Bitmaps.buildTodayDefenceMask(dayBits, daySlotNow);

		String key = ArmingKeys.groupTodayMaskKey(groupId);
		byte[] existed = redisBytes.opsForValue().get(key);
		BitSet existedMask = Bitmaps.fromBytes(existed);

		// 若计算得到的掩码在已有掩码内，则说明本次操作无效（同一布防段重复撤防）
		BitSet toAdd = (BitSet) mask.clone();
		toAdd.andNot(existedMask);
		boolean changed = !toAdd.isEmpty();
		if (!changed) {
			return false;
		}
		existedMask.or(mask);
		Duration ttl = TimeSlotUtils.durationUntilMidnight(zone);
		redisBytes.opsForValue().set(key, Bitmaps.toBytes(existedMask), ttl);

		groupTodayMaskLocal.merge((int) groupId, existedMask, (oldV, newV) -> {
			oldV.or(newV);
			return oldV;
		});

		// 持久化今日掩码到 MySQL（用于 Redis 宕机后的恢复方案）
		java.time.LocalDate today = java.time.ZonedDateTime.now(zone).toLocalDate();
		repo.upsertTodayMask(groupId, today, Bitmaps.toBytes(existedMask));
		return true;
	}

	/**
	 * 写通：更新/新增 设备→组 归属。
	 * 流程：MySQL upsert → Redis HSET → 刷新本地缓存。
	 *
	 * 作用：保证读路径可立即命中最新映射，减少外部一致性依赖。
	 */
	public void upsertDeviceGroup(long deviceId, long groupId) {
		repo.upsertDeviceGroup(deviceId, groupId);
		redisString.<String, String>opsForHash().put(ArmingKeys.deviceGroupKey(), String.valueOf(deviceId), String.valueOf(groupId));
		deviceToGroupLocal.put(deviceId, (int) groupId);
	}

	/**
	 * 写通：更新/新增 组周位图（42字节）。
	 * 流程：MySQL upsert → Redis SET → 刷新本地缓存。
	 *
	 * 注意：调用方应确保 weekBits 长度为 42 字节（或 BitSet 序列化后大小满足336位需求）。
	 */
	public void upsertGroupSchedule(long groupId, byte[] weekBits) {
		repo.upsertGroupSchedule(groupId, weekBits);
		redisBytes.opsForValue().set(ArmingKeys.groupWeekBitmapKey(groupId), weekBits);
		groupWeekLocal.put((int) groupId, Bitmaps.fromBytes(weekBits));
	}

	/**
	 * 读取设备归属组：优先 Redis Hash，其次 DB；若命中 DB 则回填 Redis。
	 */
	private Integer readDeviceGroup(long deviceId) {
		String gidStr = redisString.<String, String>opsForHash().get(ArmingKeys.deviceGroupKey(), String.valueOf(deviceId));
		if (gidStr != null) {
			return Integer.parseInt(gidStr);
		}
		Optional<Long> fromDb = repo.findGroupIdByDeviceId(deviceId);
		fromDb.ifPresent(v -> redisString.<String, String>opsForHash().put(ArmingKeys.deviceGroupKey(), String.valueOf(deviceId), String.valueOf(v)));
		return fromDb.map(Long::intValue).orElse(null);
	}

	/**
	 * 加载组周位图：优先 Redis String，其次 DB；若命中 DB 则回填 Redis。
	 */
	private BitSet loadWeekBits(int groupId) {
		byte[] bytes = redisBytes.opsForValue().get(ArmingKeys.groupWeekBitmapKey(groupId));
		if (bytes == null || bytes.length == 0) {
			Optional<byte[]> fromDb = repo.findWeekBitmapByGroupId(groupId);
			fromDb.ifPresent(data -> redisBytes.opsForValue().set(ArmingKeys.groupWeekBitmapKey(groupId), data));
			bytes = fromDb.orElse(null);
		}

		return Bitmaps.fromBytes(bytes);
	}

	/**
	 * 加载今日掩码：仅从 Redis 读取；不存在时视为全0。
	 */
	private BitSet loadTodayMask(int groupId) {
		byte[] bytes = redisBytes.opsForValue().get(ArmingKeys.groupTodayMaskKey(groupId));
		if (bytes == null || bytes.length == 0) {
			// Redis 缺失时，尝试从 MySQL 恢复今日掩码（Redis 宕机后的恢复路径）
			java.time.LocalDate today = java.time.ZonedDateTime.now(zone).toLocalDate();
			byte[] fromDb = repo.findTodayMask(groupId, today);
			if (fromDb != null && fromDb.length > 0) {
				Duration ttl = TimeSlotUtils.durationUntilMidnight(zone);
				redisBytes.opsForValue().set(ArmingKeys.groupTodayMaskKey(groupId), fromDb, ttl);
				return Bitmaps.fromBytes(fromDb);
			}
		}
		return Bitmaps.fromBytes(bytes);
	}

	/**
	 * 查询组的周位图。
	 *
	 * @param groupId 组ID
	 * @param format  返回格式："base64" 或 "01"（长度336的01字符串）。默认 base64
	 * @return 字符串表示的周位图
	 */
	public String getGroupWeekSchedule(long groupId, String format) {
		BitSet bits = groupWeekLocal.computeIfAbsent((int) groupId, this::loadWeekBits);
		if (bits == null) {
			return null;
		}
		if ("01".equalsIgnoreCase(format)) {
			StringBuilder sb = new StringBuilder(336);
			for (int i = 0; i < 336; i++) {
				sb.append(bits.get(i) ? '1' : '0');
			}
			return sb.toString();
		}
		// 默认 base64，长度固定 42 字节
		byte[] raw = bits.toByteArray();
		if (raw == null) {
			raw = new byte[0];
		}
		if (raw.length != 42) {
			byte[] fixed = new byte[42];
			int copy = Math.min(raw.length, 42);
			System.arraycopy(raw, 0, fixed, 0, copy);
			raw = fixed;
		}
		return java.util.Base64.getEncoder().encodeToString(raw);
	}
}


