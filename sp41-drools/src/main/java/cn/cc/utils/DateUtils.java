package cn.cc.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description : 时间工具类
 * @Author : GKL
 * @Date: 2024-05-06 10:52
 */
public class DateUtils {

    /**
     * date转LocalDateTime
     */
    public static LocalDate toLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static long untilLocalDate(LocalDate startDate, LocalDate endDate) {
        return startDate.until(endDate, ChronoUnit.DAYS);
    }

    /**
     * 计算隔了几个自然日
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 自然日
     */
    public static long untilLocalDate(Date startDate, Date endDate) {
        return toLocalDate(startDate).until(toLocalDate(endDate), ChronoUnit.DAYS);
    }

    public static long untilLocalDateTime(LocalDateTime startDate, LocalDateTime endDate) {
        return startDate.until(endDate, ChronoUnit.DAYS);
    }

    public static long dateDifHour(Date startDate, Date endDate) {
        return (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60);
    }

    /**
     * 时间段时间有几分钟，向上取整
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 返回结果
     */
    public static int difMinute(Date startDate, Date endDate) {
        double aDouble = Math.ceil((endDate.getTime() - startDate.getTime()) / 60d);
        return (int) aDouble;
    }


    /**
     * 前一个时间区间和后一个是否有交集
     *
     * @param startDate         区间一 开始时间
     * @param endDate           区间一 结束时间
     * @param durationStartDate 区间二 开始时间
     * @param durationEndDate   区间二 结束时间
     * @return 结果
     */
    public static boolean isOverLap(Date startDate, Date endDate, Date durationStartDate, Date durationEndDate) {
        // 几种情况
        if (
                (startDate.before(durationEndDate) && startDate.after(durationStartDate)) ||
                        (endDate.before(durationEndDate) && endDate.after(durationStartDate)) ||
                        (startDate.before(durationEndDate) && endDate.after(durationStartDate))
        ) {

            return true;
        }
        return false;
    }

    public static Date hourOFDay(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }

}
