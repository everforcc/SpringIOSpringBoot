package jdk.sun.misc;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.ApacheBase64Test;
import org.bouncycastle.util.encoders.BouncyCastleBase64Test;
import org.junit.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Slf4j
public class Base64Test {

    private static final String str = "测试aaa";

    @Test
    public void base64Test(){
        JDKBase64Test(str);
        ApacheBase64Test.apacheBase64Test(str);
//        BouncyCastleBase64Test.bouncyCastleBase64(str);
    }

    @Test
    public void base64TestBouncyCastle(){
        BouncyCastleBase64Test.bouncyCastleBase64(str);
    }

    public static void JDKBase64Test(String str) {
        try {
            BASE64Encoder encoder = new BASE64Encoder();
            String encode = encoder.encode(str.getBytes());
            log.info("jdk encode: {}", encode);

            BASE64Decoder decoder = new BASE64Decoder();
            log.info("jdk decode: {}", new String(decoder.decodeBuffer(encode)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
