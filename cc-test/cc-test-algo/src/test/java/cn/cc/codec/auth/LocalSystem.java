package cn.cc.codec.auth;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;

/**
 * 本地系统：生成HMAC认证凭证
 */
public class LocalSystem {
    // 预共享密钥（实际应用中应安全存储，定期轮换）
    private static final String SHARED_SECRET = "ThisIsASecretKey_ShouldBeStoredSecurely";
    // HMAC算法
    private static final String HMAC_ALGORITHM = "HmacSHA256";
    // 本地系统唯一标识
    private static final String SYSTEM_ID = "LOCAL_SYSTEM_001";

    /**
     * 生成HMAC认证凭证
     * @param username 已在本地认证的用户名
     * @return 包含原始信息和HMAC的认证字符串
     */
    public static String generateAuthCredential(String username) {
        try {
            // 1. 生成包含时间戳的原始信息（精确到秒，防止重放攻击）
            long timestamp = Instant.now().getEpochSecond();
            String rawData = String.format("%s|%s|%d", username, SYSTEM_ID, timestamp);
            
            // 2. 计算HMAC
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(SHARED_SECRET.getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM);
            mac.init(secretKey);
            byte[] hmacBytes = mac.doFinal(rawData.getBytes(StandardCharsets.UTF_8));
            String hmac = Base64.getEncoder().encodeToString(hmacBytes);
            
            // 3. 组合原始数据和HMAC作为凭证（格式：rawData|hmac）
            return rawData + "|" + hmac;
            
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to generate HMAC credential", e);
        }
    }

    public static void main(String[] args) {
        // 模拟用户已在本地系统通过认证
        String username = "user123";
        
        // 生成认证凭证
        String credential = generateAuthCredential(username);
        System.out.println("生成的认证凭证: " + credential);
        
        // 这里会将凭证发送给云服务（实际应用中通过HTTPS传输）
    }
}
