package cn.cc.xiaoyu.util;

import io.netty.buffer.ByteBuf;

public class CharUtils {



    // 将ByteBuf转换为十六进制字符串
    public static String byteBufToHex(ByteBuf byteBuf) {
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        return bytesToHex(bytes);
    }

    // 将字节数组转换为十六进制字符串
    public static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x ", b));
        }
        return result.toString();
    }

}
