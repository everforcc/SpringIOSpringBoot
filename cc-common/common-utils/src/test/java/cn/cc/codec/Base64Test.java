package cn.cc.codec;

import cn.cc.codec.base64.Base64UserTest;
import jdk.sun.misc.Base64JDKTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Base64ApacheTest;
import org.junit.Test;

@Slf4j
public class Base64Test {

    // 测试aaa
    private static final String str = "测";

    @Test
    public void base64Test() {
        Base64UserTest.userBase64(str);
        Base64JDKTest.JDKBase64Test(str);
        Base64ApacheTest.apacheBase64Test(str);
//        BouncyCastleBase64Test.bouncyCastleBase64(str);
    }

}
