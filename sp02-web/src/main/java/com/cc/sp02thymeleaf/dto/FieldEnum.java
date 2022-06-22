package com.cc.sp02thymeleaf.dto;


/**
 * @author guokailong 2021-09-07
 */
public enum FieldEnum {

    /**
     * 无效, 需要vip不能用
     */
    UNEFFECT,
    /**
     * 有效, 不需要vip可用
     */
    EFFECT;


    FieldEnum() {

    }


    public static void main(String[] args) {
        System.out.println(FieldEnum.UNEFFECT);
        System.out.println(FieldEnum.UNEFFECT.name());
        System.out.println(FieldEnum.UNEFFECT.ordinal());
        System.out.println(FieldEnum.UNEFFECT.getCode());
        //FieldEnum[] fieldEnums = FieldEnum.values();

    }

    public Integer getCode() {
        return this.ordinal();
    }
}
