package cn.cc.encreal;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 真实场景的RSA+AES+SHA256完整示例
 * 包含：AES加密原文、RSA加密AES密钥、SHA256+RSA签名、验签、解密全流程
 */
public class RSACompleteExample {
    // 核心算法常量
    private static final String RSA_ALGORITHM = "RSA";
    private static final String AES_ALGORITHM = "AES";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    // 密钥长度
    private static final int RSA_KEY_SIZE = 2048;
    private static final int AES_KEY_SIZE = 128;
    // AES加密模式（ECB/PKCS5Padding是入门常用模式，生产环境推荐GCM）
    private static final String AES_TRANSFORMATION = "AES/ECB/PKCS5Padding";

    private static final String SENDER_PUBLIC_KEY= "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlTQPoqntcoEsj5vEQpCroZedPpT44wQc8UcqHAukOzPoxa7EFWC/qjtykYataexUHIF810q0z03o2gyIZljT7nssWarBODSVTycz9/oMGDO1A3Epahcj9tEF2GAjhFuA1C55OmPk/6ebePg8+iSU0Q4o71snaF90PeAtNia1wDOc7MUypg+jkRbox6u7G1gLAlGweudq2pM457sHuEOs+SCj5azoJGGOMzWIO9RZS/yN/dAVaV9DvFNZKt/vaxP3zdoKhc2vYsE+TFsfspLgbWB3kiSuj5JwSReMPCqZjImzAzUiDVAzcsKxz0p3tAzSNBdejI4lQS0STeGUmdGi2QIDAQAB";
    private static final String SENDER_PRIVATE_KEY= "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCVNA+iqe1ygSyPm8RCkKuhl50+lPjjBBzxRyocC6Q7M+jFrsQVYL+qO3KRhq1p7FQcgXzXSrTPTejaDIhmWNPueyxZqsE4NJVPJzP3+gwYM7UDcSlqFyP20QXYYCOEW4DULnk6Y+T/p5t4+Dz6JJTRDijvWydoX3Q94C02JrXAM5zsxTKmD6ORFujHq7sbWAsCUbB652rakzjnuwe4Q6z5IKPlrOgkYY4zNYg71FlL/I390BVpX0O8U1kq3+9rE/fN2gqFza9iwT5MWx+ykuBtYHeSJK6PknBJF4w8KpmMibMDNSINUDNywrHPSne0DNI0F16MjiVBLRJN4ZSZ0aLZAgMBAAECggEBAITSHJ0cknFdhrKs4rCzIuauFmAQs+M71RwA0v62alP9qNjZ9FpnjxfW5e2gq51gsZvStbTeH0lwPLpnb8FUkA17fwavmGQVg1k8N+onoT6mQRy4TzrPMMfWWVK+er+nyW6ZlHejV8cyiJfOvFGd4RCdf1lclx814j/24aiUGzXmH5zJig1MyDRoE1R4lZ/KnhFEcUcxIQLJWHVLtT1HD4gIzue+rtgfbrobZaABdTINvi5NTdEV3coxokvpzQfC/3fWVO+HVA7K7eFcZX0eOPKYAwbMrR/HXUIalMc4FfeAnR113u769qoX1E+/U2xC55YrPWml8IgxPi/DZ7/FEbUCgYEA0YaF3t3nRJ3EWTmJWPn04RpxQYyrk0ED2Kr3iwyDvRvjRJeOJVT2K3bp7hhmpMUS9JtovL5XZh5r1shK2xls3cJ5q3isOjS6ciB8DCpHRPPHaR/Ys+MyNl3LW7+8hyvHofRka0V3ONT29rXftbKRKBHTHz33nMq4dkv6YZiWB8sCgYEAtkxFECaBNCGwcE0wHyjA9SvdzpGMer7wmGNBHyiNQafxELj3GqVEZiWjKmwz/A/uqpAtWGcxl3hWO9vcmFwqj4eCYjYVKzTZlcfpYmVEakTRuqL4QJA2KsqqMIWkFlH3zb7mtHOHP+7sXNM4L3mTn6sMx84JKOwdvjUaohBvA2sCgYEAl1bMUTsE9cni7fOiIlOBLiq0TX123IjkZ1auMJBcg3amRIAXmx1wJdrhgym60h7K30V1dIcjh85HZD8N2o3V1aPH0DP8app1ZUPTddH/4m0WxvbKxrw/I4fCAEZQvW3A51koQ1dMkw84RuD9cjdFo6SkUvemZ2kBreiZNG9LRtsCgYARG1snxtU9FSQ/hZ7MbcCdwT4RUmtCYYSmaDoGOIH49U+T6CeYIoYmp8WGB51hPAWDkAARWa23rx04hMtQqOV9jpPXr4eRBGaevaHTD/m5lzevMffl5yKOJXKPFxts1MhSUGXJ7UV15G/dzhxpqBkIVL7gSR9m7bFk1grnxm8EfQKBgF/7Gdvs3zPlom3OtgmNE1NG6/XnU/ySbDmsyxYpvlFTDn/8v6YEodQvgfgw6HuvIzVvoYJywFr2RT3NA9mEl38jKC4b1ExOX7UFVnUZxlZrxhr209OUu6/vItTwPZwGA0xguw1/ctATu9QKBAzzZZFy8yKUarMhjhZn7Ns5U31i";

    private static final String RECEIVER_PUBLIC_KEY= "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhi+Btr0I/p8UkmyALszv4FX1Fq0o4bwCpl0V5pKZy2nnwZasssZqw5TnhfFr/alqonnjNop1f8aQt8mG/dpuSI7rJSlTIlzVd+ueM1X0m81ykd5JsBTDF0ythiw3xiJQoZ0C4BPqR9nKSpIN2eqI/LiaXnUK52bPZyhYw8IIOvbtmTU+mQFFz+EUJIlExZuBFF05zkdROgiM7ve2mhhrzYKjwFzJl+IpW3WXFqlwgZB/1sNeDcEuSv4soJf5UNOYeYc9SB3gS7lJDMPzxlCx/IawLMBgyBVOM2mdhLmpFSyv4hcoyObCL2YF8H3fdlOHn764iAwtadmsBjsl7Xs16QIDAQAB";
    private static final String RECEIVER_PRIVATE_KEY= "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCGL4G2vQj+nxSSbIAuzO/gVfUWrSjhvAKmXRXmkpnLaefBlqyyxmrDlOeF8Wv9qWqieeM2inV/xpC3yYb92m5IjuslKVMiXNV3654zVfSbzXKR3kmwFMMXTK2GLDfGIlChnQLgE+pH2cpKkg3Z6oj8uJpedQrnZs9nKFjDwgg69u2ZNT6ZAUXP4RQkiUTFm4EUXTnOR1E6CIzu97aaGGvNgqPAXMmX4ilbdZcWqXCBkH/Ww14NwS5K/iygl/lQ05h5hz1IHeBLuUkMw/PGULH8hrAswGDIFU4zaZ2EuakVLK/iFyjI5sIvZgXwfd92U4efvriIDC1p2awGOyXtezXpAgMBAAECggEAPUh3GmlNMVSgBZuOE1G/gBph2+BrmmTOZQKAXRS5LqCImu5goylhWz+nWZH/+xKjw96c+azUqu2Z7IYao9ddpL+4/vjXogr551Hxp4prM7jpyVrXFpb03rBDU9LgChpj7tuRk5sk1XnPHK2Fq++kbp80yUz1FOfsczWHDKiSL7MoMOdM9yIMQP4uQqiNGbm18IdzIH5+0rmBOUiqWNy28Ab3QAiMn3V5A+//M2dydZFG1msTElUeGbu34yEcpICYYaXS6tw5/WZNvDMJKQ3Ip3k2tBtaUuX67q39oJcL51T8epFng96Fz3bYGIHdAvPznsSNVKZoMQJkKEcXH/+wgQKBgQDgspZ49Z2V7NWjzinYIpl0k8KbUct3bmppG2CW7wqege/pDKGPgXPiMoogeiM/ACt0XuLEThUN+C7iUqQGX1tiSgTjxXiFmxQG5/CZ2HTHdmu9j3DH6qglqp1U8MuBV6Hb0KXt5QPodw4TwyrZZgX6m4IkYIh3hImRYQx3YI9xkQKBgQCY4PrE7uUtHqE3hWp35hCvlsmCdWNVy4MlqJ74T7Cv5GYqcERX04ui6bi1Z3bgSTZXiGKpdQUmmke6dhv6oIws5RrWGstJwh3n6fUMC3HCOnsQm23EuPztYEqmyvReefUXsSdQczEAhEI5omggyOt8WWFdRsuKPwdpIWVBT4vS2QKBgQDT9eOYCyoyiY0mzo4OguT7YX+MgOZMrW3X5KmC98+uoqgr0Wx+MQGd+UrvRAgHoCNdzTxhLb8SxHGFsatlD4Lm/ygJrPf6IMYNlbITjmrq1bzgvnUHQUaI8Z8kx1lRk9btwq8j8It1OQmgftTm6yTsuNFVe3KAPBiBeWkHG6F04QKBgEe0hopmDsIZ8r4K6sQv+bWSARQaoSv1l57LNzz6i8IEW9Md80BKbd1MyKD1VXGfhUiLJNOcmnzfsjMIANmOAhFwqCSyTpVa4hILzy8GEyZprZ09eb/ikGd6Walf2jyR8V5abjkyvlIitrk3rqVp9+HMPnm0ofVG2N/7lPWMLOYJAoGAcnJLrzcQc6rgXGKByesGkhMW8zXJt6nMxOWDtxjMvw1KIsgcAY0fZ/1YuBzVjAVXqvLIkrMN9XASUnGOmmxAYDuqWLreUvMcB7addG5tddFHmGHYQIplo/gKsUmH8OzxQrJkj0ymuR5Y8qNxKdPJYLlE1tU4EjjQKwDkaPdPyTA=";

    public static void main(String[] args) throws Exception {
        // ====================== 1. 生成密钥对 ======================
        // 发送方RSA密钥对（私钥用于签名，公钥给接收方验签）
//        KeyPair senderRsaKeyPair = generateRSAKeyPair();
//        PublicKey senderPublicKey = senderRsaKeyPair.getPublic();
//        PrivateKey senderPrivateKey = senderRsaKeyPair.getPrivate();
//        System.out.println("发送方生成的公钥（Base64编码）：\n" + encodeKey(senderPublicKey));
//        System.out.println("发送方生成的私钥（Base64编码）：\n" + encodeKey(senderPrivateKey));
        PublicKey senderPublicKey = restorePublicKey(SENDER_PUBLIC_KEY);
        PrivateKey senderPrivateKey = restorePrivateKey(SENDER_PRIVATE_KEY);

        // 接收方RSA密钥对（公钥给发送方加密AES密钥，私钥自己解密AES密钥）
//        KeyPair receiverRsaKeyPair = generateRSAKeyPair();
//        PublicKey receiverPublicKey = receiverRsaKeyPair.getPublic();
//        PrivateKey receiverPrivateKey = receiverRsaKeyPair.getPrivate();
//        System.out.println("接收方生成的公钥（Base64编码）：\n" + encodeKey(receiverPublicKey));
//        System.out.println("接收方生成的私钥（Base64编码）：\n" + encodeKey(receiverPrivateKey));
        PublicKey receiverPublicKey = restorePublicKey(RECEIVER_PUBLIC_KEY);
        PrivateKey receiverPrivateKey = restorePrivateKey(RECEIVER_PRIVATE_KEY);

        // 生成临时AES密钥（用于加密原文）
        String aesKey = generateAESKey();
        System.out.println("生成的AES密钥（Base64）：" + aesKey);
        System.out.println("----------------------------------------");

        // ====================== 2. 发送方操作 ======================
        // 待传输的原始明文（敏感数据）
        String plainText = "真实场景：RSA+AES+SHA256";
        System.out.println("原始明文：" + plainText);

        // 步骤1：AES加密明文 → 密文（保证数据机密性）
        String aesCipherText = aesEncrypt(plainText, aesKey);
        System.out.println("AES加密后的密文：\n" + aesCipherText);

        // 步骤2：RSA加密AES密钥（用接收方公钥，保证密钥安全分发）
        String encryptedAesKey = rsaEncrypt(receiverPublicKey, aesKey);
        System.out.println("RSA加密后的AES密钥：\n" + encryptedAesKey);

        // 步骤3：SHA256+RSA生成签名（用发送方私钥，保证身份+完整性）
        String signature = generateSignature(plainText, senderPrivateKey);
        System.out.println("SHA256withRSA数字签名：\n" + signature);
        System.out.println("----------------------------------------");

        // ====================== 3. 传输内容（密文+加密的AES密钥+签名） ======================
        // 实际网络传输中，这三个内容会打包发送给接收方
        String[] transmitData = {aesCipherText, encryptedAesKey, signature};

        // ====================== 4. 接收方操作 ======================
        // 步骤1：RSA解密AES密钥（用接收方私钥）
        String decryptedAesKey = rsaDecrypt(receiverPrivateKey, transmitData[1]);
        System.out.println("RSA解密后的AES密钥：" + decryptedAesKey);

        // 步骤2：AES解密密文 → 还原明文
        String decryptedPlainText = aesDecrypt(transmitData[0], decryptedAesKey);
        System.out.println("AES解密后的明文：" + decryptedPlainText);

        // 步骤3：验证签名（用发送方公钥，验证身份+数据完整性）
        boolean verifyResult = verifySignature(decryptedPlainText, transmitData[2], senderPublicKey);
        System.out.println("签名验证结果：" + (verifyResult ? "✅ 验证通过（身份合法+数据未篡改）" : "❌ 验证失败（数据篡改/签名伪造）"));
    }

    // ====================== RSA 核心方法 ======================
    /**
     * 生成RSA密钥对（公钥+私钥）
     */
    public static KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGenerator.initialize(RSA_KEY_SIZE, new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * RSA加密（公钥加密，用于加密AES密钥）
     * @param publicKey 接收方公钥
     * @param plainText 待加密的短文本（如AES密钥）
     */
    public static String rsaEncrypt(PublicKey publicKey, String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * RSA解密（私钥解密，用于解密AES密钥）
     * @param privateKey 接收方私钥
     * @param encryptedText RSA加密后的Base64字符串
     */
    public static String rsaDecrypt(PrivateKey privateKey, String encryptedText) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    // ====================== SHA256withRSA 签名/验签 ======================
    /**
     * 生成数字签名（发送方私钥+SHA256）
     * @param plainText 原始明文（签名的是明文摘要，而非密文）
     * @param privateKey 发送方私钥
     */
    public static String generateSignature(String plainText, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(plainText.getBytes(StandardCharsets.UTF_8));
        byte[] signBytes = signature.sign();
        return Base64.getEncoder().encodeToString(signBytes);
    }

    /**
     * 验证数字签名（接收方用发送方公钥验签）
     * @param plainText 解密后的明文
     * @param signatureText 签名的Base64字符串
     * @param publicKey 发送方公钥
     */
    public static boolean verifySignature(String plainText, String signatureText, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(plainText.getBytes(StandardCharsets.UTF_8));
        byte[] signBytes = Base64.getDecoder().decode(signatureText);
        return signature.verify(signBytes);
    }

    // ====================== AES 核心方法 ======================
    /**
     * 生成AES密钥（Base64编码，方便存储/传输）
     */
    public static String generateAESKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
        keyGenerator.init(AES_KEY_SIZE, new SecureRandom());
        SecretKey secretKey = keyGenerator.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    /**
     * AES加密（用于加密原始明文）
     * @param plainText 原始明文
     * @param aesKey Base64编码的AES密钥
     */
    public static String aesEncrypt(String plainText, String aesKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(aesKey);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * AES解密（还原明文）
     * @param cipherText AES加密后的Base64字符串
     * @param aesKey Base64编码的AES密钥
     */
    public static String aesDecrypt(String cipherText, String aesKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(aesKey);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = Base64.getDecoder().decode(cipherText);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    // ====================== 密钥序列化/反序列化（扩展方法） ======================
    /**
     * Base64编码公钥（方便传输/存储）
     */
    public static String encodePublicKey(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    /**
     * 从Base64字符串还原公钥
     */
    public static PublicKey restorePublicKey(String base64PublicKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64PublicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 将密钥（公钥/私钥）转为Base64编码字符串（方便存储和传输）
     */
    public static String encodeKey(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    /**
     * Base64编码私钥（方便传输/存储）
     */
    public static String encodePrivateKey(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    /**
     * 从Base64字符串还原私钥
     */
    public static PrivateKey restorePrivateKey(String base64PrivateKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64PrivateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }
}
