package security;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Slf4j
public class SHAJDKTest {

    private static final String SHA_256 = "SHA-256";

    /**
     * jdk sha256 示例
     *
     * @param str 参数
     */
    public static void sha256(String str) {
        try {
            // 需要计算哈希值的字符串

            // 创建SHA-256哈希算法的MessageDigest对象
            MessageDigest sha256 = MessageDigest.getInstance(SHA_256);

            // 使用hash()方法计算字符串的哈希值
            byte[] hash = sha256.digest(str.getBytes(StandardCharsets.UTF_8));

            // 将哈希值转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            // 打印结果
            log.info("SHA-256 Hash: {}", hexString.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
