package com.cc.sp02thymeleaf.utils;

import java.util.UUID;

public class StringUtil {

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }

}
