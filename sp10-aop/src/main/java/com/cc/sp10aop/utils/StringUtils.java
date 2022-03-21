package com.cc.sp10aop.utils;

import java.util.UUID;

public class StringUtils {

    /**
     * 生成uuid并去掉中间 -
     * @return
     */
    public static String uuid32(){
        return UUID.randomUUID().toString().replace("-", "");
    }

}
