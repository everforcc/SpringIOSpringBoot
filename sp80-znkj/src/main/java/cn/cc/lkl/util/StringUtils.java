package cn.cc.lkl.util;

import java.util.Random;

public class StringUtils {

    // 获取流水号
    public static String getSerialNumber() {
        Random rand = new Random();
        long randomNum = rand.nextLong() % 1000000000000000000L + 1000000000000000000L;
        return randomNum + "";
    }

    // 获得uuid
    public static String getUUID() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }

}
