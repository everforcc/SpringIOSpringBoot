package com.cc.sp90utils.commons.codec;

import java.util.UUID;

public class UUIDUtils {

    /**
     * 生成uuid并去掉中间 -
     * @return
     */
    public static String uuid32(){
        return UUID.randomUUID().toString().replace("-", "");
    }


}
