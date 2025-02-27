package org.bouncycastle.util.encoders;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class Base64BouncyCastleTest {

    @Test
    public void base64TestBouncyCastle() {
        Base64BouncyCastleTest.bouncyCastleBase64("aaa");
    }

    /**
     * 报错
     * java.lang.SecurityException: class "org.bouncycastle.util.encoders.Base64"'s signer information does not match signer information of other classes in the same package
     * 用bouncy castle实现
     *
     * @param str 待加密参数
     */
    public static void bouncyCastleBase64(String str) {
        byte[] encodeBytes = Base64.encode(str.getBytes());
        log.info("bouncy castle encode: {}", new String(encodeBytes));

        byte[] dencodeBytes = Base64.decode(encodeBytes);
        log.info("bouncy castle decode: {}", new String(dencodeBytes));
    }

}
