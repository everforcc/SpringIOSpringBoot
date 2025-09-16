package cn.cc.lkl.util;

import org.apache.commons.lang3.RandomUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getTimeStamp() {
        return (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date());
    }

    /**
     * 14位年月日时（24小时制）分秒+8位的随机数（不重复）如：2021020112000012345678
     */
    public static String getTimeStampAndRandom() {
        return (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + RandomUtils.nextInt(10000000, 99999999);
    }

}
