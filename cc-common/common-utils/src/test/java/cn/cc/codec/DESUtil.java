package cn.cc.codec;

import jdk.javax.crypto.DESJDKUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.crypto.SecretKey;
import java.util.Base64;

@Slf4j
public class DESUtil {

    @Test
    public void aesTest() {
        String text = "Hello World!";
        // 生成密钥
        SecretKey key = null;
        try {
            log.info("原始数据: {}", text);
            key = DESJDKUtil.generateKey();
            log.info("生成的key: {}", key);
            // 加密
            byte[] encryptedText = DESJDKUtil.encrypt(key, text.getBytes());
            log.info("加密后: {}", Base64.getEncoder().encodeToString(encryptedText));
            // 解密
            byte[] decryptedText = DESJDKUtil.decrypt(key, encryptedText);
            log.info("解密后: {}", new String(decryptedText));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
