package cn.cc.jdk.chart;

public class ByteToHexString {
    public static void main(String[] args) {
        // 测试用的 byte 数组（包含正数、负数、0）
        byte[] bytes = new byte[]{
                8, -1, 127, 0, 0x38, (byte) 200
        };

        // 转换每个 byte 为两位十六进制字符串
        String[] hexStrings = byteArrayToHexStrings(bytes);

        // 输出结果
        System.out.println("原始 byte 数组：");
        for (byte b : bytes) {
            System.out.print(b + " ");
        }
        System.out.println("\n转换后的十六进制字符串数组：");
        for (String hex : hexStrings) {
            System.out.print(hex + " ");
        }

        // 可选：拼接为一个完整的十六进制字符串（如 "08FF7F0038C8"）
        String fullHex = byteArrayToFullHexString(bytes);
        System.out.println("\n拼接后的完整十六进制字符串：" + fullHex);
    }

    /**
     * 把 byte[] 每个元素转为两位十六进制字符串（数组形式返回）
     */
    public static String[] byteArrayToHexStrings(byte[] bytes) {
        String[] result = new String[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            // 核心：& 0xFF 转无符号值，%02X 格式化为两位大写十六进制
            result[i] = String.format("%02X", bytes[i] & 0xFF);
            // 若要小写：String.format("%02x", bytes[i] & 0xFF)
        }
        return result;
    }

    /**
     * 把 byte[] 转为一个完整的十六进制字符串（拼接所有结果）
     */
    public static String byteArrayToFullHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b & 0xFF));
        }
        return sb.toString();
    }
}
