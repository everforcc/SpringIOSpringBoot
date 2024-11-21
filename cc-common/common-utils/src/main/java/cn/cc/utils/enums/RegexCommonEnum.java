package cn.cc.utils.enums;

public enum RegexCommonEnum {

    DEMO_P("^((?!regex).)*$","逆向模式"),
    /**
     * 所有正则需要记录在类
     * 正则和对应的报错，如果修改，需要修改全局引用的位置，
     * @Pattern 无法直接引用，请对应调整
     */
    FILE_NAME_P("^((?!(\\<+|\\>+|\\/|\\\\+|\\|+|\\:+|\"+|\\*+|\\?+|\\；+|\\ +)).)*$","文件/文件夹命名有误"),
    PHONE_NUMBER_P("^(1)\\d{10}$","手机号格式不正确"),
    CHINESE_CODE("u4e00-u9fa5","中文"),

    ;

    private String regex;
    private String errormsg;

    RegexCommonEnum(String regex, String errormsg) {
        this.regex = regex;
        this.errormsg = errormsg;
    }

    private static String getValue(String regex) {
        RegexCommonEnum[] danceEnums = values();
        for (RegexCommonEnum danceEnum : danceEnums) {
            if (danceEnum.regex().equals(regex)) {
                return danceEnum.regex();
            }
        }
        return "3";
    }

    public String regex() {
        return this.regex;
    }

    public String errormsg() {
        return this.errormsg;
    }

//    public String getRegex() {
//        return regex;
//    }
//
//    public void setRegex(String regex) {
//        this.regex = regex;
//    }
//
//    public String getErrormsg() {
//        return errormsg;
//    }
//
//    public void setErrormsg(String errormsg) {
//        this.errormsg = errormsg;
//    }

}
