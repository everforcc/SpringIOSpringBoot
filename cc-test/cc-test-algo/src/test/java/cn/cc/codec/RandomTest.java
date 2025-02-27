package cn.cc.codec;

import org.junit.Test;
import security.SecureRandomTest;

import java.security.SecureRandom;

public class RandomTest {

    @Test
    public void random() {
        // 创建一个SecureRandom实例
        SecureRandom random = new SecureRandom();

        // 创建一个字节数组来存储随机数据
        byte[] bytes = new byte[2]; // 16位等于2字节

        // 生成随机数并存储到字节数组
        random.nextBytes(bytes);

        // 转换为16进制字符串输出
        String hexString = SecureRandomTest.bytesToHex(bytes);
        System.out.println("16位随机数（16进制）: " + hexString);
    }

}
