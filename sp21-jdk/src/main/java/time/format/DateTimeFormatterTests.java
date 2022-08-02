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

    public static DateTimeFormatter initlocalDateTime() {
        DateTimeFormatter defaultDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return dateTimeFormatter;
    }

    public static void localDate(){
        String timeStr = "2022年08月02日";
        DateTimeFormatter dateTimeFormatter = initLocalDate();
        LocalDate date = LocalDate.parse(timeStr, dateTimeFormatter);
        System.out.println(date);
        String format = date.format(dateTimeFormatter);
        System.out.println(format);
    }

    public static void localDateTime(){
        String timeStr = "2022-08-02 12:00:00";
        DateTimeFormatter dateTimeFormatter = initlocalDateTime();
        LocalDateTime date = LocalDateTime.parse(timeStr, dateTimeFormatter);
        System.out.println(date);
        String format = date.format(dateTimeFormatter);
        System.out.println(format);
    }

    public static void main(String[] args) {
        localDateTime();
    }

}
