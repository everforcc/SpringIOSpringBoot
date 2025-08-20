package cn.cc.alarm;

import java.time.*;

/**
 * 半小时槽与周偏移计算工具；并提供“距离今日午夜”的过期时长计算。
 */
public final class TimeSlotUtils {

	private TimeSlotUtils() {}

	public static int daySlot(LocalTime time) {
		return time.getHour() * 2 + (time.getMinute() >= 30 ? 1 : 0);
	}

	public static int weekDayIndex(DayOfWeek dayOfWeek) {
		return dayOfWeek.getValue() % 7; // Mon=0..Sun=6
	}

	public static int weekOffset(DayOfWeek dayOfWeek, LocalTime time) {
		return weekDayIndex(dayOfWeek) * 48 + daySlot(time);
	}

	public static int currentWeekOffset(ZoneId zoneId) {
		ZonedDateTime now = ZonedDateTime.now(zoneId);
		return weekOffset(now.getDayOfWeek(), now.toLocalTime());
	}

	public static int currentDaySlot(ZoneId zoneId) {
		ZonedDateTime now = ZonedDateTime.now(zoneId);
		return daySlot(now.toLocalTime());
	}

	/**
 	 * 计算从当前时间到今天 23:59:59.999 的剩余时长，用于设置“今日掩码”的 TTL。
 	 */
	public static Duration durationUntilMidnight(ZoneId zoneId) {
		ZonedDateTime now = ZonedDateTime.now(zoneId);
		ZonedDateTime tomorrowStart = now.toLocalDate().plusDays(1).atStartOfDay(zoneId);
		return Duration.between(now, tomorrowStart);
	}
}


