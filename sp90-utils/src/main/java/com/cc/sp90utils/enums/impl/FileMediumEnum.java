package com.cc.sp90utils.enums.impl;

import com.cc.sp90utils.enums.BaseEnum;

/**
 * @author guokailong 2021-09-07
 */
public enum FileMediumEnum implements BaseEnum<Integer> {

    /**
     * {@link } 命名作为 IFileService 实现 的判断依据
     */

    MYSQL(0),
    FTP(1),
    ALY(2),
    BDY(3),
    WINDOWS(4)
    ;

    /**
     * 枚举属性说明
     */
    public final int comment;

    FileMediumEnum(final int comment) {
        this.comment = comment;
    }

    public static void main(String[] args) {
        System.out.println(FileMediumEnum.values()[0].toString());
        System.out.println(FileMediumEnum.values()[1].toString());
        System.out.println(FileMediumEnum.values()[1].comment);
    }

    @Override
    public Integer getCode() {
        return comment;
    }
}
