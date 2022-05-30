package com.cc.sp90utils.enums.impl;

import com.cc.sp90utils.enums.BaseEnum;

/**
 * @author guokailong 2021-09-07
 */
public enum MenuEnum implements BaseEnum<Integer> {

    ROOT(0),
    TYPE(1),
    BUSI(2),
    ;

    /**
     * 枚举属性说明
     */
    public final int comment;
    /**
     * 是否已废弃
     */
    public final boolean deprecated;

    MenuEnum(final int comment) {
        this(comment, false);
    }

    MenuEnum(final int comment, final boolean deprecated) {
        this.comment = comment;
        this.deprecated = deprecated;
    }

    public static void main(String[] args) {
//        System.out.println(MenuEnum.values()[0].toString());
//        System.out.println(MenuEnum.values()[1].toString());
//        System.out.println(MenuEnum.values()[1].comment);
//        System.out.println(MenuEnum.values()[1].deprecated);

        System.out.println(MenuEnum.ROOT.comment);

        System.out.println(MenuEnum.valueOf("TYPE").comment);

        System.out.println(MenuEnum.valueOf("1").comment);
    }

    @Override
    public Integer getCode() {
        return comment;
    }
}
