package cn.cc.alarm.service;

import cn.cc.alarm.TimeSlotUtils;
import cn.cc.alarm.bitmap.Bitmaps;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 布防撤防服务测试类
 * 
 * 测试重点：
 * - 撤防逻辑的正确性
 * - 多次撤防的场景
 * - 边界情况的处理
 */
public class ArmingServiceTest {

	private final ZoneId zone = ZoneId.of("Asia/Shanghai");

	/**
	 * 测试多次撤防的场景
	 * 
	 * 场景：周计划为"0-8布防，8-12撤防，12-14布防，14-18撤防，18-24布防"
	 * 测试步骤：
	 * 1. 在8:00-12:00期间撤防（撤防12:00-14:00）
	 * 2. 在14:00-18:00期间撤防（撤防18:00-24:00）
	 * 3. 在12:00-14:00期间撤防（应该无效，因为已被撤防）
	 */
	@Test
	public void testMultipleDefuse() {
		// 构造周计划：0-8布防，8-12撤防，12-14布防，14-18撤防，18-24布防
		BitSet dayBits = new BitSet(48);
		// 0-8布防（槽位0-15）
		dayBits.set(0, 16);
		// 12-14布防（槽位24-27）
		dayBits.set(24, 28);
		// 18-24布防（槽位36-47）
		dayBits.set(36, 48);

		// 第一次撤防：在8:00-12:00期间（撤防段）撤防
		// 当前时间：10:00（槽位20）
		int daySlot1 = 20; // 10:00对应的槽位
		BitSet mask1 = Bitmaps.buildTodayDefenceMask(dayBits, daySlot1, null);
		
		// 验证：应该撤防12:00-14:00（槽位24-27）
		assertTrue(mask1.get(24));
		assertTrue(mask1.get(25));
		assertTrue(mask1.get(26));
		assertTrue(mask1.get(27));
		assertFalse(mask1.get(23)); // 11:30不应该被撤防
		assertFalse(mask1.get(28)); // 14:00不应该被撤防

		// 第二次撤防：在14:00-18:00期间（撤防段）撤防
		// 当前时间：16:00（槽位32）
		int daySlot2 = 32; // 16:00对应的槽位
		BitSet mask2 = Bitmaps.buildTodayDefenceMask(dayBits, daySlot2, mask1);
		
		// 验证：应该撤防18:00-24:00（槽位36-47）
		assertTrue(mask2.get(36));
		assertTrue(mask2.get(37));
		assertTrue(mask2.get(46));
		assertTrue(mask2.get(47));
		assertFalse(mask2.get(35)); // 17:30不应该被撤防

		// 第三次撤防：在12:00-14:00期间（已被撤防的布防段）撤防
		// 当前时间：13:00（槽位26）
		int daySlot3 = 26; // 13:00对应的槽位
		BitSet mask3 = Bitmaps.buildTodayDefenceMask(dayBits, daySlot3, mask1);
		
		// 验证：应该返回空掩码，因为该段已被撤防
		assertTrue(mask3.isEmpty());
	}

	/**
	 * 测试在布防段内撤防的场景
	 * 
	 * 场景：在布防段内点击撤防，应该撤防整个连续布防区间
	 */
	@Test
	public void testDefuseInArmedPeriod() {
		// 构造周计划：0-8布防，8-12撤防，12-14布防
		BitSet dayBits = new BitSet(48);
		// 0-8布防（槽位0-15）
		dayBits.set(0, 16);
		// 12-14布防（槽位24-27）
		dayBits.set(24, 28);

		// 在0-8布防段内撤防：当前时间6:00（槽位12）
		int daySlot = 12; // 6:00对应的槽位
		BitSet mask = Bitmaps.buildTodayDefenceMask(dayBits, daySlot, null);
		
		// 验证：应该撤防整个0-8区间（槽位0-15）
		for (int i = 0; i < 16; i++) {
			assertTrue(mask.get(i), "槽位" + i + "应该被撤防");
		}
		assertFalse(mask.get(16)); // 8:00不应该被撤防
	}

	/**
	 * 测试在撤防段内撤防的场景
	 * 
	 * 场景：在撤防段内点击撤防，应该撤防下一个布防段
	 */
	@Test
	public void testDefuseInDisarmedPeriod() {
		// 构造周计划：0-8布防，8-12撤防，12-14布防，14-18撤防，18-24布防
		BitSet dayBits = new BitSet(48);
		// 0-8布防（槽位0-15）
		dayBits.set(0, 16);
		// 12-14布防（槽位24-27）
		dayBits.set(24, 28);
		// 18-24布防（槽位36-47）
		dayBits.set(36, 48);

		// 在8-12撤防段内撤防：当前时间10:00（槽位20）
		int daySlot = 20; // 10:00对应的槽位
		BitSet mask = Bitmaps.buildTodayDefenceMask(dayBits, daySlot, null);
		
		// 验证：应该撤防下一个布防段12-14（槽位24-27）
		assertTrue(mask.get(24));
		assertTrue(mask.get(25));
		assertTrue(mask.get(26));
		assertTrue(mask.get(27));
		assertFalse(mask.get(23)); // 11:30不应该被撤防
		assertFalse(mask.get(28)); // 14:00不应该被撤防
	}

	/**
	 * 测试在已被撤防的布防段内撤防的场景
	 * 
	 * 场景：在已被撤防的布防段内点击撤防，应该返回空掩码
	 */
	@Test
	public void testDefuseInAlreadyDefusedPeriod() {
		// 构造周计划：0-8布防，8-12撤防，12-14布防
		BitSet dayBits = new BitSet(48);
		// 0-8布防（槽位0-15）
		dayBits.set(0, 16);
		// 12-14布防（槽位24-27）
		dayBits.set(24, 28);

		// 构造现有掩码：已撤防12-14区间
		BitSet existingMask = new BitSet(48);
		existingMask.set(24, 28); // 撤防12-14区间

		// 在已被撤防的12-14区间内撤防：当前时间13:00（槽位26）
		int daySlot = 26; // 13:00对应的槽位
		BitSet mask = Bitmaps.buildTodayDefenceMask(dayBits, daySlot, existingMask);
		
		// 验证：应该返回空掩码
		assertTrue(mask.isEmpty());
	}

	/**
	 * 测试边界情况：在最后一个布防段内撤防
	 * 
	 * 场景：在最后一个布防段内点击撤防，应该撤防整个连续区间
	 */
	@Test
	public void testDefuseInLastArmedPeriod() {
		// 构造周计划：0-8布防，8-12撤防，12-14布防，14-18撤防，18-24布防
		BitSet dayBits = new BitSet(48);
		// 0-8布防（槽位0-15）
		dayBits.set(0, 16);
		// 12-14布防（槽位24-27）
		dayBits.set(24, 28);
		// 18-24布防（槽位36-47）
		dayBits.set(36, 48);

		// 在18-24布防段内撤防：当前时间20:00（槽位40）
		int daySlot = 40; // 20:00对应的槽位
		BitSet mask = Bitmaps.buildTodayDefenceMask(dayBits, daySlot, null);
		
		// 验证：应该撤防整个18-24区间（槽位36-47）
		for (int i = 36; i < 48; i++) {
			assertTrue(mask.get(i), "槽位" + i + "应该被撤防");
		}
		assertFalse(mask.get(35)); // 17:30不应该被撤防
	}
}
