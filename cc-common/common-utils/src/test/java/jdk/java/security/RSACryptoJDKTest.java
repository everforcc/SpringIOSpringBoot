package jdk.java.security;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Slf4j
public class RSACryptoJDKTest {

    private static final String RSA = "RSA";

    private static final int keysize = 2048;

    /**
     * 生成密钥对
     *
     * @return 密钥对
     * @throws Exception
     */
    public static KeyPair generateRSAKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
        keyPairGenerator.initialize(keysize, new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * publicKeyStr
     *
     * @param keyPair 密钥对
     * @return publicKeyStr
     */
    public static String publicKeyStr(KeyPair keyPair) {
        // 将公钥转换为Base64编码的字符串
        String publicKeyStr = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        log.info("publicKeyStr: {}", publicKeyStr);
        return publicKeyStr;
    }

    /**
     * privateKeyStr
     *
     * @param keyPair 密钥对
     * @return privateKeyStr
     */
    public static String privateKeyStr(KeyPair keyPair) {
        // 将公钥转换为Base64编码的字符串
        String privateKeyStr = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        log.info("privateKeyStr: {}", privateKeyStr);
        return privateKeyStr;
    }

    /**
     * publicKeyStr 转 PrivateKey
     *
     * @param keyString 私钥
     * @return PrivateKey
     * @throws Exception
     */
    public static PrivateKey decodePrivateKey(String keyString) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(keyString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * privateKeyStr 转 PublicKey
     *
     * @param keyString 公钥
     * @return PublicKey
     * @throws Exception
     */
    public static PublicKey decodePublicKey(String keyString) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(keyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 加密
     *
     * @param text      待加密文本
     * @param publicKey publicKey
     * @return 加密结果
     * @throws Exception
     */
    public static String encrypt(String text, java.security.PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(text.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * 加密
     *
     * @param text      待加密文本
     * @param publicKey str
     * @return 加密结果
     * @throws Exception
     */
    public static String encrypt(String text, String publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, decodePublicKey(publicKey));
        byte[] encryptedBytes = cipher.doFinal(text.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * 解密
     *
     * @param encryptedText 待解密文本
     * @param privateKey    privateKey
     * @return 解密结果
     * @throws Exception
     */
    public static String decrypt(String encryptedText, java.security.PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }

    /**
     * 解密
     *
     * @param encryptedText 待解密文本
     * @param privateKey    str
     * @return 解密结果
     * @throws Exception
     */
    public static String decrypt(String encryptedText, String privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, decodePrivateKey(privateKey));
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }

}
