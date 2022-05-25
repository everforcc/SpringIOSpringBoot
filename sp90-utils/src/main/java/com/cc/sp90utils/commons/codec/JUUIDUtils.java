package com.cc.sp90utils.commons.codec;

import java.util.UUID;

public class JUUIDUtils {

    /**
     * 生成uuid并去掉中间 -
     * @return
     */
    public static String uuid32(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) {
        System.out.println(uuid32());
    }

}
