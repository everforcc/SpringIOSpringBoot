/**
 * @Description
 * @Author everforcc
 * @Date 2022-08-02 16:04
 * Copyright
 */

package time.format;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeFormatterTests {

    /**
     * 1. 初始化
     */
    public static DateTimeFormatter initLocalDate() {
        DateTimeFormatter defaultDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日", Locale.CHINA);
        return dateTimeFormatter;
    }

    /**
     * 不同格式化
     */
    public static DateTimeFormatter initlocalDateTime() {
        DateTimeFormatter defaultDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return dateTimeFormatter;
    }

    /**
     * 格式化测试
     */
    public static void localDate() {
        String timeStr = "2022年08月02日";
        DateTimeFormatter dateTimeFormatter = initLocalDate();
        LocalDate date = LocalDate.parse(timeStr, dateTimeFormatter);
        System.out.println(date);
        String format = date.format(dateTimeFormatter);
        System.out.println(format);
    }

    /**
     * 格式化测试
     */
    public static void localDateTime() {
        String timeStr = "2022-08-02 12:00:00";
        DateTimeFormatter dateTimeFormatter = initlocalDateTime();
        LocalDateTime date = LocalDateTime.parse(timeStr, dateTimeFormatter);
        System.out.println(date);
        String format = date.format(dateTimeFormatter);
        System.out.println(format);
    }

    /**
     * 时间增加
     * 时分秒
     */
    public static void timeAdd() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        LocalDateTime now = LocalDateTime.now();
        System.out.println("当前时间: " + now.format(dateTimeFormatter));
        now = now.plusMonths(1).plusDays(1).plusHours(1).plusMinutes(1).plusSeconds(1);
        System.out.println("增加一月一天一小时一分钟一秒钟后的时间是: " + now.format(dateTimeFormatter));
        now = now.plusMonths(-2).plusDays(-2).plusHours(-2).plusMinutes(-2).plusSeconds(-2);
        System.out.println("减少一月一天一小时一分钟一秒钟后的时间是: " + now.format(dateTimeFormatter));
    }

    public static void main(String[] args) {
        //localDateTime();
        timeAdd();
    }

}
