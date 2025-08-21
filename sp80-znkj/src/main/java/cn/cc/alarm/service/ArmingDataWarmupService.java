package cn.cc.alarm.service;

import cn.cc.alarm.ArmingKeys;
import cn.cc.alarm.repo.ArmingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import cn.cc.alarm.mapper.ArmingMapper;

/**
 * 布防系统数据预热服务
 * 
 * 功能说明：
 * - 系统启动时自动预热数据到Redis
 * - 确保Redis中有最新的布防配置数据
 * - 提高系统启动后的查询性能
 * 
 * 预热策略：
 * - 设备组映射：从MySQL加载所有设备组关系
 * - 组周位图：从MySQL加载所有布防组的周计划
 * - 今日掩码：从MySQL加载当日的撤防掩码
 * 
 * 执行时机：
 * - 应用启动完成后自动执行
 * - 避免在启动过程中阻塞其他服务
 */
@Slf4j
@Service
public class ArmingDataWarmupService {

	private final RedisTemplate<String, byte[]> redisBytes;
	private final RedisTemplate<String, String> redisString;
	private final ArmingRepository repo;

	/** 时区设置：统一使用上海时区 */
	private final ZoneId zone = ZoneId.of("Asia/Shanghai");

	/**
	 * 构造函数：注入Redis模板和数据库仓库
	 * 
	 * @param redisBytes 用于存储二进制数据的Redis模板
	 * @param redisString 用于存储字符串数据的Redis模板
	 * @param repo 数据库操作仓库
	 */
	public ArmingDataWarmupService(RedisTemplate<String, byte[]> redisBytes,
								   RedisTemplate<String, String> redisString,
								   ArmingRepository repo) {
		this.redisBytes = redisBytes;
		this.redisString = redisString;
		this.repo = repo;
	}

	/**
	 * 应用启动完成后自动执行数据预热
	 * 
	 * 执行顺序：
	 * 1. 预热设备组映射
	 * 2. 预热组周位图
	 * 3. 预热今日掩码
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void warmupData() {
		log.info("开始执行布防系统数据预热...");
		
		try {
			// 步骤1：预热设备组映射
			warmupDeviceGroupMapping();
			
			// 步骤2：预热组周位图
			warmupGroupWeekBitmaps();
			
			// 步骤3：预热今日掩码
			warmupTodayMasks();
			
			log.info("布防系统数据预热完成");
		} catch (Exception e) {
			log.error("布防系统数据预热失败", e);
		}
	}

	/**
	 * 预热设备组映射数据
	 * 
	 * 预热策略：
	 * - 从MySQL查询所有设备组关系
	 * - 批量写入Redis Hash
	 * - 无过期时间，永久有效
	 */
	private void warmupDeviceGroupMapping() {
		log.info("开始预热设备组映射数据...");
		
		try {
			// 从数据库查询所有设备组关系
			List<ArmingMapper.DeviceGroupMapping> mappings = repo.findAllDeviceGroupMappings();
			
			if (mappings.isEmpty()) {
				log.info("没有找到设备组映射数据，跳过预热");
				return;
			}
			
			// 批量写入Redis Hash
			for (ArmingMapper.DeviceGroupMapping mapping : mappings) {
				redisString.<String, String>opsForHash().put(
					ArmingKeys.deviceGroupKey(),
					String.valueOf(mapping.getDeviceId()),
					String.valueOf(mapping.getGroupId())
				);
			}
			
			log.info("设备组映射数据预热完成，共预热{}条记录", mappings.size());
		} catch (Exception e) {
			log.error("设备组映射数据预热失败", e);
		}
	}

	/**
	 * 预热组周位图数据
	 * 
	 * 预热策略：
	 * - 从MySQL查询所有布防组的周位图
	 * - 批量写入Redis String
	 * - 无过期时间，永久有效
	 */
	private void warmupGroupWeekBitmaps() {
		log.info("开始预热组周位图数据...");
		
		try {
			// 从数据库查询所有布防组的周位图
			List<ArmingMapper.GroupWeekBitmap> bitmaps = repo.findAllGroupWeekBitmaps();
			
			if (bitmaps.isEmpty()) {
				log.info("没有找到组周位图数据，跳过预热");
				return;
			}
			
			// 批量写入Redis String
			for (ArmingMapper.GroupWeekBitmap bitmap : bitmaps) {
				redisBytes.opsForValue().set(
					ArmingKeys.groupWeekBitmapKey(bitmap.getGroupId()),
					bitmap.getWeekBits()
				);
			}
			
			log.info("组周位图数据预热完成，共预热{}条记录", bitmaps.size());
		} catch (Exception e) {
			log.error("组周位图数据预热失败", e);
		}
	}

	/**
	 * 预热今日掩码数据
	 * 
	 * 预热策略：
	 * - 从MySQL查询当日的撤防掩码
	 * - 写入Redis String，设置TTL到午夜
	 * - 只预热当天的数据
	 */
	private void warmupTodayMasks() {
		log.info("开始预热今日掩码数据...");
		
		try {
			LocalDate today = ZonedDateTime.now(zone).toLocalDate();
			
			// 从数据库查询当日的撤防掩码
			List<ArmingMapper.GroupTodayMask> masks = repo.findTodayMasks(today);
			
			if (masks.isEmpty()) {
				log.info("没有找到今日掩码数据，跳过预热");
				return;
			}
			
			// 计算到午夜的剩余时间，作为TTL
			Duration ttl = calculateTimeUntilMidnight();
			
			// 批量写入Redis String，设置TTL
			for (ArmingMapper.GroupTodayMask mask : masks) {
				redisBytes.opsForValue().set(
					ArmingKeys.groupTodayMaskKey(mask.getGroupId()),
					mask.getMaskBits(),
					ttl
				);
			}
			
			log.info("今日掩码数据预热完成，共预热{}条记录", masks.size());
		} catch (Exception e) {
			log.error("今日掩码数据预热失败", e);
		}
	}

	/**
	 * 计算到午夜的剩余时间
	 * 
	 * @return 到午夜的剩余时长
	 */
	private Duration calculateTimeUntilMidnight() {
		ZonedDateTime now = ZonedDateTime.now(zone);
		ZonedDateTime tomorrowStart = now.toLocalDate().plusDays(1).atStartOfDay(zone);
		return Duration.between(now, tomorrowStart);
	}
}
