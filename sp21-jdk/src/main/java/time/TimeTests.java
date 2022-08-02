/**
 * @Description
 * @Author everforcc
 * @Date 2022-08-02 17:01
 * Copyright
 */

package time;

import java.time.*;

public class TimeTests {

    /**
     * 1. 瞬时实例
     */
    public static void instantTests() {
        Instant instant = Instant.now();
        System.out.println(instant.toString());
    }

    /**
     * 2. 本地日期,不包含具体时间
     * 2022-08-02
     */
    public static void localDateTests() {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.toString());
    }

    /**
     * 3. 本地时间不包含日期
     * 17:14:27.065
     */
    public static void localTimeTests() {
        LocalTime localTime = LocalTime.now();
        System.out.println(localTime.toString());
    }

    /**
     * 4. 组合了日期和时间，但不包含时差和时区信息
     * 2022-08-02T17:16:19.130
     */
    public static void localDateTimeTests() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.toString());
    }

    /**
     * 5. 最完整的日期时间,包含时区和相对UTC的时差
     * 新API还引入了 ZoneOffSet 和 ZoneId 类，使得解决时区问题更为简便。解析、格式化时间的 DateTimeFormatter 类也全部重新设计。
     * 2022-08-02T17:19:02.447+08:00[Asia/Shanghai]
     */
    public static void zonedDateTimeTests() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println(zonedDateTime.toString());
    }

    public static void main(String[] args) {
         instantTests();
        // localDateTests();
        // localTimeTests();
        // localDateTimeTests();
        // zonedDateTimeTests();
    }

}
