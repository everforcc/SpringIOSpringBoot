/**
 * @Description
 * @Author everforcc
 * @Date 2022-05-17 14:24
 * Copyright
 */

package cn.cc.utils.commons.lang;

import cn.cc.utils.constant.DateFormatConstant;
import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Date;

public class RFastDateFormat {


    public static String format(Date date){
        return format(date, DateFormatConstant.yyyy_MM_dd_2);
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
