package com.cc.sp90utils.enums;

public enum BooleanEnum {

    TRUE( true),
    FALSE( false),
    ;

    public final boolean flag;

    BooleanEnum(boolean flag) {
        this.flag = flag;
    }

    public static void main(String[] args) {
        System.out.println(BooleanEnum.TRUE.flag);
        System.out.println(BooleanEnum.FALSE.flag);
    }

}
