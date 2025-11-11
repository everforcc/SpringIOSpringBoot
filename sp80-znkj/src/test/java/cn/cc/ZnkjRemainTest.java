package cn.cc;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ZnkjRemainTest {

    @Test
    public void remainDay() {

        // 获取当前日期
        LocalDate today = LocalDate.now();
        // 设置要比较的日期，例如：2023年3月15日
        LocalDate specificDate = LocalDate.of(2025, 11, 8);
        // 计算两个日期之间的天数差
        long daysBetween = ChronoUnit.DAYS.between(today, specificDate);

        // 输出结果
        System.out.println("从今天到指定日期还有 " + daysBetween + " 天");
        if (daysBetween < 0) {
            System.out.println("注意：指定日期已过。");
        }

        int hour = LocalDateTime.now().minusHours(7).getHour();

        System.out.println(hour);

        String str = "1";
        System.out.println(str.charAt(0));

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime endTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), 0, 0);
        LocalDateTime startTime = endTime.minusDays(1);

        System.out.println(startTime);
        System.out.println(endTime);

        Integer a = 1;
        System.out.println(a.equals(1));
    }

}
