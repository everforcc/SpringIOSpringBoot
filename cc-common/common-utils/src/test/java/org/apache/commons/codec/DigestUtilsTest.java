package org.apache.commons.codec;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
@Slf4j
public class DigestUtilsTest {

    @Test
    public void sha256HexTest(){

        // 密码
        String passWord = "passWord";
        // 加盐
        String saltStr = "saltStr";

        String result = DigestUtils.sha256Hex((passWord + saltStr).getBytes(StandardCharsets.UTF_8));

        // 数据库存的值
        log.info("result: \r\n{}", result);
    }

}
