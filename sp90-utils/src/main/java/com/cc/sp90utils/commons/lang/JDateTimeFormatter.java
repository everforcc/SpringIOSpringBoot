/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-26 17:02
 * Copyright
 */

package com.cc.sp90utils.commons.lang;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Date;

/**
 * 格式化时间
 */
public class JDateTimeFormatter {

    public static Date parse(String dateStr, String pattern) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
            dateTimeFormatter.withResolverStyle(ResolverStyle.STRICT);
            dateTimeFormatter.withChronology(IsoChronology.INSTANCE);

            LocalDate localDate = LocalDate.parse(dateStr, dateTimeFormatter);
            // 设置系统默认时区
            ZoneId zoneId = ZoneId.systemDefault();
            Instant instant = localDate.atStartOfDay().atZone(zoneId).toInstant();
            return Date.from(instant);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("日期格式化异常: " + e.getMessage());
        }
    }

}
