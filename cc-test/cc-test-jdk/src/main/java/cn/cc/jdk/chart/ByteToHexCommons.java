package cn.cc.jdk.chart;

import org.apache.commons.codec.binary.Hex;

public class ByteToHexCommons {
    public static void main(String[] args) {
        byte[] bytes = new byte[]{8, -1, 0x38};
        // 转为大写十六进制字符串（拼接版）
        String fullHex = Hex.encodeHexString(bytes, false); // false=大写，true=小写
        System.out.println("Commons Codec 转换结果：" + fullHex); // 08FF38

        // 若要拆分为数组，可先拼接再按两位拆分
        String[] hexArray = fullHex.split("(?<=\\G.{2})"); // 每两位拆分
        for (String hex : hexArray) {
            System.out.print(hex + " "); // 08 FF 38
        }
    }
}