package com.cc.sp90utils.enums;

public enum BooleanEnum {

    TRUE( true),
    FALSE( false),
    ;

    public final boolean flag;

    BooleanEnum(boolean flag) {
        this.flag = flag;
    }

}
