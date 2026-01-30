package cn.cc.xiaoyu;

import cn.cc.xiaoyu.util.charutil.AsciiUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XiaoYuAnalysisTest {

    public static void main(String[] args) {
//        shoudong();
//        ip();
    }

    public static void ip() {
        String ip = "SetIP[127.0.0.1:1024]END";
        System.out.println(ip.length());
        // ip拆分为byte数组
        byte[] ipBytes = ip.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        System.out.println(ipBytes.length);
    }

    public static void fun8_1() {

        byte[] bytes2 = new byte[]{16, -47, 33, -14, -2, -126, 32, 0, 1, 0, 0, -56, 0, 0, 0, 10, 0, 4, 0, -103, 1, -112, 1, 13, 1};
        System.out.println(AsciiUtils.hexString(bytes2));
        ByteBuf byteBuf = Unpooled.copiedBuffer(bytes2);
        long orderNum12 = byteBuf.readLong();
        byte port12 = byteBuf.readByte();
        byte endType12 = byteBuf.readByte();
        byte feeType12 = byteBuf.readByte();

        short r1 = byteBuf.readShort();
        short r2 = byteBuf.readShort();
        short minute12 = byteBuf.readShort();
        short elec12 = byteBuf.readShort();
        short maxW12 = byteBuf.readShort();
        // 0XFFFF
        short nMinuteAverageW12 = byteBuf.readShort();
        short averageW12 = byteBuf.readShort();
        log.info("晓宇充电桩 8.1 充电桩主动结束充电 订单号：{} 插座号：{} 结束类型：{} 计费类型：{} 时间:{} 电量:{} 最大功率：{} 平均功率：{} 平均电流：{}",
                orderNum12, port12, endType12, feeType12, minute12, elec12, maxW12, averageW12, nMinuteAverageW12);
    }

    public static void fun8_2() {
        //channelId:e02b8b1a,设备编号:51001855,指令:16,内容:[16, -47, 47, -38, -67, -62, 32, 0, 1, 0, 0, 120, 0, 0, 0, 2, 0, 1, 0, -120, 1, -1, -1, -126, 1]
        byte[] bytes2 = new byte[]{
                16, -47, 47, -38, -67, -62, 32, 0,
                1,
                0,
                0,
                120, 0,
                0, 0,
                2, 0,
                1, 0,
                -120, 1,
                -1, -1,
                -126, 1};
        /**
         * d55d
         * 51000521
         * 10
         * 0019
         *
         *
         * 00,00,00,00,39,d2,c5,c1,07,00,05,
         *
         * 58,02,
         * 00,00,
         * 5e,00,
         * 1c,00,
         * c1,00,
         * b2,00,
         * ad,00
         *
         *
         * ,f8,af
         * b33b
         */
//        bytes2 = new byte[]{0x00,0x00,0x00,0x00,0x39, (byte) 0xd2, (byte) 0xc5, (byte) 0xc1,0x07,0x00,0x05,0x58,0x02,0x00,0x00,0x5e,0x00,0x1c,0x00, (byte) 0xc1,0x00, (byte) 0xb2,0x00, (byte) 0xad,0x00, (byte) 0xf8};
        bytes2 = new byte[]{0x10, (byte) 0xd1, (byte) 2f, (byte) 0xda, (byte) 0xbd, (byte) 0xc2, 20, 0, 1, 0, 0, 78, 0, 0, 0, 2, 0, 1, 0, 88, 1, (byte) 0xff, (byte) 0xff, 82, 0x1};
        // [10, d1, 2f, da, bd, c2, 20, 0, 1, 0, 0, 78, 0, 0, 0, 2, 0, 1, 0, 88, 1, ff, ff, 82, 1]

        System.out.println("bytes2.length" + bytes2.length);

        ByteBuf byteBuf = Unpooled.copiedBuffer(bytes2);
        System.out.println("byteBuf.readableBytes(): " + byteBuf.readableBytes());
        long orderNum12 = byteBuf.readLong();
        System.out.println("byteBuf.readableBytes(): " + byteBuf.readableBytes());
        byte port12 = byteBuf.readByte();
        System.out.println("byteBuf.readableBytes(): " + byteBuf.readableBytes());
        byte endType12 = byteBuf.readByte();
        byte feeType12 = byteBuf.readByte();

        short r1 = Short.reverseBytes(byteBuf.readShort());
        short r2 = Short.reverseBytes(byteBuf.readShort());
        short minute12 = Short.reverseBytes(byteBuf.readShort());
        short elec12 = Short.reverseBytes(byteBuf.readShort());
        short maxW12 = Short.reverseBytes(byteBuf.readShort());
        // 0XFFFF
        short nMinuteAverageW12 = Short.reverseBytes(byteBuf.readShort());
        System.out.println("byteBuf.readableBytes(): " + byteBuf.readableBytes());
        short averageW12 = Short.reverseBytes(byteBuf.readShort());
        System.out.println("byteBuf.readableBytes(): " + byteBuf.readableBytes());
        log.info("AsciiUtils.hexString(bytes2): {}", AsciiUtils.hexString(bytes2));
        log.info("晓宇充电桩 8.1 充电桩主动结束充电 " +
                        "订单号：{} " +
                        "插座号：{} " +
                        "回复类型：{} " +
                        "计费类型：{} " +


                        "计费类型：{} " +
                        "金额：{} " +


                        "时间:{} " +
                        "电量:{} " +
                        "最大功率：{} " +
                        "N平均功率：{} " +
                        "平均电流：{}",
                orderNum12, port12, endType12, feeType12,

                r1, r2,

                minute12, elec12, maxW12, averageW12, nMinuteAverageW12);
    }

}
