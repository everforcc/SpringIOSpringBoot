package cn.cc.codec;

import jdk.javax.crypto.AESJDKUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class AESUtil {

    @Test
    public void aesTest() {
        String originalText = "Hello World! 啊啊啊";
        String key = null;
        try {
            log.info("原始数据: {}", originalText);
            key = AESJDKUtil.generateAESKey();
            log.info("生成的key: {}", key);
            String encryptedText = AESJDKUtil.encryptAES(originalText, key);
            log.info("加密后: {}", encryptedText);
            // 解密操作
            String decryptedText = AESJDKUtil.decryptAES(encryptedText, key);
            log.info("解密后: {}", decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
