package com.cc.sp90utils.commons.codec;

import org.apache.commons.codec.digest.DigestUtils;

public class RMD5Utils {

    public static String md5Hex(byte[] bytes){
        return DigestUtils.md5Hex(bytes);
    }

    public static String md5Hex(String str){
        return DigestUtils.md5Hex(str);
    }

}
