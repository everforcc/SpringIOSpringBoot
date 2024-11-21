/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-22 11:04
 * Copyright
 */

package cn.cc.utils.commons.codec;

import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class JSha256Utils {

    private static final String ALGORITHM = "SHA-256";

    /**
     * 用java原生的摘要实现SHA256加密
     *
     * @param str 加密前的报文
     * @return 加密结果
     */
    public static String getSHA256String(String str) {
        String encodeStr = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            //encodeStr = byte2Hex(messageDigest.digest());
            encodeStr = Hex.encodeHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }


//    /**
//     * byte[]转为16进制
//     *
//     * @param bytes 字节
//     * @return 十六进制后的值
//     */
//    private static String byte2Hex(byte[] bytes) {
//        StringBuilder stringBuffer = new StringBuilder();
//        for (int i = 0; i < bytes.length; i++) {
//            String temp = Integer.toHexString(bytes[i] & 0xFF);
//            if (temp.length() == 1) {
//                stringBuffer.append("0");
//            }
//            stringBuffer.append(temp);
//        }
//        return stringBuffer.toString();
//    }

}
