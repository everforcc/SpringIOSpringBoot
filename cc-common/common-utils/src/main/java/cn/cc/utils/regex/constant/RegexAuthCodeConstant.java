package cn.cc.utils.regex.constant;

public class RegexAuthCodeConstant {

    /**
     * 支付宝
     */
    public static String AUTH_CODE_ZFB = "^(25|26|27|28|29|30)\\d{14,22}$";

    /**
     * 微信
     */
    public static String AUTH_CODE_WX = "^(10|11|12|13|14|15)\\d{16}$";

}
