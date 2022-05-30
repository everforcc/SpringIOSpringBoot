package com.cc.sp90utils.enums.impl;

import com.cc.sp90utils.enums.BaseEnum;

/**
 * @author guokailong 2021-09-07
 */
public enum StatusEnum implements BaseEnum<Integer> {

    /**
     * 无效, 需要vip不能用
     */
    UNEFFECT(0),
    /**
     * 有效, 不需要vip可用
     */
    EFFECT(1);

    /**
     * 枚举属性说明
     */
    public final int comment;
    /**
     * 是否已废弃
     */
    public final boolean deprecated;

    StatusEnum(final int comment) {
        this(comment, false);
    }

    StatusEnum(final int comment, final boolean deprecated) {
        this.comment = comment;
        this.deprecated = deprecated;
    }

    public static void main(String[] args) {
        System.out.println(StatusEnum.values()[0].toString());
        System.out.println(StatusEnum.values()[1].toString());
        System.out.println(StatusEnum.values()[1].comment);
        System.out.println(StatusEnum.values()[1].deprecated);
    }

    @Override
    public Integer getCode() {
        return comment;
    }
}
