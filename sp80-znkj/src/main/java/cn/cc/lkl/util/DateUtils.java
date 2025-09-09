package cn.cc.lkl.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getTimeStamp() {
        return (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date());
    }

}
