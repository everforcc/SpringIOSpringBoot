package com.cc.sp90utils.exception;


public enum Code implements ICode {
    A00000("成功"),
    A00001("失败"),

    ;
    /**
     * 枚举属性说明
     */
    public final String comment;

    Code(String comment) {
        this.comment = comment;
    }

    @Override
    public String getComment() {
        return this.comment;
    }

    public static void main(String[] args) {

    }

}
