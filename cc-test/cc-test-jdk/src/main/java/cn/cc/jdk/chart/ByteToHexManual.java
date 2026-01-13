package cn.cc.jdk.chart;

public class ByteToHexManual {
    // 十六进制字符表（大写）
    private static final char[] HEX_CHAR_TABLE = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static void main(String[] args) {
        byte[] bytes = new byte[]{8, -1, 127, 0x38};
        String[] hexStrings = byteToHexManual(bytes);

        System.out.println("手动转换结果：");
        for (String hex : hexStrings) {
            System.out.print(hex + " "); // 输出：08 FF 7F 38
        }
    }

    public static String[] byteToHexManual(byte[] bytes) {
        // 创建结果数组，长度与输入字节数组相同
        String[] result = new String[bytes.length];

        // 遍历每个字节进行转换
        for (int i = 0; i < bytes.length; i++) {
            // 将byte值转换为无符号整数值(0-255)
            // byte是8位有符号数(-128到127)，与0xFF进行按位与运算可得到其无符号表示
            System.out.printf("字节[%d]: %d\n", i, bytes[i]);
            System.out.printf("  原始二进制: %8s (补零到8位)\n", Integer.toBinaryString(bytes[i] & 0xFF));

            int unsignedByte = bytes[i] & 0xFF;
            System.out.printf("  按位与0xFF: %d & 0xFF = %d\n", bytes[i], unsignedByte);
            System.out.printf("  无符号二进制: %8s\n", String.format("%8s", Integer.toBinaryString(unsignedByte)).replace(' ', '0'));
            System.out.printf("  无符号值: %d (0x%02X)\n", unsignedByte, unsignedByte);

            // 提取高4位并获取对应十六进制字符
            // 使用无符号右移4位(>>> 4)获取高4位数值，作为HEX_CHAR_TABLE索引
            int highNibble = unsignedByte >>> 4;                           // 右移4位获取高4位
            System.out.printf("  高4位提取: %8s >>> 4 = %8s (十进制: %d)\n",
                    String.format("%8s", Integer.toBinaryString(unsignedByte)).replace(' ', '0'),
                    String.format("%8s", Integer.toBinaryString(highNibble)).replace(' ', '0'),
                    highNibble);
            char high = HEX_CHAR_TABLE[highNibble];                        // 高4位
            System.out.printf("  高4位: %d -> 字符: '%c'\n", highNibble, high);

            // 提取低4位并获取对应十六进制字符
            // 与0x0F(二进制: 00001111)进行按位与运算，保留低4位数值，作为HEX_CHAR_TABLE索引
            int lowNibble = unsignedByte & 0x0F;                          // 与0x0F按位与获取低4位
            System.out.printf("  低4位提取: %8s & %8s = %8s (十进制: %d)\n",
                    String.format("%8s", Integer.toBinaryString(unsignedByte)).replace(' ', '0'),
                    String.format("%8s", Integer.toBinaryString(0x0F)).replace(' ', '0'),
                    String.format("%8s", Integer.toBinaryString(lowNibble)).replace(' ', '0'),
                    lowNibble);
            char low = HEX_CHAR_TABLE[lowNibble];                         // 低4位
            System.out.printf("  低4位: %d -> 字符: '%c'\n", lowNibble, low);

            // 将高4位和低4位字符组合成两位十六进制字符串，并存储到结果数组
            result[i] = new String(new char[]{high, low});
            System.out.printf("  组合结果: \"%c%c\" -> \"%s\"\n\n", high, low, result[i]);
        }

        // 返回转换后的十六进制字符串数组
        return result;
    }


}