package cn.cc.utils.enums;

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
