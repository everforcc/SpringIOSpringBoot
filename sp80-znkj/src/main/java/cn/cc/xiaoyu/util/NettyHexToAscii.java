package cn.cc.xiaoyu.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.bytes.ByteArrayDecoder;

public class NettyHexToAscii {
    public static void main(String[] args) {
        String hexStr = "393836303436313136313937323737343239300B";
        // 1. 十六进制字符串转 byte[]（核心：每两位转一个字节）
        byte[] byteArray = hexStringToByteArray(hexStr);

        // 2. 模拟 Netty 中 ByteArrayDecoder 解码（实际会自动处理，这里演示逻辑）
        ByteArrayDecoder decoder = new ByteArrayDecoder();
        ByteBuf buf = Unpooled.wrappedBuffer(byteArray);
        // 3. 字节数组转 ASCII 字符
        StringBuilder asciiResult = new StringBuilder();
        // todo 记录一下
        /**
         * ASCII码对应：当字节值在 0-127 范围内时，转换后的 char 确实对应相应的 ASCII 字符
         * 数值直接映射：字节值作为字符的 Unicode 码点，0-127 范围内与 ASCII 码相同
         */
        for (byte b : byteArray) {
            asciiResult.append((char) b);
        }

        System.out.println(asciiResult.toString());
        System.out.println("Netty 场景转换结果：" + escapeControlChars(asciiResult.toString()));
    }

    // 工具方法：十六进制字符串转 byte[]
    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    // 复用之前的控制字符转义方法
    private static String escapeControlChars(String str) {
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
}
