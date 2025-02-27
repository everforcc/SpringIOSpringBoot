package org.apache.commons.codec;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * sha256 加密示例
 */
@Slf4j
public class SHAApacheTest {

    private static final String SHA_256 = "SHA-256";

    /**
     * apache sha256 工具类
     *
     * @param passWord 密码
     */
    public static void sha256HexTest(String passWord) {
        String result = DigestUtils.sha256Hex(passWord.getBytes(StandardCharsets.UTF_8));
        // 数据库存的值
        log.info("sha256: \r\n{}", result);
    }

    /**
     * 带加密流程的
     *
     * @param passWord 密码
     */
    public static void sha256HexFlowTest(String passWord) {
        byte[] bytes = passWord.getBytes();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(SHA_256);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert messageDigest != null;
        byte[] resultBytes = messageDigest.digest(bytes);
        String result = Hex.encodeHexString(resultBytes);
        // 数据库存的值
        log.info("sha256 flow: \r\n{}", result);
    }

    /**
     * 加盐的加密
     * 加盐分为前缀 和后缀
     * 这个是后缀
     *
     * @param passWord 密码
     * @param saltStr  加盐 比如用户密码先生成随机盐
     */
    public static void sha256HexWithAppendSaltTest(String passWord, String saltStr) {
        String result = DigestUtils.sha256Hex((passWord + saltStr).getBytes(StandardCharsets.UTF_8));
        // 数据库存的值
        log.info("sha256 加盐: \r\n{}", result);
    }

}
