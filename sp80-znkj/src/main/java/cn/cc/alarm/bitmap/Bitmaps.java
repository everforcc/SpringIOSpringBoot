package cn.cc.alarm.bitmap;

import java.util.BitSet;

/**
 * 位图工具：
 * - fromBytes/toBytes：与 java.util.BitSet 的二进制互转；
 * - sliceDay：从周位图切出当天 48 槽；
 * - buildTodayDefenceMask：依据"今日撤防规则"构建掩码（支持当前段或下一布防段）。
 *
 * 约定：
 * - 1 表示布防（在周位图）；1 表示"需要撤防"（在今日掩码）。
 * - 掩码将与存量掩码做并集（OR），并设置至午夜的 TTL。
 * 
 * 数据结构说明：
 * - 周位图：336位（7天×48槽位），42字节
 * - 今日掩码：48位（1天×48槽位），6字节
 * - BitSet：Java内置的位集合类，支持位操作
 */
public final class Bitmaps {

	private Bitmaps() {}

	/**
	 * 将二进制字节数组转为 BitSet；null 视为空 BitSet。
	 * 
	 * 用途：
	 * - 从Redis或数据库读取的字节数组转换为BitSet
	 * - 支持null输入，返回空的BitSet
	 * 
	 * @param bytes 字节数组，可以为null
	 * @return BitSet对象，如果输入为null则返回空BitSet
	 */
	public static BitSet fromBytes(byte[] bytes) {
		return bytes == null ? new BitSet() : BitSet.valueOf(bytes);
	}

	/**
	 * 将 BitSet 转为二进制字节数组；null 视为空数组。
	 * 
	 * 用途：
	 * - 将BitSet转换为字节数组以便存储到Redis或数据库
	 * - 支持null输入，返回空数组
	 * 
	 * @param bitSet BitSet对象，可以为null
	 * @return 字节数组，如果输入为null则返回空数组
	 */
	public static byte[] toBytes(BitSet bitSet) {
		return bitSet == null ? new byte[0] : bitSet.toByteArray();
	}

	/**
	 * 从周位图中切出某天的 48 槽位图。
	 * 
	 * 操作说明：
	 * - 从336位的周位图中提取指定天的48个槽位
	 * - 例如：dayIndex=0表示周一，提取槽位0-47
	 * - 例如：dayIndex=1表示周二，提取槽位48-95
	 * 
	 * @param weekBits 336位的周位图
	 * @param dayIndex 天的索引（0=周一，6=周日）
	 * @return 48位的日位图
	 */
	public static BitSet sliceDay(BitSet weekBits, int dayIndex) {
		BitSet day = new BitSet(48);
		int base = dayIndex * 48; // 计算该天在周位图中的起始位置
		
		// 遍历该天的48个槽位
		for (int i = 0; i < 48; i++) {
			if (weekBits.get(base + i)) {
				day.set(i);
			}
		}
		return day;
	}

	/**
	 * 构建今日撤防掩码：
	 * - 若当前处于布防段，则撤防"包含当前槽位的连续布防区间"；
	 * - 若当前处于撤防段，则撤防"今天的下一个连续布防区间"。
	 * 
	 * 撤防规则：
	 * 1. 如果当前在布防段：
	 *    - 找到包含当前槽位的连续布防区间
	 *    - 撤防整个连续区间
	 * 2. 如果当前在撤防段：
	 *    - 找到下一个连续布防区间
	 *    - 撤防该区间
	 * 
	 * 示例：
	 * - 当前在布防段[12:00-14:00]的12:30，撤防整个[12:00-14:00]区间
	 * - 当前在撤防段[14:00-18:00]的15:00，撤防下一个布防段[18:00-24:00]
	 * 
	 * @param dayBits 今天的48个槽位的布防状态
	 * @param daySlotNow 当前时间对应的槽位（0-47）
	 * @return 需要撤防的槽位掩码
	 */
	public static BitSet buildTodayDefenceMask(BitSet dayBits, int daySlotNow) {
		return buildTodayDefenceMask(dayBits, daySlotNow, null);
	}

	/**
	 * 构建今日撤防掩码（考虑现有掩码）：
	 * - 若当前处于布防段，则撤防"包含当前槽位的连续布防区间"；
	 * - 若当前处于撤防段，则撤防"今天的下一个连续布防区间"。
	 * 
	 * 撤防规则：
	 * 1. 如果当前在布防段：
	 *    - 找到包含当前槽位的连续布防区间
	 *    - 撤防整个连续区间
	 * 2. 如果当前在撤防段：
	 *    - 找到下一个连续布防区间
	 *    - 撤防该区间
	 * 
	 * 重要改进：
	 * - 考虑现有掩码的影响，确保撤防逻辑的正确性
	 * - 避免在已被撤防的布防段内重复撤防
	 * 
	 * @param dayBits 今天的48个槽位的布防状态（周计划）
	 * @param daySlotNow 当前时间对应的槽位（0-47）
	 * @param existingMask 现有的今日掩码，可以为null
	 * @return 需要撤防的槽位掩码
	 */
	public static BitSet buildTodayDefenceMask(BitSet dayBits, int daySlotNow, BitSet existingMask) {
		BitSet mask = new BitSet(48);
		
		// 计算当前槽位的实际布防状态（考虑现有掩码的影响）
		boolean nowArmedBySchedule = dayBits.get(daySlotNow); // 周计划中的布防状态
		boolean nowArmedByMask = existingMask != null && existingMask.get(daySlotNow); // 现有掩码中的撤防状态
		boolean nowArmed = nowArmedBySchedule && !nowArmedByMask; // 实际布防状态
		
		if (nowArmed) {
			// 情况1：当前在布防段，撤防包含当前槽位的连续布防区间
			
			// 向左扩展，找到连续布防区间的起始位置
			int left = daySlotNow;
			while (left > 0) {
				boolean leftArmedBySchedule = dayBits.get(left - 1);
				boolean leftArmedByMask = existingMask != null && existingMask.get(left - 1);
				boolean leftArmed = leftArmedBySchedule && !leftArmedByMask;
				if (!leftArmed) {
					break;
				}
				left--;
			}
			
			// 向右扩展，找到连续布防区间的结束位置
			int right = daySlotNow;
			while (right + 1 < 48) {
				boolean rightArmedBySchedule = dayBits.get(right + 1);
				boolean rightArmedByMask = existingMask != null && existingMask.get(right + 1);
				// 左边是布防，右边是空，则++
				// 左边是布防，右边是不撤防，则++
				// 左边是布防，右边是撤防，则退出
				// 左边是撤防，则退出
				boolean rightArmed = rightArmedBySchedule && !rightArmedByMask;
				if (!rightArmed) {
					break;
				}
				right++;
			}
			
			// 设置撤防掩码（包含整个连续区间）
			// 将 mask 中从索引 left 到 right（包含 right）的所有位都设置为 true
			mask.set(left, right + 1);
		} else {
			// 情况2：当前在撤防段，撤防下一个连续布防区间
			
			// 从当前槽位开始，找到下一个布防段的起始位置
			int i = daySlotNow + 1;
			while (i < 48) {
				boolean iArmedBySchedule = dayBits.get(i);
				boolean iArmedByMask = existingMask != null && existingMask.get(i);
				boolean iArmed = iArmedBySchedule && !iArmedByMask;
				if (iArmed) {
					break;
				}
				i++;
			}
			
			if (i < 48) {
				// 找到了下一个布防段
				int left = i;
				int right = i;
				
				// 向右扩展，找到连续布防区间的结束位置
				while (right + 1 < 48) {
					boolean rightArmedBySchedule = dayBits.get(right + 1);
					boolean rightArmedByMask = existingMask != null && existingMask.get(right + 1);
					boolean rightArmed = rightArmedBySchedule && !rightArmedByMask;
					if (!rightArmed) {
						break;
					}
					right++;
				}
				
				// 设置撤防掩码（包含整个连续区间）
				mask.set(left, right + 1);
			}
			// 如果没有找到下一个布防段，则返回空掩码
		}
		
		return mask;
	}
}


