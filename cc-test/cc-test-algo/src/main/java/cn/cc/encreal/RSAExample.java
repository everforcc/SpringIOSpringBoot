package cn.cc.encreal;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * RSA SHA-256 正确流程
 * RSA非对称加密示例
 * 包含：密钥生成、公钥加密、私钥解密、私钥签名、公钥验签
 */
public class RSAExample {
    // RSA算法名称
    private static final String ALGORITHM = "RSA";
    // 签名算法（SHA256withRSA是常用的安全签名算法）
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    // 密钥长度（1024/2048位，2048位更安全）
    private static final int KEY_SIZE = 2048;

    private static String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAz4sUoGml381zaSPtDNVPSW7+yWEHg0/sIs437eoNiJF4wIvAZb9hWnwnLkiLcxRkcVL9QBvTu8mU//hiyJCHZxqf5Iozu21ITyJNDNgEXNqsLJWik/5MhsZ3wHydmiXIojaD8dHgeaK0mQJ1cwpOSUXpozy68tnOM/rjJihKWKeZyeBlCU5ukQLvtWobxi6THJmPetKCdN9rc7wXljneBB5+eRt9aISbmxCx4Xzqto8a1nuLARE7D/HMK92g88DFWGMzfKxUVB7YV0GeLXOt740ly67W6V3K/Txjat76QgSm9ATV9APslial6uWNeIck804dHmHLG3urByUZ4pH0xwIDAQAB";
    private static String PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDPixSgaaXfzXNpI+0M1U9Jbv7JYQeDT+wizjft6g2IkXjAi8Blv2FafCcuSItzFGRxUv1AG9O7yZT/+GLIkIdnGp/kijO7bUhPIk0M2ARc2qwslaKT/kyGxnfAfJ2aJciiNoPx0eB5orSZAnVzCk5JRemjPLry2c4z+uMmKEpYp5nJ4GUJTm6RAu+1ahvGLpMcmY960oJ032tzvBeWOd4EHn55G31ohJubELHhfOq2jxrWe4sBETsP8cwr3aDzwMVYYzN8rFRUHthXQZ4tc63vjSXLrtbpXcr9PGNq3vpCBKb0BNX0A+yWJqXq5Y14hyTzTh0eYcsbe6sHJRnikfTHAgMBAAECggEASJVTeWZ5ZHPt0O+Hj91qBTyUTsloXzwsUV/dn48jfIeju3MkrnDoE0UoTr+ARZb0Kc3kU+9ndV1ihsLPiWVCdYSSNtM+lGX7y/ErETrbC75OSKlsWmWtTdVLPD5Yv/hG2rRGIORNpTyJ/JknijwodOfGdhhfKV+TBgrQNiARID+bnvNlRi5eKGifbvTPoNORlY+tyPGIZSzPo4yO+HI7rv6wJwHJDMEKRWYQRs2fwt+Gf2KrJk7hlvRpQQC8DkyksooZFq5DQgUD8uTpiVUAEG+9JRbcjHGEp8ZPRPV1vPyBZmtzLz4ak2cMI+abfrydzhVildhh+1FFGM7Pi43JgQKBgQD3dtPdQcFA/gq7/8pE9gMn9siq8RowmTALUVfdMu41Irq6dtUmR90AmF2mbVmSr7Tj9X7cDf4XJZlwiXxY2B5BWULjEgS3qgWb/nOJy2+BJiT+n1Yx3iupBoSDNSqu+HERMmpmlKdWIEOp8i6Uu4UtN7c7sy/Aw/Gzt5uk8i8yqQKBgQDWs73DF9cdaiX0ASuxwe0zd6aAXWhsDapRYwmBXl4cIjwDNUZAFyjpeyUtFftrJJ2HCrPU0ycRQ7jE1Uor+N5CmS+7XXADD7D7YykVByjLa1GpqnhCAJWSW2Mtu5fwTCSJH+g+pPRKfhbQmg7QSl1qbvolSroKHzWcaggRXzcB7wKBgGPpWgjO75Op/fOBhszo9sU/4i0CQyyz7K3lHbB1S6+wYeu+5rgZ86S137m+FJgg0Mvo5ls6WwhFafoByG6mczC3NUYwlC8DOliNF2De/hoKqI0VD9v6c24+72l6Ic2bCSuES2k3Q4iGZktLOHtcapyjDhvQcMtHUWLaEsE59sxhAoGAUU01EZiIhAH7Jz/+PMzTtsabTUaR0UEHetRG+C808fBgy1I0LPbYLjnMpRsK3tK5uDZsTK48wAPmBttBwA74kz2Q3qGOVgpuGXO+TdEtOIKGJPqBmXTBLu/eSaYc7gjXWPm1J2utejvvBcUg0O0oHLKzlMWMV2LkGTvpnDJzEQ8CgYEAueBv9VBvm2pIaevicRaK+lonVWyRE2dG0qRQeHJoBAF82OR9yT4n1pgSiMOKv3yJacA3Nd2XHORp7rESvZswIeLGNeMmkI3Aai5CWoV0Znb8b6+sncxBt/SLAef3rddvAY5m96rWU2++Hl9LOJC1m9jV3arq4ySRyr2WYUfrZ/M=";

    public static void main(String[] args) throws Exception {
        // 1. 生成RSA密钥对（公钥+私钥）
//        KeyPair keyPair = generateRSAKeyPair();
//        PublicKey publicKey = keyPair.getPublic();
//        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = restorePublicKey(PUBLIC_KEY);
        PrivateKey privateKey = restorePrivateKey(PRIVATE_KEY);
                //
        System.out.println("生成的公钥（Base64编码）：\n" + encodeKey(publicKey));
        //
        System.out.println("生成的私钥（Base64编码）：\n" + encodeKey(privateKey));
        System.out.println("----------------------------------------");

        // 待加密的原文（示例常用短文本，RSA不适合加密大文件）
        String plainText = "RSA加密示例";
        System.out.println("原文：" + plainText);

        // 2. 公钥加密
        String encryptedText = encryptByPublicKey(plainText, publicKey);
        System.out.println("公钥加密后（Base64编码）：\n" + encryptedText);

        // 3. 私钥解密
        String decryptedText = decryptByPrivateKey(encryptedText, privateKey);
        System.out.println("私钥解密后：" + decryptedText);
        System.out.println("----------------------------------------");

        // 4. 私钥生成签名
        String signature = sign(plainText, privateKey);
        System.out.println("私钥签名（Base64编码）：\n" + signature);

        // 5. 公钥验证签名
        boolean verifyResult = verify(plainText, signature, publicKey);
        System.out.println("公钥验签结果：" + verifyResult);
    }

    /**
     * 生成RSA密钥对
     */
    public static KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        // 初始化密钥生成器，指定密钥长度和随机源
        keyPairGenerator.initialize(KEY_SIZE, new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 公钥加密
     * @param plainText 明文
     * @param publicKey 公钥
     */
    public static String encryptByPublicKey(String plainText, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 初始化加密模式：公钥加密
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        // 加密后得到字节数组，转Base64方便传输/存储
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * 私钥解密
     * @param encryptedText 加密后的Base64字符串
     * @param privateKey 私钥
     */
    public static String decryptByPrivateKey(String encryptedText, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 初始化解密模式：私钥解密
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        // 先解码Base64，再解密
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    /**
     * 私钥签名
     * @param plainText 要签名的原文
     * @param privateKey 私钥
     */
    public static String sign(String plainText, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化签名器：私钥签名
        signature.initSign(privateKey);
        // 传入原文字节
        signature.update(plainText.getBytes(StandardCharsets.UTF_8));
        // 生成签名字节数组，转Base64
        byte[] signBytes = signature.sign();
        return Base64.getEncoder().encodeToString(signBytes);
    }

    /**
     * 公钥验签
     * @param plainText 原文
     * @param signatureText 签名的Base64字符串
     * @param publicKey 公钥
     */
    public static boolean verify(String plainText, String signatureText, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化验证器：公钥验签
        signature.initVerify(publicKey);
        // 传入原文字节
        signature.update(plainText.getBytes(StandardCharsets.UTF_8));
        // 解码签名并验证
        byte[] signBytes = Base64.getDecoder().decode(signatureText);
        return signature.verify(signBytes);
    }

    /**
     * 将密钥（公钥/私钥）转为Base64编码字符串（方便存储和传输）
     */
    public static String encodeKey(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    /**
     * 从Base64编码字符串恢复公钥
     */
    public static PublicKey restorePublicKey(String base64PublicKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64PublicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 从Base64编码字符串恢复私钥
     */
    public static PrivateKey restorePrivateKey(String base64PrivateKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64PrivateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }
}
