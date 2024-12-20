package org.apache.commons.codec.digest;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;

@Slf4j
public class MD5ApacheTest {

    public static String getMD5(String data) {
//        String base = dataStr + slat;
        String md5 = null;
        try {
            md5 = DigestUtils.md5Hex(data.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.info("data: {}, md5: \r\n{}", data, md5);
        return md5;
    }

}
