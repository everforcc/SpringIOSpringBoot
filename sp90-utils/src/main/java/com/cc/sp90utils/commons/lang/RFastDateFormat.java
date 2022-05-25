/**
 * Project:TODO ADD PROJECT NAME SpringIOSpringBoot
 *
 * @Description
 * @Author Author Date Description
 * ------ ------ ------
 *    TODO 开发人员邮箱前缀 调整时间 年-月-日 主要改动点>5字符
 * @Date 2022-05-17 14:24
 * Copyright
 */

package com.cc.sp90utils.commons.lang;

import com.cc.sp90utils.constant.DateFormatConstant;
import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Date;

public class RFastDateFormat {


    public static String format(Date date){
        return format(date,DateFormatConstant.yyyy_MM_dd_2);
    }

    public static String format(long time){
        return format(time,DateFormatConstant.yyyy_MM_dd_2);
    }

    /**
     *
     * @param date
     * @param pattern {@link DateFormatConstant }
     */
    public static String format(Date date,String pattern){
        FastDateFormat fastDateFormat = FastDateFormat.getInstance(pattern);
        return fastDateFormat.format(date);
    }

    public static String format(long time,String pattern){
        FastDateFormat fastDateFormat = FastDateFormat.getInstance(pattern);
        return fastDateFormat.format(time);
    }

    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(format(date,DateFormatConstant.yyyy_MM_dd_2));
        System.out.println(format(date.getTime(),DateFormatConstant.yyyy_MM_dd_2));
    }

}
