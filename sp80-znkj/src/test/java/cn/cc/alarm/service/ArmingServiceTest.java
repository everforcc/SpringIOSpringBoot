package cn.cc.alarm.service;

import cn.cc.alarm.ArmingKeys;
import cn.cc.alarm.TimeSlotUtils;
import cn.cc.alarm.bitmap.Bitmaps;
import cn.cc.alarm.repo.ArmingRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.BitSet;

/**
 * 基于 ArmingService.defuseToday 的集成测试（真实 MySQL 与 Redis）。
 *
 * 设计思路：
 * - 测试用例直接调用服务层 API（无 Mock），通过真实的 Redis 与 MySQL 验证链路：
 *   upsertGroupSchedule → defuseToday / rearmToday → Redis今日掩码（TTL到午夜）与DB upsert。
 * - 每个用例执行前清理“今日掩码”，避免跨用例串扰。
 * - 通过日志打印关键参数与掩码结果，便于人工确认；本类不追求严苛断言（只在个别边界场景保守断言）。
 *
 * 日志字段说明：
 * - group：布防组ID（每个用例使用不同值，互不影响）
 * - effective：本次操作是否生效
 * - range/next/A/B：操作目标的时间槽位区间
 * - mask：Redis中“今日掩码”的位图（BitSet#toString 格式，仅用于快速观察）
 */
@Slf4j
@SpringBootTest
public class ArmingServiceTest {

	private final ZoneId zone = ZoneId.of("Asia/Shanghai");

	@Resource
	private ArmingService armingService;

	@Resource
	private ArmingRepository repo;

	@Resource
	private RedisTemplate<String, byte[]> redisBytes;

	private void logState(String tag, BitSet dayBits, BitSet mask, int slotNow) {
		boolean plan = dayBits != null && dayBits.get(slotNow);
		boolean masked = mask != null && mask.get(slotNow);
		boolean effective = plan && !masked;
		log.info("{} => 计划(plan)={}, 掩码(mask)={}, 有效(effective)={}", tag, plan, masked, effective);
	}

	private void clearTodayMask(long groupId) {
		redisBytes.delete(ArmingKeys.groupTodayMaskKey(groupId));
		repo.upsertTodayMask(groupId, LocalDate.now(zone), new byte[0]);
	}

	private byte[] buildWeekBitsForToday(BitSet dayBits) {
		int dayIdx = java.time.ZonedDateTime.now(zone).getDayOfWeek().getValue() % 7;
		BitSet week = new BitSet(336);
		int base = dayIdx * 48;
		for (int i = 0; i < 48; i++) if (dayBits.get(i)) week.set(base + i);
		return week.toByteArray();
	}

	private BitSet readMaskFromRedis(long groupId) {
		byte[] raw = redisBytes.opsForValue().get(ArmingKeys.groupTodayMaskKey(groupId));
		return Bitmaps.fromBytes(raw);
	}

	// 将 BitSet 的置位区间格式化为 [a..b],[c..d] 便于日志查看
	private static String formatRanges(BitSet bits, int start, int end) {
		if (bits == null) return "[]";
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		int i = start;
		while (i <= end) {
			if (bits.get(i)) {
				int j = i;
				while (j + 1 <= end && bits.get(j + 1)) j++;
				if (!first) sb.append(",");
				sb.append("[").append(i).append("..").append(j).append("]");
				first = false;
				i = j + 1;
			} else {
				i++;
			}
		}
		return first ? "[]" : sb.toString();
	}

	// 1) 在布防段内撤防
	@Test
	public void testDefuseInArmedPeriod() {
		long groupId = 4001L;
		clearTodayMask(groupId);
		int slotNow = TimeSlotUtils.currentDaySlot(zone);
		int left = Math.max(0, slotNow - 3);
		int right = Math.min(47, slotNow + 3);
		BitSet dayBits = new BitSet(48);
		dayBits.set(left, right + 1);
		logState("状态-撤防前", dayBits, null, slotNow);
		armingService.upsertGroupSchedule(groupId, buildWeekBitsForToday(dayBits));
		boolean effective = armingService.defuseToday(groupId);
		BitSet mask = readMaskFromRedis(groupId);
		log.info("[ArmedPeriod] group={}, effective={}, range=[{}..{}], mask={}", groupId, effective, left, right, mask);
		logState("状态-撤防后", dayBits, mask, slotNow);
	}

	// 2) 在撤防段内撤防
	@Test
	public void testDefuseInDisarmedPeriod() {
		long groupId = 4002L;
		clearTodayMask(groupId);
		int slotNow = TimeSlotUtils.currentDaySlot(zone);
		BitSet dayBits = new BitSet(48);
		int nextL = Math.max(0, Math.min(47, slotNow + 2));
		int nextR = Math.max(nextL, Math.min(47, slotNow + 5));
		if (nextL <= nextR) dayBits.set(nextL, nextR + 1);
		armingService.upsertGroupSchedule(groupId, buildWeekBitsForToday(dayBits));
		boolean effective = armingService.defuseToday(groupId);
		BitSet mask = readMaskFromRedis(groupId);
		log.info("[DisarmedPeriod] group={}, effective={}, next=[{}..{}], mask={}", groupId, effective, nextL, nextR, mask);
	}

	// 3) 布防段内多次撤防（第二次无新增）
	@Test
	public void testMultipleDefuseInArmed() {
		long groupId = 4003L;
		clearTodayMask(groupId);
		int slotNow = TimeSlotUtils.currentDaySlot(zone);
		int L = Math.max(0, slotNow - 3);
		int R = Math.min(47, slotNow + 3);
		BitSet dayBits = new BitSet(48);
		dayBits.set(L, R + 1);
		armingService.upsertGroupSchedule(groupId, buildWeekBitsForToday(dayBits));
		boolean eff1 = armingService.defuseToday(groupId);
		BitSet mask1 = readMaskFromRedis(groupId);
		boolean eff2 = armingService.defuseToday(groupId);
		BitSet mask2 = readMaskFromRedis(groupId);
		log.info("[Armed-Multi-1] group={}, eff={}, range=[{}..{}], mask1={}", groupId, eff1, L, R, mask1);
		log.info("[Armed-Multi-2] group={}, eff={} (expected no-change), mask2={}", groupId, eff2, mask2);
	}

	/**
	 * 4) 撤防段内多次撤防（限制：只能撤防“下一个布防段 A”一次，后续点击无效）
	 *
	 * 测试数据构造：
	 * - 当前槽位“撤防段”（即 dayBits 在当前槽位为 0）
	 * - 设置接下来存在两个布防段：A=[now+2..now+4]、B=[A_R+2..A_R+4]
	 * 预期：
	 * - 第一次点击：撤防 A（有效）
	 * - 第二次点击：无效（不可再撤 B）
	 */
	@Test
	public void testMultipleDefuseInDisarmed() {
		long groupId = 4004L;
		clearTodayMask(groupId);
		int slotNow = TimeSlotUtils.currentDaySlot(zone);
		BitSet dayBits = new BitSet(48); // 当前不布防
		int A_L = Math.max(0, Math.min(47, slotNow + 2));
		int A_R = Math.max(A_L, Math.min(47, A_L + 2));
		int B_L = Math.max(0, Math.min(47, A_R + 2));
		int B_R = Math.max(B_L, Math.min(47, B_L + 2));
		if (A_L <= A_R) dayBits.set(A_L, A_R + 1);
		if (B_L <= B_R) dayBits.set(B_L, B_R + 1);
		armingService.upsertGroupSchedule(groupId, buildWeekBitsForToday(dayBits));
		boolean eff1 = armingService.defuseToday(groupId); // 撤防 A
		BitSet mask1 = readMaskFromRedis(groupId);
		boolean eff2 = armingService.defuseToday(groupId); // 尝试撤防 B（应无效）
		BitSet mask2 = readMaskFromRedis(groupId);
		log.info("[Disarmed-Multi-Limited-1] group={}, eff1={}, A=[{}..{}], mask1={}", groupId, eff1, A_L, A_R, mask1);
		log.info("[Disarmed-Multi-Limited-2] group={}, eff2={} (expected no-change), mask2={}", groupId, eff2, mask2);
	}



	/**
	 * 5) 整日分段用固定槽位验证：
	 * 周一到周日每天：
	 *  - 0-8 布防 [0..15]
	 *  - 8-12 撤防 [16..23]
	 *  - 12-14 布防 [24..27]
	 *  - 14-18 撤防 [28..35]
	 *  - 18-24 布防 [36..47]
	 * 验证顺序：slot=18（撤 A=12-14）、slot=25（无变化）、slot=30（撤 B=18-24）。
	 */
	@Test
	public void testFullDayFixedSlotsScenario() {
		long groupId = 4020L;
		clearTodayMask(groupId);

		// 构造当天周计划位图
		BitSet dayBits = new BitSet(48);
		dayBits.set(0, 16);   // 0-8 布防
		dayBits.set(24, 28);  // 12-14 布防
		dayBits.set(36, 48);  // 18-24 布防
		armingService.upsertGroupSchedule(groupId, buildWeekBitsForToday(dayBits));

		// Step A：在 8-12 撤防段点击撤防（slot=18 → 9:00）→ 应撤 12-14
		log.info("Step A：在 8-12 撤防段点击撤防（slot=18 → 9:00）→ 应撤 12-14");
		boolean effA1 = armingService.defuseAtSlot(groupId, 18);
		BitSet maskA1 = readMaskFromRedis(groupId);
		log.info("[FullDay-A1] slot=18 eff={} maskRanges={}", effA1, formatRanges(maskA1, 0, 47));
		boolean effA2 = armingService.defuseAtSlot(groupId, 18);
		BitSet maskA2 = readMaskFromRedis(groupId);
		log.info("[FullDay-A2] slot=18 eff={} (expected false) maskRanges={}", effA2, formatRanges(maskA2, 0, 47));

		// Step B：在 12-14 段点击撤防（slot=25 → 12:30）→ 已被撤防，仍无变化
		log.info("Step B：在 12-14 段点击撤防（slot=25 → 12:30）→ 已被撤防，仍无变化");
		boolean effB1 = armingService.defuseAtSlot(groupId, 25);
		BitSet maskB1 = readMaskFromRedis(groupId);
		log.info("[FullDay-B1] slot=25 eff={} (expected false) maskRanges={}", effB1, formatRanges(maskB1, 0, 47));

		// Step C：在 14-18 撤防段点击撤防（slot=30 → 15:00）→ 应撤 18-24
		log.info("Step C：在 14-18 撤防段点击撤防（slot=30 → 15:00）→ 应撤 18-24");
		boolean effC1 = armingService.defuseAtSlot(groupId, 30);
		BitSet maskC1 = readMaskFromRedis(groupId);
		log.info("[FullDay-C1] slot=30 eff={} maskRanges={}", effC1, formatRanges(maskC1, 0, 47));
		boolean effC2 = armingService.defuseAtSlot(groupId, 30);
		BitSet maskC2 = readMaskFromRedis(groupId);
		log.info("[FullDay-C2] slot=30 eff={} (expected false) maskRanges={}", effC2, formatRanges(maskC2, 0, 47));
	}

	/**
	 * Rearm 1) 0-8撤防后，在0-8内点击“布防” → 该时间段重新布防
	 * 2. 0-8撤防后，多次撤防，只有第一次成功
	 * 3. 0-8撤防后，多次布防，只有第一次成功
	 *
	 */
	@Test
	public void testRearmWithinSameArmedSegment() {
		long groupId = 4011L;
		clearTodayMask(groupId);
		int slotNow1 = 7;
		int slotNow2 = 8;
		int slotNow3 = 9;
		int slotNow4 = 10;
		BitSet dayBits = new BitSet(48);
		dayBits.set(0, 16);   // 0-8 布防
		dayBits.set(24, 28);  // 12-14 布防
		dayBits.set(36, 48);  // 18-24 布防

		// 初始化数据
		armingService.upsertGroupSchedule(groupId, buildWeekBitsForToday(dayBits));

		// defuse 撤防 rearm 布防
		// 撤防
		boolean cf1 = armingService.defuseAtSlot(groupId, slotNow1);
		BitSet maskAfterDefuse = readMaskFromRedis(groupId);
		log.info("maskAfterDefuse1: {}", maskAfterDefuse);
		boolean cf2 = armingService.defuseAtSlot(groupId, slotNow2);

		maskAfterDefuse = readMaskFromRedis(groupId);
		log.info("maskAfterDefuse2: {}", maskAfterDefuse);
		log.info("撤防1: {}, 撤防2: {}", cf1, cf2);

		// 布防
		boolean rearmed = armingService.rearmAtSlot(groupId, slotNow3);
		// 这个地方读的是掩码，所有没有问题
		BitSet maskAfterRearm = readMaskFromRedis(groupId);

		log.info("maskAfterRearm掩码: {}", maskAfterRearm);
		boolean rearmed2 = armingService.rearmAtSlot(groupId, slotNow3);

		maskAfterRearm = readMaskFromRedis(groupId);
		log.info("maskAfterRearm掩码: {}", maskAfterRearm);
		log.info("布防1: {}, 布防2: {}", rearmed, rearmed2);

		log.info("[Rearm-Within] group={}, defuseMask={}, rearmed布防={}, finalMask掩码={}", groupId, maskAfterDefuse, rearmed, maskAfterRearm);
		// 改用区间打印
		log.info("[Rearm-Within] group={}, defuseMaskRanges={}, rearmed布防={}, finalMaskRanges={}", groupId, formatRanges(maskAfterDefuse, 0, 47), rearmed, formatRanges(maskAfterRearm, 0, 47));

		log.info("重新撤防");
		boolean cf3 = armingService.defuseAtSlot(groupId, slotNow1);
		BitSet maskAfterDefuse3 = readMaskFromRedis(groupId);
		log.info("撤防3: {}, maskAfterDefuse3: {}", cf3, maskAfterDefuse3);

		log.info("重新布防");
		boolean rearmed3 = armingService.rearmAtSlot(groupId, slotNow3);
		BitSet maskAfterRearm3 = readMaskFromRedis(groupId);
		log.info("布防3: {}, maskAfterRearm3掩码: {}", rearmed3, maskAfterRearm3);

	}

	/**
	 * Rearm 2) 0-8撤防后，8点以后点击“布防” → 应无变化
	 */
	@Test
	public void testRearmOutsideSegmentNoChange() {
		long groupId = 4012L;
		clearTodayMask(groupId);
		int slotNow1 = 7;
		int slotNow2 = 17;
//		int slotNow3 = 9;
//		int slotNow4 = 10;
		BitSet dayBits = new BitSet(48);
		dayBits.set(0, 16);   // 0-8 布防
		dayBits.set(24, 28);  // 12-14 布防
		dayBits.set(36, 48);  // 18-24 布防
		// 初始化数据
		armingService.upsertGroupSchedule(groupId, buildWeekBitsForToday(dayBits));

		boolean cf1 = armingService.defuseAtSlot(groupId, slotNow1);
		BitSet maskAfterDefuse = readMaskFromRedis(groupId);
		log.info("撤防结果: {}, 掩码: {}", cf1, maskAfterDefuse);

		boolean rearmed = armingService.rearmAtSlot(groupId, slotNow2);
		BitSet maskAfterRearm = readMaskFromRedis(groupId);
		log.info("布防结果: {}, 掩码: {}", rearmed, maskAfterRearm);

		log.info("[Rearm-Outside] group={}, defuseMask={}, rearmed={}, finalMask={}", groupId, maskAfterDefuse, rearmed, maskAfterRearm);
	}

	/**
	 * Rearm 3) 8-12/12-14撤防，12-14已撤防；在8-12或12-14内点击“布防”，则12-14重新布防
	 * 3.1 8-12撤防，12-14已撤防;8-12“布防”，则12-14重新布防
	 * 3.2 8-12撤防，12-14已撤防;12-14“布防”，则12-14重新布防
	 * 3.3 12-14撤防，12-14已撤防;12-14“布防”，则12-14重新布防
	 * 3.4 没有这个场景，12-14撤防，12-14已撤防;8-12“布防”，则12-14重新布防
	 */
	@Test
	public void testRearmNextArmedSegmentFromEitherPeriod() {
		for(int i = 1; i < 4; i++) {

			int slotNow1 ;
			int slotNow2 ;
			if(1 == i){
				slotNow1 = 20;
				slotNow2 = 21;
			}else if(2 == i){
				slotNow1 = 20;
				slotNow2 = 26;
			}else {
				slotNow1 = 25;
				slotNow2 = 26;
			}

			long groupId = 4013L;
			clearTodayMask(groupId);

			BitSet dayBits = new BitSet(48);
			dayBits.set(0, 16);   // 0-8 布防
			dayBits.set(24, 28);  // 12-14 布防
			dayBits.set(36, 48);  // 18-24 布防

			// 初始化数据
			armingService.upsertGroupSchedule(groupId, buildWeekBitsForToday(dayBits));

			log.info("时间插槽: [{}, {}]", slotNow1/2, slotNow2/2);
			// 撤防
			boolean cf1 = armingService.defuseAtSlot(groupId, slotNow1);
			BitSet maskAfterDefuse = readMaskFromRedis(groupId);
			log.info("撤防结果{}: {}, 掩码: {}", i, cf1, maskAfterDefuse);

			// 布防
			boolean rearmed = armingService.rearmAtSlot(groupId, slotNow2);
			BitSet maskAfterRearm = readMaskFromRedis(groupId);
			log.info("布防结果{}: {}, 掩码: {}", i, rearmed, maskAfterRearm);

			log.info("[Rearm-Next] group={}, defuseMask={}, rearmed={}, finalMask={}", groupId, maskAfterDefuse, rearmed, maskAfterRearm);
		}
	}
}
