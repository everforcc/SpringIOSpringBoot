package cn.cc.utils;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description : 时间工具类测试
 * @Author : GKL
 * @Date: 2024-05-06 10:59
 */
public class DateUtilsTest {

    @Test
    public void tUntilLocalDate() {
        LocalDate startDate = LocalDate.of(2024, 5, 6);
        LocalDate endDate = LocalDate.of(2024, 5, 5);
        System.out.println(DateUtils.untilLocalDate(startDate, endDate));
    }

    @Test
    public void tUntilLocalDate2() {

        Date date = new Date(0 * 3600 * 1000);
        LocalDate localDate = DateUtils.toLocalDate(date);
        System.out.println("date: " + date);
        System.out.println("localDate: " + localDate);

        Date date2 = new Date(24 * 3600 * 1000);
        LocalDate localDate2 = DateUtils.toLocalDate(date2);
        System.out.println("date2: " + date2);
        System.out.println("localDate2: " + localDate2);

        System.out.println(DateUtils.untilLocalDate(date, date2));

    }

    @Test
    public void untilLocalDateTime() {
        LocalDateTime startDate = LocalDateTime.of(2024, 5, 6, 11, 9, 1);
        LocalDateTime endDate = LocalDateTime.of(2024, 5, 7, 0, 9, 2);
        System.out.println(DateUtils.untilLocalDateTime(startDate, endDate));
    }

    @Test
    public void tDateDifHour() {
        Date startDate = new Date(3600 * 1000);
        Date endDate = new Date(7200 * 1000);
        System.out.println(DateUtils.dateDifHour(startDate, endDate));
    }

    @Test
    public void tDifMinute() {
        Date startDate = new Date(3600);
        Date endDate = new Date(7251);
        System.out.println(DateUtils.difMinute(startDate, endDate));
    }

    @Test
    public void tIsOverLap() {
        Date startDate = new Date(3600);
        Date endDate = new Date(7200);
        Date durationStartDate1 = new Date(2600);
        Date durationEndDate1 = new Date(4600);
        System.out.println("是否有交集1: " + DateUtils.isOverLap(startDate, endDate,
                durationStartDate1, durationEndDate1));

        Date durationStartDate2 = new Date(4600);
        Date durationEndDate2 = new Date(8600);
        System.out.println("是否有交集2: " + DateUtils.isOverLap(startDate, endDate,
                durationStartDate2, durationEndDate2));

        Date durationStartDate3 = new Date(2600);
        Date durationEndDate3 = new Date(8600);
        System.out.println("是否有交集3: " + DateUtils.isOverLap(startDate, endDate,
                durationStartDate3, durationEndDate3));

        Date durationStartDate4 = new Date(4600);
        Date durationEndDate4 = new Date(4600);
        System.out.println("是否有交集4: " + DateUtils.isOverLap(startDate, endDate,
                durationStartDate4, durationEndDate4));

        Date durationStartDate5 = new Date(8600);
        Date durationEndDate5 = new Date(8600);
        System.out.println("是否有交集5: " + DateUtils.isOverLap(startDate, endDate,
                durationStartDate5, durationEndDate5));

    }

}
