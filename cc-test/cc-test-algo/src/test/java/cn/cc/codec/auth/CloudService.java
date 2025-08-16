package cn.cc.codec.auth;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 云服务：验证HMAC认证凭证并生成Token
 */
public class CloudService {
    // 与本地系统一致的预共享密钥
    private static final String SHARED_SECRET = "ThisIsASecretKey_ShouldBeStoredSecurely";
    // HMAC算法
    private static final String HMAC_ALGORITHM = "HmacSHA256";
    // 凭证有效期（5分钟，单位：秒）
    private static final long CREDENTIAL_EXPIRY_SECONDS = 300;
    // 已注册的本地系统ID（实际应用中从数据库加载）
    private static final Map<String, Boolean> VALID_SYSTEM_IDS = new HashMap<>();
    // 已注册的用户（实际应用中从数据库加载）
    private static final Map<String, Boolean> VALID_USERS = new HashMap<>();

    static {
        // 初始化：添加合法的本地系统ID和用户
        VALID_SYSTEM_IDS.put("LOCAL_SYSTEM_001", true);
        VALID_SYSTEM_IDS.put("LOCAL_SYSTEM_002", true);
        
        VALID_USERS.put("user123", true);
        VALID_USERS.put("admin456", true);
    }

    /**
     * 验证HMAC凭证并生成访问Token
     * @param credential 本地系统生成的认证凭证
     * @return 访问Token（验证失败返回null）
     */
    public static String verifyAndGenerateToken(String credential) {
        try {
            // 1. 解析凭证
            String[] parts = credential.split("\\|");
            if (parts.length != 4) {
                System.out.println("凭证格式错误");
                return null;
            }
            
            String username = parts[0];
            String systemId = parts[1];
            long timestamp;
            String receivedHmac = parts[3];
            
            try {
                timestamp = Long.parseLong(parts[2]);
            } catch (NumberFormatException e) {
                System.out.println("时间戳格式错误");
                return null;
            }
            
            // 2. 验证系统ID合法性
            if (!VALID_SYSTEM_IDS.containsKey(systemId)) {
                System.out.println("非法的本地系统ID: " + systemId);
                return null;
            }
            
            // 3. 验证用户存在性
            if (!VALID_USERS.containsKey(username)) {
                System.out.println("用户不存在: " + username);
                return null;
            }
            
            // 4. 验证凭证时效性（防止过期凭证被滥用）
            long currentTime = Instant.now().getEpochSecond();
            if (currentTime - timestamp > CREDENTIAL_EXPIRY_SECONDS) {
                System.out.println("凭证已过期");
                return null;
            }
            
            // 5. 验证HMAC（确保凭证未被篡改且来自合法系统）
            String rawData = String.format("%s|%s|%d", username, systemId, timestamp);
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(SHARED_SECRET.getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM);
            mac.init(secretKey);
            byte[] computedHmacBytes = mac.doFinal(rawData.getBytes(StandardCharsets.UTF_8));
            String computedHmac = Base64.getEncoder().encodeToString(computedHmacBytes);
            
            if (!computedHmac.equals(receivedHmac)) {
                System.out.println("HMAC验证失败，凭证可能被篡改或来自非法系统");
                return null;
            }
            
            // 6. 所有验证通过，生成Token（实际应用中使用JWT等标准格式）
            String token = generateToken(username);
            System.out.println("验证成功，生成Token: " + token);
            return token;
            
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            System.err.println("验证过程出错: " + e.getMessage());
            return null;
        }
    }

    /**
     * 生成访问Token（简化实现，实际应用中使用JWT等）
     */
    private static String generateToken(String username) {
        // 实际应用中应使用JWT库生成包含过期时间、用户信息的Token
        return "TOKEN_" + System.currentTimeMillis() + "_" + username;
    }

    public static void main(String[] args) {
        // 模拟接收本地系统发送的凭证（实际应用中通过HTTPS接收）
        String validCredential = "user123|LOCAL_SYSTEM_001|1755331058|AWWMP/9/awfWG8Dkg1ssqzTGAtE9xc/s6LM3D3YDflQ="; // 替换为LocalSystem生成的实际凭证
        verifyAndGenerateToken(validCredential);
        
        // 测试无效凭证（可选）
        String invalidCredential = "user123|LOCAL_SYSTEM_001|1692508800|wrongHmac";
        verifyAndGenerateToken(invalidCredential);
    }
}
