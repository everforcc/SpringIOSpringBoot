package cn.cc.alarm;

import java.time.*;

/**
 * 半小时槽与周偏移计算工具；并提供"距离今日午夜"的过期时长计算。
 * 
 * 时间槽位说明：
 * - 一天分为48个半小时槽位（0-47）
 * - 槽位0：00:00-00:30
 * - 槽位1：00:30-01:00
 * - ...
 * - 槽位47：23:30-24:00
 * 
 * 周偏移说明：
 * - 一周336个槽位（7天×48槽位）
 * - 周一：0-47
 * - 周二：48-95
 * - ...
 * - 周日：288-335
 */
public final class TimeSlotUtils {

	private TimeSlotUtils() {}

	/**
	 * 根据时间计算对应的半小时槽位
	 * 
	 * 计算规则：
	 * - 小时数 × 2 + (分钟数 >= 30 ? 1 : 0)
	 * - 例如：14:25 → 14×2+0 = 28
	 * - 例如：14:35 → 14×2+1 = 29
	 * 
	 * @param time 时间
	 * @return 0-47的槽位编号
	 */
	public static int daySlot(LocalTime time) {
		return time.getHour() * 2 + (time.getMinute() >= 30 ? 1 : 0);
	}

	/**
	 * 将DayOfWeek转换为0-6的索引
	 * 
	 * 映射关系：
	 * - MONDAY → 0
	 * - TUESDAY → 1
	 * - WEDNESDAY → 2
	 * - THURSDAY → 3
	 * - FRIDAY → 4
	 * - SATURDAY → 5
	 * - SUNDAY → 6
	 * 
	 * @param dayOfWeek 星期几
	 * @return 0-6的索引值
	 */
	public static int weekDayIndex(DayOfWeek dayOfWeek) {
		return dayOfWeek.getValue() % 7; // Mon=0..Sun=6
	}

	/**
	 * 计算指定日期和时间在周位图中的偏移量
	 * 
	 * 计算公式：
	 * - weekDayIndex(dayOfWeek) × 48 + daySlot(time)
	 * - 例如：周一14:25 → 0×48+28 = 28
	 * - 例如：周二09:15 → 1×48+18 = 66
	 * 
	 * @param dayOfWeek 星期几
	 * @param time 时间
	 * @return 0-335的周偏移量
	 */
	public static int weekOffset(DayOfWeek dayOfWeek, LocalTime time) {
		return weekDayIndex(dayOfWeek) * 48 + daySlot(time);
	}

	/**
	 * 计算当前时间在周位图中的偏移量
	 * 
	 * @param zoneId 时区
	 * @return 0-335的周偏移量
	 */
	public static int currentWeekOffset(ZoneId zoneId) {
		ZonedDateTime now = ZonedDateTime.now(zoneId);
		return weekOffset(now.getDayOfWeek(), now.toLocalTime());
	}

	/**
	 * 计算当前时间对应的半小时槽位
	 * 
	 * @param zoneId 时区
	 * @return 0-47的槽位编号
	 */
	public static int currentDaySlot(ZoneId zoneId) {
		ZonedDateTime now = ZonedDateTime.now(zoneId);
		return daySlot(now.toLocalTime());
	}

	/**
	 * 计算从当前时间到今天 23:59:59.999 的剩余时长，用于设置"今日掩码"的 TTL。
	 * 
	 * 用途：
	 * - 今日掩码的过期时间设置为午夜
	 * - 确保第二天自动恢复周计划
	 * 
	 * @param zoneId 时区
	 * @return 到午夜的剩余时长
	 */
	public static Duration durationUntilMidnight(ZoneId zoneId) {
		ZonedDateTime now = ZonedDateTime.now(zoneId);
		ZonedDateTime tomorrowStart = now.toLocalDate().plusDays(1).atStartOfDay(zoneId);
		return Duration.between(now, tomorrowStart);
	}
}


