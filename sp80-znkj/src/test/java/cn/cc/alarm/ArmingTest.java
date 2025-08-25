package cn.cc.alarm;

import org.junit.Test;

import java.util.Base64;

public class ArmingTest {

    /**
     * FFFF000FF0FFFFFF000FF0FFFFFF000FF0FFFFFF000FF0FFFFFF000FF0FFFFFF000FF0FFFFFF000FF0FF
     * //8AD/D///8AD/D///8AD/D///8AD/D///8AD/D///8AD/D///8AD/D/
     */
    @Test
    public void test() {

        StringBuilder sb = new StringBuilder(336);
        for (int d = 0; d < 7; d++) {
            for (int h = 0; h < 24; h++) {
                if (h < 8) {            // 0-8 布防
                    sb.append("11"); // 11
                } else if (h < 12) {    // 8-12 撤防
                    sb.append("00"); // 00
                } else if (h < 14) {    // 12-14 布防
                    sb.append("00"); // 11
                } else if (h < 18) {    // 14-18 撤防
                    sb.append("00"); // 00
                } else {                // 18-24 布防
                    sb.append("00"); // 11
                }
            }
        }
        String bits01 = sb.toString(); // 长度应为 336

        // 111111111111111100000000111100000000111111111111111111111111111100000000111100000000111111111111111111111111111100000000111100000000111111111111111111111111111100000000111100000000111111111111111111111111111100000000111100000000111111111111111111111111111100000000111100000000111111111111111111111111111100000000111100000000111111111111
        System.out.println(bits01);
        byte[] out = new byte[42];
        for (int i = 0; i < 42; i++) {
            int b = 0;
            for (int k = 0; k < 8; k++) {
                int idx = i * 8 + k;
                if (idx < bits01.length() && bits01.charAt(idx) == '1') {
                    // todo 用“|=”是累加设位，多次命中会合并；若用“=”会覆盖之前已设的位。
                    // Java 的移位在 int 上完成，最后强转成 byte，仅截取低 8 位即可，符号位不影响存储的原始比特。
                    // 低位在前”(LSB-first by bit) ,右边是前
                    b |= (1 << k); // 低位在前，匹配 BitSet 的位序
                }
            }
            out[i] = (byte) b;
        }
        String base64 = Base64.getEncoder().encodeToString(out);
        System.out.println("base64: " + base64);
        // base64转16进制
        String hex = "";
        for (byte b : out) {
            hex += String.format("%02x", b);
        }
        System.out.println("hex: " + hex);

    }

    @Test
    public void test0() {
        String bits01 = "10111111";
        System.out.println("原字符串: " + bits01);
        byte[] out = new byte[1];
        for (int i = 0; i < 1; i++) {
            int b = 0;
            for (int k = 0; k < 8; k++) {
                int idx = i * 8 + k;
                if (idx < bits01.length() && bits01.charAt(idx) == '1') {
                    // todo 用“|=”是累加设位，多次命中会合并；若用“=”会覆盖之前已设的位。
                    // Java 的移位在 int 上完成，最后强转成 byte，仅截取低 8 位即可，符号位不影响存储的原始比特。
                    // 低位在前”(LSB-first by bit) ,右边是前
                    b |= (1 << k); // 低位在前，匹配 BitSet 的位序
                    System.out.println("位置k: " + k);
                }
                System.out.println("二进制b: " + Integer.toBinaryString(b));
            }
            System.out.println("---------------------------");
            out[i] = (byte) b;
            System.out.println("byte-b:" + b);
            System.out.println("---------------------------");
            int intB = b&0xFF;
            System.out.println("intB:" + intB);
            String binaryB = Integer.toBinaryString(intB);
            System.out.println("---------------------------");
            System.out.println("binary 和字符串8位 左右位置被翻转");
            System.out.println("binaryB:" + binaryB);
            for(int j = 0; j < 8; j++){
                int bj =(b >> (j % 8)) & 1;
                System.out.println("bj:" + bj);
            }
        }
    }

    @Test
    public void base64ToStr() {
        String base64 = "//8AD/D///8AD/D///8AD/D///8AD/D///8AD/D///8AD/D///8AD/D/"; // 42字节的Base64
        byte[] bytes = Base64.getDecoder().decode(base64);

        // 确保长度固定为42，不足在右侧补0
        if (bytes.length < 42) {
            byte[] fixed = new byte[42];
            System.arraycopy(bytes, 0, fixed, 0, bytes.length);
            bytes = fixed;
        }


        // 42字节 -> 336位数组
        int[] arr = new int[336];
        for (int i = 0; i < 336; i++) {
            // Java中 byte 是有符号类型（-128到127），而我们希望得到0-255的无符号值，所以通过 & 0xFF 来屏蔽高位，只保留低8位的有效数据。
            // 简单来说，& 在这里是用来提取特定位的值，确保得到正确的无符号字节值。
            int b = bytes[i / 8] & 0xFF;
            // todo 一个int 占4字节，一个字节占8位，一个字节的二进制位序为0-7，上面分好组了
            // 从右向左获取
            arr[i] = (b >> (i % 8)) & 1; // 低位在前
        }


        // 数组 -> 01字符串
        StringBuilder sb = new StringBuilder(336);
        for (int v : arr) {
            sb.append(v == 1 ? '1' : '0');
        }
        String bits01 = sb.toString();
        System.out.println(bits01);
    }


}
