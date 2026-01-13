package cn.cc.xiaoyu.util;

import cn.cc.xiaoyu.server.handler.XiaoyuDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
@Slf4j
public class XiaoyuUtil {

    public static List<String> hexString(byte[] bytes) {
        List<String> strings = new ArrayList<>();
        for (byte aByte : bytes) {
            log.info("aByte: {}", aByte);
            String hexString = Integer.toHexString(aByte & 0xff);
            strings.add(hexString);
        }
        return strings;
    }

    public static ByteBuf getSendBody(int id, int cmd, byte... bytes){
        byte[] ids = ByteBuffer.allocate(4).putInt(id).array();
        return getSendBody(ids, (byte) cmd,bytes);
    }

    public static ByteBuf getSendBody(byte[] id, byte cmd, byte[] bytes){
        int crcLen = 2 + 4 + 1 + 2 + bytes.length ;
        int len = crcLen + 2 + 2;
        byte[] b = new byte[len];
        b[0] = XiaoyuDecoder.HEADER_1;
        b[1] = XiaoyuDecoder.HEADER_2;

        b[2] = id[0];
        b[3] = id[1];
        b[4] = id[2];
        b[5] = id[3];

        b[6] = cmd;

        short l = (short) bytes.length;
        byte[] lenBytes = ByteBuffer.allocate(2).putShort(l).array();
        b[7] = lenBytes[0];
        b[8] = lenBytes[1];

        for (int i = 0; i < bytes.length; i++) {
            b[9+i] = bytes[i];
        }

        short calculate = (short) Crc16.calculate(b, crcLen);
        byte[] calculateBytes = ByteBuffer.allocate(2).putShort(calculate).array();
        b[len-4] = calculateBytes[0];
        b[len-3] = calculateBytes[1];

        b[len-2] = XiaoyuDecoder.TAIL_1;
        b[len-1] = XiaoyuDecoder.TAIL_2;
        ByteBuf byteBuf = Unpooled.copiedBuffer(b);

        log.info("组装即将发送的数据:{}",hexString(b));
        return byteBuf;
    }

}
