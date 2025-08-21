package cn.cc.alarm.service;

import cn.cc.alarm.ArmingKeys;
import cn.cc.alarm.TimeSlotUtils;
import cn.cc.alarm.bitmap.Bitmaps;
import cn.cc.alarm.repo.ArmingRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.BitSet;
import java.util.Optional;

/**
 * 布防判定与撤防服务。
 *
 * 设计要点（为什么这么做）：
 * 1) 判定路径只做 O(1) 内存位操作：使用"周位图(336位) + 今日撤防掩码(48位)"合并得出结果；
 * 2) 热路径尽量命中Redis缓存（设备→组、组周位图、今日掩码），未命中时退回DB并回填Redis；
 * 3) 今日撤防掩码带 TTL（到午夜），多次撤防做并集，第二天自动恢复周计划；
 * 4) 配置变更走写通：先写 MySQL，再写 Redis，保证读路径稳定且低延迟。
 *
 * 重要约束：
 * - 时区统一使用 Asia/Shanghai；
 * - 周位图字节序与 java.util.BitSet 的 toByteArray()/valueOf() 一致；
 * - Redis 键规范见 ArmingKeys；
 * - 设备仅能归属一个布防组；
 * - 若读取链路均未命中（极端情况），保守返回不布防(false) 可根据业务需要调整。
 * 
 * 缓存策略：
 * - 设备→组映射：Redis Hash，无过期时间
 * - 组周位图：Redis String，无过期时间  
 * - 今日掩码：Redis String，TTL到午夜
 * - 所有缓存都有DB回填机制，确保Redis宕机时仍能正常工作
 */
@Service
public class ArmingService {

	private final RedisTemplate<String, byte[]> redisBytes;
	private final RedisTemplate<String, String> redisString;
	private final ArmingRepository repo;

	/** 时区设置：统一使用上海时区 */
	private final ZoneId zone = ZoneId.of("Asia/Shanghai");

	/**
	 * 构造函数：注入Redis模板和数据库仓库
	 * 
	 * @param redisBytes 用于存储二进制数据的Redis模板（周位图、今日掩码）
	 * @param redisString 用于存储字符串数据的Redis模板（设备组映射）
	 * @param repo 数据库操作仓库
	 */
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
	 * 输出：true=当前处于"有效布防"状态；false=撤防或未知
	 *
	 * 判定步骤：
	 * 1) 通过Redis/DB 获取 deviceId 对应的 groupId；
	 * 2) 按当前时间计算 daySlot(0..47) 与 weekOffset(0..335)；
	 * 3) 读取组的周位图 weekBits[weekOffset]；若为0，则直接返回 false；
	 * 4) 读取今日掩码 todayMask[daySlot]；若为1，则返回 false；否则 true。
	 * 
	 * @param deviceId 设备ID
	 * @return true表示需要上报事件，false表示不需要上报
	 */
	public boolean shouldReport(long deviceId) {
		// 步骤1：获取设备所属的布防组ID
		Integer groupId = readDeviceGroup(deviceId);
		if (groupId == null) {
			// 设备未配置布防组，保守返回false（不上报）
			return false;
		}

		// 步骤2：加载该组的周位图（从Redis或DB）
		BitSet weekBits = loadWeekBits(groupId);
		if (weekBits == null || weekBits.isEmpty()) {
			// 组未配置周位图，保守返回false
			return false;
		}

		// 步骤3：计算当前时间对应的槽位和偏移量
		int daySlot = TimeSlotUtils.currentDaySlot(zone);
		int weekOffset = TimeSlotUtils.currentWeekOffset(zone);
		
		// 步骤4：检查周位图中当前时间是否布防
		boolean baseArmed = weekBits.get(weekOffset);
		if (!baseArmed) {
			// 根据周计划当前时间不布防，直接返回false
			return false;
		}

		// 步骤5：检查今日掩码，看是否被临时撤防
		BitSet todayMask = loadOrRecoverTodayMask(groupId);
		// 如果今日掩码为空或当前槽位未被撤防，则需要上报
		return todayMask == null || !todayMask.get(daySlot);
	}

	/**
	 * 按"今日规则"执行撤防：
	 * - 若当前在布防段：撤防包含当前槽位的连续布防区间；
	 * - 若当前在撤防段：撤防今天的下一个连续布防区间；
	 * 生成的掩码与 Redis 中既有掩码做并集（OR），TTL 设置为"距午夜剩余时长"。
	 *
	 * 输入：groupId
	 * 输出：true=本次撤防有效，false=同一布防段内重复撤防无效
	 * 
	 * @param groupId 布防组ID
	 * @return true表示撤防操作有效，false表示重复撤防无效
	 */
	public boolean defuseToday(long groupId) {
		// 步骤1：获取当前是周几（0=周一，6=周日）
		int dayIdx = ZonedDateTime.now(zone).getDayOfWeek().getValue() % 7;
		
		// 步骤2：获取当前时间槽位
		int daySlotNow = TimeSlotUtils.currentDaySlot(zone);
		
		// 步骤3：加载该组的周位图
		BitSet weekBits = loadWeekBits((int) groupId);
		if (weekBits == null || weekBits.isEmpty()) {
			// 组未配置周位图，无法撤防
			return false;
		}
		
		// 步骤4：从周位图中提取今天的48个槽位
		BitSet dayBits = Bitmaps.sliceDay(weekBits, dayIdx);
		
		// 步骤 5.6. 获取Redis或mysql中的今日掩码键
		BitSet existedMask = loadOrRecoverTodayMask(groupId);

		// 额外规则（产品变更）：若当前处于“撤防段”（实际不布防），则当日仅允许撤防“下一个布防段”一次。
		// 实现：若在当前或未来槽位已存在掩码位，则认为今天的“撤防段撤防”机会已用过，直接返回 false。
		boolean nowEffectiveArmed = dayBits.get(daySlotNow) && !existedMask.get(daySlotNow);
		if (!nowEffectiveArmed) {
			// 如果返回-1，说明下个阶段没有被撤防过，如果不是-1，则认为有已撤防的槽位，返回false
			int nextMasked = existedMask.nextSetBit(daySlotNow);
			if (nextMasked != -1) {
				return false;
			}
		}
		
		// 步骤7：根据当前时间、今日布防计划和现有掩码，构建撤防掩码
		// 重要改进：传入现有掩码，确保撤防逻辑的正确性
		BitSet mask = Bitmaps.buildTodayDefenceMask(dayBits, daySlotNow, existedMask);

		// 步骤8：计算新增的撤防掩码（与现有掩码的差集）
		BitSet toAdd = (BitSet) mask.clone();
		// 计算差集：计算新增的撤防位
		toAdd.andNot(existedMask);
		
		// 步骤9：判断本次撤防是否有效（是否有新增的撤防槽位）
		boolean changed = !toAdd.isEmpty();
		if (!changed) {
			// 没有新增撤防槽位，说明是重复撤防，返回false
			return false;
		}
		
		// 步骤10：将新掩码与现有掩码合并
		/**
		 * existedMask: 1 1 0 0 0 0 0 0 (已撤防前2个槽位)
		 * mask:        0 0 1 1 0 0 0 0 (新撤防中间2个槽位)
		 * 执行or后:
		 * existedMask: 1 1 1 1 0 0 0 0 (合并后撤防前4个槽位)
		 */
		existedMask.or(mask);
		
		// 步骤11：计算到午夜的剩余时间，作为TTL
		Duration ttl = TimeSlotUtils.durationUntilMidnight(zone);
		
		// 步骤12：将合并后的掩码写入Redis，设置TTL
		redisBytes.opsForValue().set(ArmingKeys.groupTodayMaskKey(groupId), Bitmaps.toBytes(existedMask), ttl);

		// 步骤13：持久化今日掩码到MySQL（用于Redis宕机后的恢复）
		// 注意：由于表中有唯一约束uk_gid_date(group_id, biz_date)，
		// 每次撤防都会更新同一条记录，不会产生重复数据
		LocalDate today = ZonedDateTime.now(zone).toLocalDate();
		// 新增，如果有了就更新
		repo.upsertTodayMask(groupId, today, Bitmaps.toBytes(existedMask));
		
		return true;
	}

	/**
	 * 指定当日槽位执行撤防（测试/联调用）。
	 *
	 * 语义同 {@link #defuseToday(long)}，唯一区别是由调用方指定 daySlot。
	 * 仅作用于“今天”的掩码。
	 *
	 * @param groupId 组ID
	 * @param daySlot 0..47 半小时槽
	 */
	public boolean defuseAtSlot(long groupId, int daySlot) {
		int dayIdx = ZonedDateTime.now(zone).getDayOfWeek().getValue() % 7;
		BitSet weekBits = loadWeekBits((int) groupId);
		if (weekBits == null || weekBits.isEmpty()) {
			return false;
		}
		BitSet dayBits = Bitmaps.sliceDay(weekBits, dayIdx);
		BitSet existedMask = loadOrRecoverTodayMask(groupId);
		boolean nowEffectiveArmed = dayBits.get(daySlot) && !existedMask.get(daySlot);
		if (!nowEffectiveArmed) {
			int nextMasked = existedMask.nextSetBit(daySlot);
			if (nextMasked != -1) {
				return false;
			}
		}
		BitSet mask = Bitmaps.buildTodayDefenceMask(dayBits, daySlot, existedMask);
		BitSet toAdd = (BitSet) mask.clone();
		toAdd.andNot(existedMask);
		if (toAdd.isEmpty()) {
			return false;
		}
		existedMask.or(mask);
		Duration ttl = TimeSlotUtils.durationUntilMidnight(zone);
		redisBytes.opsForValue().set(ArmingKeys.groupTodayMaskKey(groupId), Bitmaps.toBytes(existedMask), ttl);
		LocalDate today = ZonedDateTime.now(zone).toLocalDate();
		repo.upsertTodayMask(groupId, today, Bitmaps.toBytes(existedMask));
		return true;
	}

	/**
	 * 当日重新布防（撤销今日掩码中一段撤防）：
	 *
	 * 规则说明：
	 * - 若当前处于布防段：恢复包含当前槽位的“连续布防区间”；
	 * - 若当前处于撤防段：恢复“今天的下一个连续布防区间”（与撤防逻辑的目标区间一致）；
	 * - 仅对“今日掩码”中已置位的槽位生效，若目标区间在掩码中没有置位，则认为本次无效（返回 false）。
	 * - 成功后将目标区间对应位从掩码中清除，并将结果写回 Redis（TTL 至午夜）与 MySQL（用于容灾恢复）。
	 *
	 * 场景举例：
	 * 1) 0-8 段撤防后，在 0-8 内点击“布防”，则 0-8 重新布防；
	 * 2) 0-8 段撤防后，8 点以后点击“布防”，不生效（目标区间与已撤防区间不交集）；
	 * 3) 若 12-14 段被撤防，则在 8-12（撤防段）或 12-14（布防段）点击“布防”，都能将 12-14 恢复布防。
	 *
	 * @param groupId 布防组ID
	 * @return true 表示本次“重新布防”生效（掩码有清除），false 表示掩码未发生变化
	 */
	public boolean rearmToday(long groupId) {
		// 计算当日与时间槽
		int dayIdx = ZonedDateTime.now(zone).getDayOfWeek().getValue() % 7;
		int daySlotNow = TimeSlotUtils.currentDaySlot(zone);

		// 读取周位图与今日掩码
		BitSet weekBits = loadWeekBits((int) groupId);
		if (weekBits == null || weekBits.isEmpty()) {
			return false;
		}
		BitSet dayBits = Bitmaps.sliceDay(weekBits, dayIdx);
		BitSet existedMask = loadOrRecoverTodayMask(groupId);

		// 计算“目标恢复区间”位图：忽略现有掩码，仅按周计划选择当前段或下一个连续布防段
		BitSet target = Bitmaps.buildTodayDefenceMask(dayBits, daySlotNow);
		if (target.isEmpty()) {
			return false;
		}

		// 仅清除今日掩码中已置位的交集部分
		BitSet toRemove = (BitSet) target.clone();
		toRemove.and(existedMask);
		if (toRemove.isEmpty()) {
			// 目标区间与掩码无交集 → 本次“重新布防”无效
			return false;
		}

		// 清除目标区间对应位
		existedMask.andNot(target);

		// 写回 Redis（TTL 至午夜）
		Duration ttl = TimeSlotUtils.durationUntilMidnight(zone);
		redisBytes.opsForValue().set(ArmingKeys.groupTodayMaskKey(groupId), Bitmaps.toBytes(existedMask), ttl);

		// 持久化至 MySQL（用于容灾恢复）
		LocalDate today = ZonedDateTime.now(zone).toLocalDate();
		repo.upsertTodayMask(groupId, today, Bitmaps.toBytes(existedMask));

		return true;
	}

	/**
	 * 指定当日槽位执行"重新布防"（测试/联调用）。
	 * 
	 * 语义同 {@link #rearmToday(long)}，唯一区别是由调用方指定 daySlot。
	 * 
	 * 重新布防的作用是撤销之前在特定时间段内的撤防操作，即恢复该时间段的布防状态。
	 * 只有当目标时间段在今日掩码中已被标记为撤防时，重新布防操作才有效。
	 *
	 * @param groupId 组ID
	 * @param daySlot 0..47 半小时槽，表示一天中的时间段（每半小时一个槽位）
	 * @return true 表示本次"重新布防"生效（掩码有清除），false 表示掩码未发生变化
	 */
	public boolean rearmAtSlot(long groupId, int daySlot) {
		// 获取当前是周几（0=周一，6=周日）
		int dayIdx = ZonedDateTime.now(zone).getDayOfWeek().getValue() % 7;
		
		// 加载该组的周位图（包含一周7天的布防计划）
		BitSet weekBits = loadWeekBits((int) groupId);
		if (weekBits == null || weekBits.isEmpty()) {
			// 组未配置周位图，无法进行重新布防操作
			return false;
		}
		
		// 从周位图中提取今天的48个槽位的布防计划
		BitSet dayBits = Bitmaps.sliceDay(weekBits, dayIdx);
		
		// 获取Redis或mysql中的今日掩码（记录了今天哪些时间段被撤防）
		BitSet existedMask = loadOrRecoverTodayMask(groupId);
		
		// 构建目标恢复区间的位图：根据当前槽位和今日布防计划，确定需要重新布防的时间段
		// 这个方法会找出包含指定槽位的连续布防区间
		BitSet target = Bitmaps.buildTodayDefenceMask(dayBits, daySlot);
		if (target.isEmpty()) {
			// 目标区间为空，说明指定的槽位不在任何布防时间段内，无法重新布防
			return false;
		}
		
		// 计算需要移除的掩码位：目标区间与现有掩码的交集
		// 只有那些既在目标区间内又在现有掩码中的位才需要被清除
		BitSet toRemove = (BitSet) target.clone();
		toRemove.and(existedMask);
		if (toRemove.isEmpty()) {
			// 目标区间与掩码无交集，说明目标区间并未被撤防，无需重新布防
			return false;
		}
		
		// 从今日掩码中清除目标区间的位，实现重新布防
		// andNot操作会从existedMask中移除target中为1的位
		existedMask.andNot(target);
		
		// 计算到午夜的剩余时间，作为TTL（生存时间）
		Duration ttl = TimeSlotUtils.durationUntilMidnight(zone);
		
		// 将更新后的掩码写入Redis，并设置TTL到午夜
		redisBytes.opsForValue().set(ArmingKeys.groupTodayMaskKey(groupId), Bitmaps.toBytes(existedMask), ttl);
		
		// 持久化今日掩码到MySQL（用于容灾恢复）
		LocalDate today = ZonedDateTime.now(zone).toLocalDate();
		repo.upsertTodayMask(groupId, today, Bitmaps.toBytes(existedMask));
		
		return true;
	}

	/**
	 * 指定当日槽位查询“是否需要上报”（有效布防）。
	 * baseArmed = 周位图(dayIdx*48+daySlot)；
	 * masked = 今日掩码(daySlot)；
	 * 返回 baseArmed && !masked。
	 */
	public boolean isArmedAtSlot(long deviceId, int daySlot) {
		Integer groupId = readDeviceGroup(deviceId);
		if (groupId == null) {
			return false;
		}
		BitSet weekBits = loadWeekBits(groupId);
		if (weekBits == null || weekBits.isEmpty()) {
			return false;
		}
		int dayIdx = ZonedDateTime.now(zone).getDayOfWeek().getValue() % 7;
		int weekOffset = dayIdx * 48 + daySlot;
		boolean baseArmed = weekBits.get(weekOffset);
		if (!baseArmed) {
			return false;
		}
		BitSet todayMask = loadOrRecoverTodayMask(groupId);
		return todayMask == null || !todayMask.get(daySlot);
	}

	/**
	 * 写通：更新/新增 设备→组 归属。
	 * 流程：MySQL upsert → Redis HSET，保证数据一致性。
	 *
	 * 作用：保证读路径可立即命中最新映射，减少外部一致性依赖。
	 * 
	 * @param deviceId 设备ID
	 * @param groupId 布防组ID
	 */
	public void upsertDeviceGroup(long deviceId, long groupId) {
		// 步骤1：先写入MySQL数据库
		repo.upsertDeviceGroup(deviceId, groupId);
		
		// 步骤2：再写入Redis Hash，确保数据一致性
		redisString.<String, String>opsForHash().put(
			ArmingKeys.deviceGroupKey(), 
			String.valueOf(deviceId), 
			String.valueOf(groupId)
		);
	}

	/**
	 * 写通：更新/新增 组周位图（42字节）。
	 * 流程：MySQL upsert → Redis SET，保证数据一致性。
	 *
	 * 注意：调用方应确保 weekBits 长度为 42 字节（或 BitSet 序列化后大小满足336位需求）。
	 * 
	 * @param groupId 布防组ID
	 * @param weekBits 42字节的周位图数据
	 */
	public void upsertGroupSchedule(long groupId, byte[] weekBits) {
		// 步骤1：先写入MySQL数据库
		repo.upsertGroupSchedule(groupId, weekBits);
		
		// 步骤2：再写入Redis，确保数据一致性
		redisBytes.opsForValue().set(ArmingKeys.groupWeekBitmapKey(groupId), weekBits);
	}

	/**
	 * 读取设备归属组：优先 Redis Hash，其次 DB；若命中 DB 则回填 Redis。
	 * 
	 * 缓存策略：
	 * - 优先从Redis Hash中读取
	 * - Redis未命中时从DB读取
	 * - DB命中时回填Redis，避免下次查询
	 * 
	 * @param deviceId 设备ID
	 * @return 布防组ID，如果设备未配置则返回null
	 */
	private Integer readDeviceGroup(long deviceId) {
		// 步骤1：尝试从Redis Hash中读取设备组映射
		String groupIdStr = redisString.<String, String>opsForHash().get(
			ArmingKeys.deviceGroupKey(), 
			String.valueOf(deviceId)
		);
		
		if (groupIdStr != null) {
			// Redis命中，直接返回
			return Integer.parseInt(groupIdStr);
		}
		
		// 步骤2：Redis未命中，从数据库查询
		Optional<Long> fromDb = repo.findGroupIdByDeviceId(deviceId);
		
		if (fromDb.isPresent()) {
			// 数据库命中，回填Redis
			Long groupId = fromDb.get();
			redisString.<String, String>opsForHash().put(
				ArmingKeys.deviceGroupKey(), 
				String.valueOf(deviceId), 
				String.valueOf(groupId)
			);
			return groupId.intValue();
		}
		
		// 步骤3：数据库也未命中，返回null
		return null;
	}

	/**
	 * 加载组周位图：优先 Redis String，其次 DB；若命中 DB 则回填 Redis。
	 * 
	 * 缓存策略：
	 * - 优先从Redis中读取42字节的周位图
	 * - Redis未命中时从DB读取
	 * - DB命中时回填Redis，避免下次查询
	 * 
	 * @param groupId 布防组ID
	 * @return 周位图的BitSet表示，如果组未配置则返回null
	 */
	private BitSet loadWeekBits(int groupId) {
		// 步骤1：尝试从Redis中读取周位图
		byte[] bytes = redisBytes.opsForValue().get(ArmingKeys.groupWeekBitmapKey(groupId));
		
		if (bytes == null || bytes.length == 0) {
			// Redis未命中，从数据库查询
			Optional<byte[]> fromDb = repo.findWeekBitmapByGroupId(groupId);
			
			if (fromDb.isPresent()) {
				// 数据库命中，回填Redis
				byte[] data = fromDb.get();
				redisBytes.opsForValue().set(ArmingKeys.groupWeekBitmapKey(groupId), data);
				bytes = data;
			} else {
				// 数据库也未命中，返回null
				bytes = null;
			}
		}

		// 步骤2：将字节数组转换为BitSet
		return Bitmaps.fromBytes(bytes);
	}

	/**
	 * 加载当日撤防掩码：优先Redis，缺失则从DB恢复并回填Redis（TTL到午夜）。
	 *
	 * 统一读取逻辑：
	 * - Redis命中：直接返回对应BitSet；
	 * - Redis缺失：从MySQL读取当日掩码，若存在则写回Redis并设置TTL；
	 * - 最终返回：若均未命中，则返回空BitSet。
	 *
	 * @param groupId 布防组ID
	 * @return 今日掩码的BitSet表示；不存在时为空BitSet
	 */
	private BitSet loadOrRecoverTodayMask(long groupId) {
		String key = ArmingKeys.groupTodayMaskKey(groupId);
		byte[] bytes = redisBytes.opsForValue().get(key);
		if (bytes == null || bytes.length == 0) {
			// Redis缺失时，尝试从MySQL恢复今日掩码（Redis宕机后的恢复路径）
			LocalDate today = ZonedDateTime.now(zone).toLocalDate();
			byte[] fromDb = repo.findTodayMask(groupId, today);
			if (fromDb != null && fromDb.length > 0) {
				// 从数据库恢复成功，重新写入Redis并设置TTL
				Duration ttl = TimeSlotUtils.durationUntilMidnight(zone);
				redisBytes.opsForValue().set(key, fromDb, ttl);
				bytes = fromDb;
			}
		}
		return Bitmaps.fromBytes(bytes);
	}

	/**
	 * 查询组的周位图。
	 *
	 * 返回格式：
	 * - "base64"：42字节的Base64编码字符串（默认格式）
	 * - "01"：336位的01字符串，便于人工查看
	 *
	 * @param groupId 组ID
	 * @param format 返回格式："base64" 或 "01"（长度336的01字符串）。默认 base64
	 * @return 字符串表示的周位图，如果组未配置则返回null
	 */
	public String getGroupWeekSchedule(long groupId, String format) {
		// 步骤1：加载组的周位图
		BitSet bits = loadWeekBits((int) groupId);
		if (bits == null) {
			return null;
		}
		
		// 步骤2：根据格式要求返回不同形式的字符串
		if ("01".equalsIgnoreCase(format)) {
			// 返回336位的01字符串，便于人工查看和调试
			StringBuilder sb = new StringBuilder(336);
			for (int i = 0; i < 336; i++) {
				sb.append(bits.get(i) ? '1' : '0');
			}
			return sb.toString();
		}
		
		// 默认返回Base64格式，长度固定42字节
		byte[] raw = bits.toByteArray();
		if (raw == null) {
			raw = new byte[0];
		}
		
		// 确保字节数组长度为42字节（336位需要42字节）
		if (raw.length != 42) {
			byte[] fixed = new byte[42];
			int copy = Math.min(raw.length, 42);
			// 将raw处理为42位，截取或填充0
			System.arraycopy(raw, 0, fixed, 0, copy);
			raw = fixed;
		}
		
		// 返回Base64编码的字符串
		return Base64.getEncoder().encodeToString(raw);
	}
}


