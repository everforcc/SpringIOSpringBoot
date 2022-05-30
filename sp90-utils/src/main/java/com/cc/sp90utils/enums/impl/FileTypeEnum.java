package com.cc.sp90utils.enums.impl;

import com.cc.sp90utils.enums.BaseEnum;

public enum FileTypeEnum implements BaseEnum<Integer> {
    TXT(0),
    BYTES(1),
    ;

    public final int filetype;

    FileTypeEnum(int filetype) {
        this.filetype = filetype;
    }

    @Override
    public Integer getCode() {
        return filetype;
    }

    public static void main(String[] args) {
        System.out.println(FileTypeEnum.TXT.toString());
    }

}
