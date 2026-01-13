package cn.cc.xiaoyu.util.charutil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 字符数值转 ASCLL
 */
@Slf4j
public class AsciiUtils {

    /**
     * 各种类型转 ASCII
     * - [x] byte[]
     * - [x] byte
     * - [x] int
     * char
     */
    public static void main(String[] args) {
        // 你的十六进制表示的 byte 数组
//        byte[] bytes = new byte[]{
//                0x38, 0x39, 0x38, 0x36, 0x30,
//                0x34, 0x36, 0x31, 0x31, 0x36,
//                0x31, 0x39, 0x37, 0x32, 0x37,
//                0x37, 0x34, 0x32, 0x39, 0x30
//        };
        byte[] initBytes = new byte[]{
                0x38, 0x39, 0x38, 0x36, 0x30,
                0x34, 0x36, 0x31, 0x31, 0x36,
                0x31, 0x39, 0x37, 0x32, 0x37,
                0x37, 0x34, 0x32, 0x39, 0x30
        };
        ByteBuf byteBuf = Unpooled.copiedBuffer(initBytes);
        byte[] bytes = ByteBufUtil.getBytes(byteBuf, 0, 19);

//        hexByteAsciiConvert(bytes);
//        byteToASCII(bytes);
        demoASCII();

    }

    public static void demoASCII() {
        // 1. 字符转ASCII数值（自动类型转换）
        char ch = '8'; // 定义一个字符
        int asciiValue = ch; // char自动转为int，得到对应的ASCII值
        System.out.println("字符 '" + ch + "' 对应的ASCII值: " + asciiValue); // 输出：65

        // 2. ASCII数值转字符（强制类型转换）
        int num = 0x38; // 定义一个ASCII数值（对应小写a）
        char charFromAscii = (char) num; // int强制转为char
        System.out.println("ASCII值 " + num + " 对应的字符: " + charFromAscii); // 输出：a

        // 扩展：遍历打印常用ASCII字符（0-127）
        System.out.println("\n常用ASCII字符对照表（部分）：");
        for (int i = 32; i <= 126; i++) { // 32是空格，126是~，覆盖可见字符
            System.out.print(i + " -> " + (char) i + "  ");
            if (i % 10 == 0) { // 每10个换行，方便查看
                System.out.println();
            }
        }
    }

    /**
     * @param bytes 转 String 的三种方式
     */
    public static void hexByteAsciiConvert(byte[] bytes) {

    }

    /**
     * 1. 工具方法：十六进制字符串转 byte[]
     * "393836303436313136313937323737343239300B"
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    // 复用之前的控制字符转义方法

    /**
     *
     */
    public static String escapeControlChars(String str) {
        StringBuilder escaped = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (c < 32 || c > 126) {
                escaped.append(String.format("\\x%02X", (int) c));
            } else {
                escaped.append(c);
            }
        }
        return escaped.toString();
    }

    /**
     * 2. 模拟 Netty 中 ByteArrayDecoder 解码（实际会自动处理，这里演示逻辑）
     */
    private static void demoNetty(byte[] byteArray) {
        ByteArrayDecoder decoder = new ByteArrayDecoder();
        ByteBuf buf = Unpooled.wrappedBuffer(byteArray);
    }



    /**
     * 方式2：String 构造方法（极简，推荐）
     */
    public static void hexByteAsciiConvert_2(byte[] bytes) {
        String result = new String(bytes, StandardCharsets.US_ASCII);
        System.out.println("String 直接转换结果：" + result);
    }

    /**
     * 可选：打印每个元素的十六进制、十进制、对应字符（核对用）
     */
    public static void hexByteAsciiConvert_3(byte[] bytes) {
        System.out.println("\n逐个核对：");
        for (byte b : bytes) {
            System.out.printf("0x%02X（十六进制）= %d（十进制） → %c（字符）\n", b, b, b);
        }
    }

    /**
     * todo-2 不可以
     */
    // 将ByteBuf转换为十六进制字符串
    public static String byteBufToHex(ByteBuf byteBuf) {
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        return hexByteAsciiConvert_1(bytes);
    }

    /**
     * todo-1 可以
     * @param in
     */
    public static void printByteBuf(ByteBuf in) {
        byte [] bytes = new byte[in.readableBytes()];
        in.readBytes(bytes);
        log.info("响应数据内容: {}",hexString(bytes));
        in.resetReaderIndex();
    }

    /**
     * 十六进制表示
     */
    public static List<String> hexString(byte[] bytes) {
        List<String> strings = new ArrayList<>();
        for (byte aByte : bytes) {
            String hexString = Integer.toHexString(aByte & 0xff);
            strings.add(hexString);
        }
        return strings;
    }
    /**
     * ASCII码对应：当字节值在 0-127 范围内时，转换后的 char 确实对应相应的 ASCII 字符
     * 数值直接映射：字节值作为字符的 Unicode 码点，0-127 范围内与 ASCII 码相同
     */
    public static String hexByteAsciiConvert_1(byte[] bytes) {
        // 方式1：循环逐个转换（直观）
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append((char) b); // 核心：byte（ASCII码）转 char
            // result.append(String.format("%02x ", b));
        }
        System.out.println("循环转换结果：" + sb.toString());
        return sb.toString();
    }

}
