package com.cc.sp90utils.enums;

public enum HttpTypeEnum {

    GET("GET"),
    POST("POST"),
    // PUT 等不常用
    ;

    public final String type;

    HttpTypeEnum(String type) {
        this.type = type;
    }

}
