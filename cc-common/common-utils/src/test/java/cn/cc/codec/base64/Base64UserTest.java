package cn.cc.codec.base64;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * base64简单实现过程
 */
@Slf4j
public class Base64UserTest {

    private static final byte[] STANDARD_ENCODE_TABLE = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
    };

    /**
     * Byte used to pad output.
     */
    protected static final byte PAD_DEFAULT = '='; // Allow static access to default

    /**
     * todo 中文转码有问题
     */
    public static String userBase64(String str) {

        log.info("基础字节 STANDARD_ENCODE_TABLE: {}", new String(STANDARD_ENCODE_TABLE));

        // 字符串转换为字节数组
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
//        byte[] bytes = str.getBytes();
        int bytesLength = bytes.length;
        log.info("bytesLength: {}", bytesLength);
        /*
         * 二进制转换后的字符串
         */
        StringBuffer binaryStringBuffer = null;
        // 转换后的base64
        StringBuffer base64Result = new StringBuffer();
        // 需要几个 "="
        int end_num = 0;
        for (int i = 0; i < bytesLength; i += 3) {
            binaryStringBuffer = new StringBuffer();
            int a = bytes[i];
            int b;
            int c = 0;
            if ((i + 1) == bytesLength) {
                b = 0;
                end_num = 2;
            } else {
                b = bytes[i + 1];
                if ((i + 2) >= bytesLength) {
                    c = 0;
                    end_num = 1;
                } else {
                    c = bytes[i + 2];
                }
            }

            log.info("a: {}, b: {}, c: {}", a, b, c);
            // 中文转二进制原始数据是负数
            if (a < 0) {
                a += 256;
            }
            if (b < 0) {
                b += 256;
            }
            if (c < 0) {
                c += 256;
            }

            log.info("a: {}, b: {}, c: {}", a, b, c);
            // 转换二进制后的字符串
            // Integer.toBinaryString(a)
            binaryStringBuffer.append(String.format("%8s", Integer.toBinaryString(a)).replace(' ', '0'));
            log.info("stringBuffer.length()： {}", binaryStringBuffer.length());
            binaryStringBuffer.append(String.format("%8s", Integer.toBinaryString(b)).replace(' ', '0'));
            log.info("stringBuffer.length()： {}", binaryStringBuffer.length());
            binaryStringBuffer.append(String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0'));
            log.info("stringBuffer.length()： {}", binaryStringBuffer.length());
            log.info("stringBuffer: {}", binaryStringBuffer);

            // 切割字符串
            String temp = binaryStringBuffer.toString();
            String a_str = temp.substring(0, 6);
            String b_str = temp.substring(6, 12);
            String c_str = temp.substring(12, 18);
            String d_str = temp.substring(18, 24);
            log.info("a_str: {}", a_str);
            log.info("b_str: {}", b_str);
            log.info("c_str: {}", c_str);
            log.info("d_str: {}", d_str);

            // 字符串格式化二进制
            int a_int = Integer.parseInt(a_str, 2);
            int b_int = Integer.parseInt(b_str, 2);
            int c_int = Integer.parseInt(c_str, 2);
            int d_int = Integer.parseInt(d_str, 2);
            log.info("a_int: {} ,b_int: {} ,c_int: {}", a_int, b_int, c_int);

            // 二进制转字节
            char a_char = (char) STANDARD_ENCODE_TABLE[a_int];
            log.info("a_char: {}", a_char);

            // 字节转string，错的没必要
            byte[] a_int_bytes = new byte[STANDARD_ENCODE_TABLE[a_int]];
            log.info("a_int_bytes: {}", new String(a_int_bytes));

            log.info("STANDARD_ENCODE_TABLE[a_int]: {}," +
                            "STANDARD_ENCODE_TABLE[b_int]: {}," +
                            "(char) STANDARD_ENCODE_TABLE[c_int]: {}",
                    STANDARD_ENCODE_TABLE[a_int],
                    STANDARD_ENCODE_TABLE[b_int],
                    (char) STANDARD_ENCODE_TABLE[c_int]);


            // 字节转给定字符
            base64Result.append((char) STANDARD_ENCODE_TABLE[a_int]);
            base64Result.append((char) STANDARD_ENCODE_TABLE[b_int]);
            if (0 == end_num) {
                base64Result.append((char) STANDARD_ENCODE_TABLE[c_int]);
                base64Result.append((char) STANDARD_ENCODE_TABLE[d_int]);
            } else if (1 == end_num) {
                base64Result.append((char) STANDARD_ENCODE_TABLE[c_int]);
                base64Result.append((char) PAD_DEFAULT);
            } else {
                base64Result.append((char) PAD_DEFAULT);
                base64Result.append((char) PAD_DEFAULT);
            }

        }

        log.info("user result: {}", base64Result);
//        byte[] encodeBytes = Base64.encodeBase64(str.getBytes());
//        log.info("common codes encode: {}", new String(encodeBytes));
//        Base64Test.JDKBase64Test(str);
        return base64Result.toString();
    }

    /**
     * 1. 输入字符串
     * 2. 将字符串编码字节
     * 3. 每六位取出转换
     * 4. 不足补 = 号
     * 5. 测试还原
     */
    public static String userBase641(String str) {

        log.info("基础字节 STANDARD_ENCODE_TABLE: {}", new String(STANDARD_ENCODE_TABLE));

        // 字符串转换为字节数组
//        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        byte[] bytes = str.getBytes();
        int bytesLength = bytes.length;
        log.info("bytesLength: {}", bytesLength);
        /*
         * 二进制转换后的字符串
         */
        StringBuffer binaryStringBuffer = null;
        // 转换后的base64
        StringBuffer base64Result = new StringBuffer();
        // 需要几个 "="
        int end_num = 0;
        for (int i = 0; i < bytesLength; i += 3) {
            binaryStringBuffer = new StringBuffer();
            byte a = bytes[i];
            byte b;
            byte c = 0;
            if ((i + 1) == bytesLength) {
                b = 0;
                end_num = 2;
            } else {
                b = bytes[i + 1];
                if ((i + 2) >= bytesLength) {
                    c = 0;
                    end_num = 1;
                } else {
                    c = bytes[i + 2];
                }
            }

            // 转换二进制后的字符串
            // Integer.toBinaryString(a)
            binaryStringBuffer.append(String.format("%8s", Integer.toBinaryString(a)).replace(' ', '0'));
            log.info("stringBuffer.length()： {}", binaryStringBuffer.length());
            binaryStringBuffer.append(String.format("%8s", Integer.toBinaryString(b)).replace(' ', '0'));
            log.info("stringBuffer.length()： {}", binaryStringBuffer.length());
            binaryStringBuffer.append(String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0'));
            log.info("stringBuffer.length()： {}", binaryStringBuffer.length());
            log.info("stringBuffer: {}", binaryStringBuffer);

            // 切割字符串
            String temp = binaryStringBuffer.toString();
            String a_str = temp.substring(0, 6);
            String b_str = temp.substring(6, 12);
            String c_str = temp.substring(12, 18);
            String d_str = temp.substring(18, 24);
            log.info("a_str: {}", a_str);
            log.info("b_str: {}", b_str);
            log.info("c_str: {}", c_str);
            log.info("d_str: {}", d_str);

            // 字符串格式化二进制
            int a_int = Integer.parseInt(a_str, 2);
            int b_int = Integer.parseInt(b_str, 2);
            int c_int = Integer.parseInt(c_str, 2);
            int d_int = Integer.parseInt(d_str, 2);
            log.info("a_int: {} ,b_int: {} ,c_int: {}", a_int, b_int, c_int);

            // 二进制转字节
            char a_char = (char) STANDARD_ENCODE_TABLE[a_int];
            log.info("a_char: {}", a_char);

            // 字节转string，错的没必要
            byte[] a_int_bytes = new byte[STANDARD_ENCODE_TABLE[a_int]];
            log.info("a_int_bytes: {}", new String(a_int_bytes));

            log.info("STANDARD_ENCODE_TABLE[a_int]: {}," +
                            "STANDARD_ENCODE_TABLE[b_int]: {}," +
                            "(char) STANDARD_ENCODE_TABLE[c_int]: {}",
                    STANDARD_ENCODE_TABLE[a_int],
                    STANDARD_ENCODE_TABLE[b_int],
                    (char) STANDARD_ENCODE_TABLE[c_int]);


            // 字节转给定字符
            base64Result.append((char) STANDARD_ENCODE_TABLE[a_int]);
            base64Result.append((char) STANDARD_ENCODE_TABLE[b_int]);
            if (0 == end_num) {
                base64Result.append((char) STANDARD_ENCODE_TABLE[c_int]);
                base64Result.append((char) STANDARD_ENCODE_TABLE[d_int]);
            } else if (1 == end_num) {
                base64Result.append((char) STANDARD_ENCODE_TABLE[c_int]);
                base64Result.append((char) PAD_DEFAULT);
            } else {
                base64Result.append((char) PAD_DEFAULT);
                base64Result.append((char) PAD_DEFAULT);
            }

        }

        log.info("user result: {}", base64Result);
//        byte[] encodeBytes = Base64.encodeBase64(str.getBytes());
//        log.info("common codes encode: {}", new String(encodeBytes));
//        Base64Test.JDKBase64Test(str);
        return base64Result.toString();
    }

    @Test
    public void testUserBase64() {
        String str = "Ow!";
        String str1 = "Ow!a";
        String str2 = "Ow!ab";
        byte[] bytes = new byte[]{1};
        log.info("bytes.length {}, bytes[0] {}, bytes[1] {}", bytes.length, bytes[0], 0);
        String result = userBase64(str2);
        log.info("result: {}", result);
    }

}

