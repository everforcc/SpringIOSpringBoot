package org.apache.commons.codec;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Slf4j
public class ApacheBase64Test {

    public static void apacheBase64Test(String str) {
        byte[] encodeBytes = Base64.encodeBase64(str.getBytes());
        log.info("common codes encode: {}", new String(encodeBytes));

        byte[] dencodeBytes = Base64.decodeBase64(encodeBytes);
        log.info("common codes decode: {}", new String(dencodeBytes));
    }

}
