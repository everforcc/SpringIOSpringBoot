package cn.cc.xiaoyu.util;

import cn.cc.xiaoyu.dto.XiaoyuPort;
import cn.cc.xiaoyu.server.handler.XiaoyuChannelCache;
import cn.cc.xiaoyu.server.handler.XiaoyuChannelHandler;
import cn.cc.xiaoyu.server.handler.XiaoyuDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class XiaoyuUtil {

    public static boolean isConnect(String id) {
        return XiaoyuChannelCache.cache.containsKey(id);
    }

    public static void disConnect(String id) {
        Channel channel = XiaoyuChannelCache.cache.get(id);
        if (channel != null) {
            channel.close();
        }
    }


    public static boolean canLogin(String deviceNo) {
//        return SpringUtils.getBean(XiaoYuService.class).canLogin(deviceNo);
        return true;
    }

    public static void modifyDevice(String deviceNo, int portNum, String ssm) {
//        SpringUtils.getBean(XiaoYuService.class).modifyDevice(deviceNo, portNum, ssm);
    }

    public static void modifyHeart(String deviceNo) {
//        SpringUtils.getBean(XiaoYuService.class).modifyHeartOnline(deviceNo);
    }

    public static void modifyDeviceOrderStatus(long orderNum, int orderStatus) {
//        SpringUtils.getBean(XiaoYuService.class).modifyDeviceOrderStatus(orderNum, orderStatus);
    }

    public static void completeDeviceOrder(long orderNum, short minute, short elec, int endType) {
//        SpringUtils.getBean(XiaoYuService.class).completeDeviceOrder(orderNum, minute, elec, endType);
    }


    public static void devicePortLog(String deviceNo, long orderId, int port, short w) {
//        SpringUtils.getBean(XiaoYuService.class).devicePortLog(deviceNo, orderId, port, w);
    }

    public static void deviceLog(String deviceNo, byte b1, byte b2, byte b3) {
//        SpringUtils.getBean(XiaoYuService.class).deviceLog(deviceNo, b1, b2, b3);
    }

    public static void deviceByteLog(String deviceNo, Long orderId, String step, String rs, ByteBuf byteBuf) {
        byteBuf.resetReaderIndex();
        byte[] backupBytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(backupBytes);
        String hexByte = hexString(backupBytes).toString();
        byteBuf.resetReaderIndex();
//        SpringUtils.getBean(XiaoYuService.class).deviceByteLog(deviceNo, orderId, step, rs, hexByte);
    }

    public static void deviceByteLog(String deviceNo, Long orderId, String step, String rs, byte[] bytesReturn) {
        String hexByte = hexString(bytesReturn).toString();
//        SpringUtils.getBean(XiaoYuService.class).deviceByteLog(deviceNo, orderId, step, rs, hexByte);
    }

    public static void setOutline(String deviceNo) {
//        if (StringUtils.isBlank(deviceNo)) {
//            return;
//        }
//        SpringUtils.getBean(XiaoYuService.class).modifyHeartOutLine(deviceNo);
    }

    public static boolean isOk(String deviceNo) {
//        XiaoyuChannelHandler handler = XiaoyuChannelCache.handlerCache.get(deviceNo);
//        return !(handler == null || handler.isWarn());
        return true;
    }

    public static boolean powerOpen(String deviceNo, int port, byte type, int data, long powderOrder) {
        if (!isOk(deviceNo)) {
            return false;
        }
        log.info("晓宇充电桩 7.1 服务器命令充电桩开启充电");
        Channel channel = XiaoyuChannelCache.cache.get(deviceNo);
        if (channel != null) {
            byte[] orderArr = ByteBuffer.allocate(8).putLong(powderOrder).array();
            byte ports = (byte) port;
            byte openOrClose = 1;
            byte[] dataArr = ByteBuffer.allocate(2).putShort((short) data).array();
            byte[] brr = new byte[16];
            System.arraycopy(orderArr, 0, brr, 0, 8);

            brr[8] = ports;
            brr[9] = openOrClose;

            brr[10] = type; // 01

            brr[11] = dataArr[0];
            brr[12] = dataArr[1];

            byte[] defaultShortArr = ByteBuffer.allocate(2).putShort((short) 0).array();
            brr[13] = defaultShortArr[0];
            brr[14] = defaultShortArr[1];

            XiaoyuChannelHandler handler = XiaoyuChannelCache.handlerCache.get(deviceNo);
            int id = handler.getId();
            handler.setStep7("7.2");
            channel.writeAndFlush(getSendBody(id, 0x10, brr));
            deviceByteLog(deviceNo, powderOrder, "7.1", "s", brr);
            return true;
        }

        return false;
    }

    public static boolean powerClose(String deviceNo, int port, long powderOrder) {
        if (!isOk(deviceNo)) {
            return false;
        }

        Channel channel = XiaoyuChannelCache.cache.get(deviceNo);
        if (channel != null) {
            byte[] orderArr = ByteBuffer.allocate(8).putLong(powderOrder).array();
            byte ports = (byte) port;
            byte openOrClose = 0;
            byte[] brr = new byte[10];
            System.arraycopy(orderArr, 0, brr, 0, 8);

            brr[8] = ports;
            brr[9] = openOrClose;

            XiaoyuChannelHandler handler = XiaoyuChannelCache.handlerCache.get(deviceNo);
            int id = handler.getId();
            handler.setStep7("7.5");
            channel.writeAndFlush(getSendBody(id, 0x10, brr));
            deviceByteLog(deviceNo, powderOrder, "7.4", "s", brr);
            return true;
        }

        return false;
    }

    public static List<XiaoyuPort> listPorts(String id) {
        XiaoyuChannelHandler handler = XiaoyuChannelCache.handlerCache.get(id);
        log.info("查询充电口的状态:{} - {}", id, handler == null ? null : handler.getPortList());
        if (handler != null) {
            return handler.getPortList();
        }
        return null;
    }

    public static ByteBuf getSendBody(byte[] id, byte cmd, byte[] bytes) {
        int crcLen = 2 + 4 + 1 + 2 + bytes.length;
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
            b[9 + i] = bytes[i];
        }

        short calculate = (short) Crc16.calculate(b, crcLen);
        byte[] calculateBytes = ByteBuffer.allocate(2).putShort(calculate).array();
        b[len - 4] = calculateBytes[0];
        b[len - 3] = calculateBytes[1];

        b[len - 2] = XiaoyuDecoder.TAIL_1;
        b[len - 1] = XiaoyuDecoder.TAIL_2;
        ByteBuf byteBuf = Unpooled.copiedBuffer(b);

        log.info("晓宇充电桩 组装即将发送的数据:{}", hexString(b));
        return byteBuf;
    }

    public static ByteBuf getSendNull(byte[] id, byte cmd) {
        int crcLen = 2 + 4 + 1 + 2 + 0;
        int len = crcLen + 2 + 2;
        byte[] b = new byte[len];
        b[0] = XiaoyuDecoder.HEADER_1;
        b[1] = XiaoyuDecoder.HEADER_2;

        b[2] = id[0];
        b[3] = id[1];
        b[4] = id[2];
        b[5] = id[3];

        b[6] = cmd;

        short l = 0;
        byte[] lenBytes = ByteBuffer.allocate(2).putShort(l).array();
        b[7] = lenBytes[0];
        b[8] = lenBytes[1];

//        for (int i = 0; i < bytes.length; i++) {
//            b[9+i] = bytes[i];
//        }

        short calculate = (short) Crc16.calculate(b, crcLen);
        byte[] calculateBytes = ByteBuffer.allocate(2).putShort(calculate).array();
        b[len - 4] = calculateBytes[0];
        b[len - 3] = calculateBytes[1];

        b[len - 2] = XiaoyuDecoder.TAIL_1;
        b[len - 1] = XiaoyuDecoder.TAIL_2;
        ByteBuf byteBuf = Unpooled.copiedBuffer(b);

        log.info("心跳应答数据:{}", hexString(b));
        return byteBuf;
    }

    public static ByteBuf getSendBody(int id, int cmd, byte... bytes) {
        byte[] ids = ByteBuffer.allocate(4).putInt(id).array();
        return getSendBody(ids, (byte) cmd, bytes);
    }

    public static ByteBuf getSendNull(int id, int cmd) {
        byte[] ids = ByteBuffer.allocate(4).putInt(id).array();
        return getSendNull(ids, (byte) cmd);
    }

    public static List<String> hexString(byte[] bytes) {
        List<String> strings = new ArrayList<>();
        for (byte aByte : bytes) {
            String hexString = Integer.toHexString(aByte & 0xff);
            strings.add(hexString);
        }
        return strings;
    }

    public static void notify6(String deviceNo, int orderId, int port, int type) {
//        SpringUtils.getBean(XiaoyuService.class).notify6(deviceNo, orderId,port,type);
    }

    public static void notify5(String deviceNo, int orderNum, byte port, byte type, short munite) {
//        SpringUtils.getBean(XiaoyuService.class).notify5(deviceNo,orderNum,port,type,munite);
    }

    public static void setOnline(String deviceNo) {
        // todo 更新设备 心跳/状态
//        XiaoyuDevice device = DeviceUtil.getDeviceByType(XiaoyuDevice.class, deviceNo);
//        if (device != null){
//            device.setStatusOnline();
//        }
//        SpringUtils.getBean(XiaoyuService.class).setOnline(deviceNo);
    }

    public static String escapeControlChars(String str) {
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
