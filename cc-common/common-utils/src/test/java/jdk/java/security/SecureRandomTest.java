package jdk.java.security;

import java.security.SecureRandom;

public class SecureRandomTest {
    public static void main(String[] args) {
        // 创建一个SecureRandom实例
        SecureRandom random = new SecureRandom();

        // 创建一个字节数组来存储随机数据
        byte[] bytes = new byte[2]; // 16位等于2字节

        // 生成随机数并存储到字节数组
        random.nextBytes(bytes);

        // 转换为16进制字符串输出
        String hexString = bytesToHex(bytes);
        System.out.println("16位随机数（16进制）: " + hexString);
    }

    // 将字节数组转换为16进制字符串
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}