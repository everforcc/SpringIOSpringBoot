package cn.cc.codec;

import jdk.java.security.RSACryptoJDKTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.security.KeyPair;

@Slf4j
public class RSATest {

    @Test
    public void rsaTest() {
        String str = "Hello, World!";
        // 生成RSA密钥对
        KeyPair keyPair = null;
        try {
            keyPair = RSACryptoJDKTest.generateRSAKeyPair();

            String publicKeyStr = RSACryptoJDKTest.publicKeyStr(keyPair);
            String privateKeyStr = RSACryptoJDKTest.privateKeyStr(keyPair);

            // 公钥加密
            String encryptedText = RSACryptoJDKTest.encrypt(str, keyPair.getPublic());
            log.info("Encrypted Text: {}", encryptedText);

            // 公钥加密
            encryptedText = RSACryptoJDKTest.encrypt(str, publicKeyStr);
            log.info("Encrypted Text: {}", encryptedText);

            // 私钥解密
            String decryptedText = RSACryptoJDKTest.decrypt(encryptedText, keyPair.getPrivate());
            log.info("Decrypted Text: {}", decryptedText);

            // 私钥解密
            decryptedText = RSACryptoJDKTest.decrypt(encryptedText, privateKeyStr);
            log.info("Decrypted Text: {}", decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
