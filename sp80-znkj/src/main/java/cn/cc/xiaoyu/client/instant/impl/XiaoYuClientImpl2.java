package cn.cc.xiaoyu.client.instant.impl;

import cn.cc.xiaoyu.client.instant.IXiaoYuClient;
import cn.cc.xiaoyu.util.Crc16;

import java.nio.ByteBuffer;

public class XiaoYuClientImpl2 implements IXiaoYuClient {

    private static final byte card_1 = 0x52;
    private static final byte card_2 = 0x0;
    private static final byte card_3 = 0x1;
    private static final byte card_4 = (byte)0x82;

    @Override
    public byte[] getLoginData() {

        int crcLen = 2 + 4 + 1 + 2 + 24;
        byte[] bytes = new byte[]{
                (byte) 0xd5, 0x5d,
                card_1, card_2, card_3, card_4,
                0x1,
                0x0, 0x18,

                0x38, 0x39, 0x38, 0x36,
                0x30, 0x34, 0x36, 0x31,
                0x31, 0x36, 0x31, 0x39,
                0x37, 0x32, 0x37, 0x37,
                0x34, 0x32, 0x39, 0x30,
                0xb, 0x1b, 0x14, 0xf,

                0x72, (byte) 0xed,
                (byte) 0xb3, 0x3b};
        short calculate = (short) Crc16.calculate(bytes, crcLen);
        byte[] calculateBytes = ByteBuffer.allocate(2).putShort(calculate).array();
        bytes[crcLen] = calculateBytes[0];
        bytes[crcLen + 1] = calculateBytes[1];
        return bytes;
    }

    @Override
    public byte[] getHeartData() {
        int crcLen = 2 + 4 + 1 + 2 + 24;
        byte[] bytes = new byte[]{
                (byte) 0xd5, (byte) 0x5d,
                card_1, card_2, card_3, card_4,
                0x2,
                0x0, 0x18,
                0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
                0x60, (byte) 0xFE,
                (byte) 0xb3, 0x3b};
        short calculate = (short) Crc16.calculate(bytes, crcLen);
        byte[] calculateBytes = ByteBuffer.allocate(2).putShort(calculate).array();
        bytes[crcLen] = calculateBytes[0];
        bytes[crcLen + 1] = calculateBytes[1];
        return bytes;
    }
}
