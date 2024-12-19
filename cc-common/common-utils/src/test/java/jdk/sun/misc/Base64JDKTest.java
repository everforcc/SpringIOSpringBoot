package jdk.sun.misc;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Slf4j
public class Base64JDKTest {

    /**
     * jdk 实现 base64
     *
     * @param str 入参
     */
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
