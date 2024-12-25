package org.apache.commons.codec;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

@Slf4j
public class MD5ApacheTest {

    public static String getMD5(String data) {
//        String base = dataStr + slat;
        String md5 = null;
        //md5 = DigestUtils.md5Hex(data.getBytes(StandardCharsets.UTF_8));
        md5 = DigestUtils.md5Hex(data.getBytes());
        log.info("data: {}, md5: \r\n{}", data, md5);
        return md5;
    }

}
