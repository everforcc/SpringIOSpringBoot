package com.cc.sp90utils.commons.lang;

import com.cc.sp90utils.constant.DateFormatConstant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 时间工具类
 */
public class RSimpleDateFormat {

    /**
     * 根据正则返回时间
     *
     * @param regex
     * @return
     */
    public static String nowTime(String regex) {
        return new SimpleDateFormat(regex).format(new Date(System.currentTimeMillis()));
    }

    public static String nowTime(long timeMillis) {
        return new SimpleDateFormat(DateFormatConstant.yyyy_MM_dd_2).format(new Date(timeMillis));
    }

    public static String nowTime() {
        return new SimpleDateFormat(DateFormatConstant.yyyy_MM_dd_2).format(new Date(System.currentTimeMillis()));
    }

    /**
     * 严格校验日期是否为正确格式
     *
     * @param dateStr 日期字符串
     * @param pattern 正则
     * @return 校验结果
     */
    public static boolean checkStrEffect(String dateStr, String pattern) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            // 设置严格校验， 否则 20220229 也会通过
            simpleDateFormat.setLenient(false);
            simpleDateFormat.parse(dateStr);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

}
