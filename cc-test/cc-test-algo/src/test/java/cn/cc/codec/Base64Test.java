package cn.cc.codec;


import cn.cc.codec.base64.Base64UserTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Base64ApacheTest;
import org.bouncycastle.util.encoders.Base64BouncyCastleTest;
import org.junit.Test;
import sun.misc.Base64JDKTest;

@Slf4j
public class Base64Test {

    // 测试aaa
    private static final String str = "测";

    @Test
    public void base64Test() {
        Base64UserTest.userBase64(str);
        Base64JDKTest.JDKBase64Test(str);
        Base64ApacheTest.apacheBase64Test(str);
    }

    /**
     * 使用有问题
     */
    @Test
    public void bouncyCastleBase64Test() {
        Base64BouncyCastleTest.bouncyCastleBase64(str);
    }

}
