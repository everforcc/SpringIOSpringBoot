package cn.cc.utils.regex.constant;

public class RegexChineseConstant {

    /**
     * 检查字符串是不是
     * 中文
     * 英文
     * 数字
     * 组成的
     * 一样
     * "^[\\u4e00-\\u9fa5a-zA-Z0-9]{1,10}$"
     */
    public static String CHINESE_AZ_NUMBER = "^[\u4e00-\u9fa5a-zA-Z0-9]{1,10}$";

}
