package cn.cc.utils.regex;

import cn.cc.utils.regex.constant.RegexAuthCodeConstant;
import org.junit.Test;

public class RegexAuthCodeTest {

    /**
     * 校验是不是支付宝微信支付码
     */
    @Test
    public void t1() {
        String authCode = "134021098912129667";
        if (!(authCode.matches(RegexAuthCodeConstant.AUTH_CODE_ZFB) || authCode.matches(RegexAuthCodeConstant.AUTH_CODE_WX))) {
            throw new RuntimeException("支付码格式错误");
        } else {
            System.out.println("正确");
        }
    }

}
