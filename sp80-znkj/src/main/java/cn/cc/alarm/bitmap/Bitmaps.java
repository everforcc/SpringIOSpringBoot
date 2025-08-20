package cn.cc.alarm.bitmap;

import java.util.BitSet;

/**
 * 位图工具：
 * - fromBytes/toBytes：与 java.util.BitSet 的二进制互转；
 * - sliceDay：从周位图切出当天 48 槽；
 * - buildTodayDefenceMask：依据“今日撤防规则”构建掩码（支持当前段或下一布防段）。
 *
 * 约定：
 * - 1 表示布防（在周位图）；1 表示“需要撤防”（在今日掩码）。
 * - 掩码将与存量掩码做并集（OR），并设置至午夜的 TTL。
 */
public final class Bitmaps {

	private Bitmaps() {}

	/**
 	 * 将二进制转为 BitSet；null 视为空 BitSet。
 	 */
	public static BitSet fromBytes(byte[] bytes) {
		return bytes == null ? new BitSet() : BitSet.valueOf(bytes);
	}

	/**
 	 * 将 BitSet 转为二进制；null 视为空数组。
 	 */
	public static byte[] toBytes(BitSet bitSet) {
		return bitSet == null ? new byte[0] : bitSet.toByteArray();
	}

	/**
 	 * 从周位图中切出某天的 48 槽位图。
 	 */
	public static BitSet sliceDay(BitSet weekBits, int dayIndex) {
		BitSet day = new BitSet(48);
		int base = dayIndex * 48;
		for (int i = 0; i < 48; i++) {
			if (weekBits.get(base + i)) {
				day.set(i);
			}
		}
		return day;
	}

	/**
 	 * 构建今日撤防掩码：
 	 * - 若当前处于布防段，则撤防“包含当前槽位的连续布防区间”；
 	 * - 若当前处于撤防段，则撤防“今天的下一个连续布防区间”。
 	 */
	public static BitSet buildTodayDefenceMask(BitSet dayBits, int daySlotNow) {
		BitSet mask = new BitSet(48);
		boolean nowArmed = dayBits.get(daySlotNow);
		if (nowArmed) {
			int left = daySlotNow;
			while (left > 0 && dayBits.get(left - 1)) {
				left--;
			}
			int right = daySlotNow;
			while (right + 1 < 48 && dayBits.get(right + 1)) {
				right++;
			}
			mask.set(left, right + 1);
		} else {
			int i = daySlotNow + 1;
			while (i < 48 && !dayBits.get(i)) {
				i++;
			}
			if (i < 48) {
				int left = i;
				int right = i;
				while (right + 1 < 48 && dayBits.get(right + 1)) {
					right++;
				}
				mask.set(left, right + 1);
			}
		}
		return mask;
	}
}


