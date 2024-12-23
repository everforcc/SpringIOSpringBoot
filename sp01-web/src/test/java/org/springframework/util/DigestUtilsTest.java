package org.springframework.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

@Slf4j
public class DigestUtilsTest {

    @Test
    public void getMD5() {
        String str = "aa啊啊啊";
        String md5 = DigestUtils.md5DigestAsHex(str.getBytes(StandardCharsets.UTF_8));
        log.info("md5: {}", md5);
    }

}
