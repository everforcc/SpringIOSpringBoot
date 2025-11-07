package cn.cc.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    /**
     * @return 获取这个格式的时间 20251106
     */
    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }

}
