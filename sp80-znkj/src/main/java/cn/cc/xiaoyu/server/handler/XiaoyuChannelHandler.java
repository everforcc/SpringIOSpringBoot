package cn.cc.xiaoyu.server.handler;

import cn.cc.xiaoyu.dto.XiaoyuPort;
import cn.cc.xiaoyu.util.XiaoyuUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class XiaoyuChannelHandler extends ChannelInboundHandlerAdapter {

    public static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(50, 50, 1, TimeUnit.MINUTES, new LinkedBlockingDeque<>(1024));

    private int id;
    private String deviceNo;
    //交流桩类型，10：10 路机；20：20 路机；30：单枪交流桩；40：双枪交流桩；1 字节
    private int type;

    private String step7 = "7.1";

    private int connectType;

    private boolean warn;

    public boolean isWarn() {
        return warn;
    }

    private List<XiaoyuPort> portList = new ArrayList<>();

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        log.info("设备离线..." + deviceNo);
        XiaoyuChannelCache.cache.remove(deviceNo);
        XiaoyuChannelCache.handlerCache.remove(deviceNo);
        setOutline();
    }

    private void setOutline() {
        XiaoyuUtil.setOutline(deviceNo);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.READER_IDLE) {
                log.info("长时间未连接发送数据,中断...");
                ctx.channel().close();
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        warn = false;
        XiaoyuDecoder.DataPacket dataPacket = (XiaoyuDecoder.DataPacket) msg;
        log.info("channelId:{},设备编号:{},指令:{},内容:{}", ctx.channel().id(), dataPacket.getIdStr(), dataPacket.getFunctionCode(), dataPacket.getData());
        //设备编号
        String deviceNo = dataPacket.getIdStr();
        //设备指令
        int fun = dataPacket.getFunctionCode();
        if (XiaoyuChannelCache.cache.containsKey(deviceNo)) {
            Channel existChannel = XiaoyuChannelCache.cache.get(deviceNo);
            if (!existChannel.id().equals(ctx.channel().id())) {
                existChannel.writeAndFlush(Unpooled.copyBoolean(true));
                XiaoyuChannelCache.handlerCache.get(deviceNo).warn = true;
                throw new RuntimeException(deviceNo + "已有连接,暂时不能连接..." + existChannel.id() + "->>" + ctx.channel().id());
            }
        } else {
            //第一次登陆
            boolean login = XiaoyuUtil.canLogin(deviceNo);
            if (!login) {
                log.error("设备不存在:{} 不允许登陆...", deviceNo);
                XiaoyuUtil.deviceByteLog(deviceNo, 0L, "99", "r", dataPacket.getData());
//                throw new RuntimeException(deviceNo + "不允许登陆...");
                return;
            }
            this.id = dataPacket.getId();
            this.deviceNo = deviceNo;
            XiaoyuChannelCache.cache.put(deviceNo, ctx.channel());
            XiaoyuChannelCache.handlerCache.put(deviceNo, this);
        }

        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                handle(ctx, dataPacket, deviceNo, fun);
            }
        });


    }

    private void handle(ChannelHandlerContext ctx, XiaoyuDecoder.DataPacket dataPacket, String deviceNo, int function) {
        ByteBuf byteBuf = dataPacket.getByteBuf();
        int dataLen = dataPacket.getDataLen();
        switch (function) {
            case 1:
                byte[] bytes = ByteBufUtil.getBytes(byteBuf, 0, 20);
                String ssm = new String(bytes, StandardCharsets.US_ASCII);
                int portNum = byteBuf.getByte(22) & 0xff;
                log.info("晓宇充电桩 设备号:{} ssm卡号:{} 端口数量:{}", deviceNo, ssm, portNum);
                XiaoyuUtil.modifyDevice(deviceNo, portNum, ssm);
                ctx.channel().writeAndFlush(XiaoyuUtil.getSendBody(this.id, function, (byte) 0));

                XiaoyuUtil.deviceByteLog(deviceNo, 0L, "2", "r", byteBuf);
                break;
            case 2:
                log.info("心跳...");

                updatePort(dataPacket.getData());
                setOnline(deviceNo);
                XiaoyuUtil.modifyHeart(deviceNo);
                ctx.channel().writeAndFlush(XiaoyuUtil.getSendNull(this.id, function));
                XiaoyuUtil.deviceByteLog(deviceNo, 0L, "3", "r", dataPacket.getData());
                break;
            case 0x10: // 7、服务器远程开启、结束充电
                // 7.1 远程开启充电
                if ("7.2".equals(this.step7)) {
                    long orderNum10 = byteBuf.readLong();
                    byte port10 = byteBuf.readByte();
                    byte type10 = byteBuf.readByte();
                    byte status10 = byteBuf.readByte();
                    log.info("晓宇充电桩 7.2 开启后回复服务器信息 订单号：{} 插座号：{} 类型：{} 状态：{} ", orderNum10, port10, type10, status10);
                    // 调用更新业务信息接口 status!=1 报错
                    if (1 != status10) {
                        log.info("status: {}, 0：无负载订单不建立；1：有负载开始充电；2，有订单返回已占用", status10);
                    }
                    byte[] bytesReturn = new byte[10];
                    byte[] orderNums10 = ByteBuffer.allocate(8).putLong(orderNum10).array();
                    // orderNums读到bytesReturn里
                    System.arraycopy(orderNums10, 0, bytesReturn, 0, 8);
                    bytesReturn[8] = port10;
                    bytesReturn[9] = 3;
                    XiaoyuUtil.deviceByteLog(deviceNo, orderNum10, "7.2", "r", byteBuf);
                    XiaoyuUtil.deviceByteLog(deviceNo, orderNum10, "7.3", "s", bytesReturn);
                    ctx.channel().writeAndFlush(XiaoyuUtil.getSendBody(this.id, function, bytesReturn));
                    // 更新订单状态
                    // 2充电中 4 无负载 5端口已使用
                    int orderStatus = 2;
                    if (0 == status10) {
                        orderStatus = 4;
                    } else if (2 == status10) {
                        orderStatus = 5;
                    }
                    XiaoyuUtil.modifyDeviceOrderStatus(orderNum10, orderStatus);
                } else if ("7.5".equals(this.step7)) {
                    // 7.4 远程结束充电
                    log.info("晓宇充电桩 7.5 结束充电后回复服务器信息");
                    long orderNum10 = byteBuf.readLong();
                    byte port10 = byteBuf.readByte();
                    byte type10 = byteBuf.readByte();
                    byte feeType10 = byteBuf.readByte();
                    // 根据计费类型不同下发数据。
                    short feeData10 = Short.reverseBytes(byteBuf.readShort());
                    short money10 = Short.reverseBytes(byteBuf.readShort());
                    short minute10 = Short.reverseBytes(byteBuf.readShort());
                    short elec10 = Short.reverseBytes(byteBuf.readShort());
                    short maxW10 = Short.reverseBytes(byteBuf.readShort());
                    // 0XFFFF 时为桩还没有读取完成
                    short nMinuteAverageW10 = Short.reverseBytes(byteBuf.readShort());
                    short averageW10 = Short.reverseBytes(byteBuf.readShort());

                    log.info("晓宇充电桩 7.5 结束充电后回复服务器信息 " +
                                    "订单号：{} 插座号：{} 类型：{} 计费类型：{} 根据计费类型不同下发数据：{} " +
                                    "金额：{} " +
                                    "分钟：{} " +
                                    "度数：{} 最大功率：{} 充电N 分钟平均功率：{} 平均功率：{} ",
                            orderNum10, port10, type10, feeType10, feeData10,
                            money10,
                            minute10,
                            elec10, maxW10, nMinuteAverageW10, averageW10);


                    byte[] bytesReturn = new byte[10];
                    byte[] orderNums10 = ByteBuffer.allocate(8).putLong(orderNum10).array();
                    System.arraycopy(orderNums10, 0, bytesReturn, 0, 8);
                    bytesReturn[8] = port10;
                    bytesReturn[9] = 0x02;
                    XiaoyuUtil.deviceByteLog(deviceNo, orderNum10, "7.5", "r", byteBuf);
                    // todo 报几次就结束了，如何获取
//                    if(-1 == nMinuteAverageW10){
//                        log.info("未读取完成，多等几次: {}", orderNum10);
//                        return;
//                    }
                    log.info("已读取完成: {}", orderNum10);
                    XiaoyuUtil.deviceByteLog(deviceNo, orderNum10, "7.6", "s", bytesReturn);
                    ctx.channel().writeAndFlush(XiaoyuUtil.getSendBody(this.id, function, bytesReturn));
                    // 更新订单状态
                    XiaoyuUtil.completeDeviceOrder(orderNum10, minute10, elec10, 12);
                }
                break;
            case 0x12: // 8、充电桩主动结束充电（不包含用户自已结束）
                log.info("晓宇充电桩 8.1 充电桩主动结束充电...");
                long orderNum12 = byteBuf.readLong();
                byte port12 = byteBuf.readByte();
                byte endType12 = byteBuf.readByte();
                byte feeType12 = byteBuf.readByte();

                short r1 = Short.reverseBytes(byteBuf.readShort());
                short r2 = Short.reverseBytes(byteBuf.readShort());
                short minute12 = Short.reverseBytes(byteBuf.readShort());
                short elec12 = Short.reverseBytes(byteBuf.readShort());
                short maxW12 = Short.reverseBytes(byteBuf.readShort());
                // 0XFFFF
                short nMinuteAverageW12 = Short.reverseBytes(byteBuf.readShort());
                short averageW12 = Short.reverseBytes(byteBuf.readShort());
                log.info("晓宇充电桩 8.1 充电桩主动结束充电 订单号：{} 插座号：{} 结束类型：{} 计费类型：{} 时间:{} 度数:{} 最大功率：{} 平均功率：{} 平均电流：{}",
                        orderNum12, port12, endType12, feeType12, minute12, elec12, maxW12, averageW12, nMinuteAverageW12);
                // 收到消息后确认回复客户端
                byte[] bytesReturn = new byte[9];
                byte[] orderNums12 = ByteBuffer.allocate(8).putLong(orderNum12).array();
                System.arraycopy(orderNums12, 0, bytesReturn, 0, 8);
                bytesReturn[8] = port12;
                ctx.channel().writeAndFlush(XiaoyuUtil.getSendBody(this.id, function, bytesReturn));
                XiaoyuUtil.deviceByteLog(deviceNo, orderNum12, "8.1", "r", byteBuf);
                XiaoyuUtil.deviceByteLog(deviceNo, orderNum12, "8.2", "s", bytesReturn);
                XiaoyuUtil.completeDeviceOrder(orderNum12, minute12, elec12, endType12);
                break;
            case 0x13: // 9、充电主动上报端口状态
                log.info("晓宇充电桩 9.1 充电主动上报端口状态...");
                long orderNum13 = byteBuf.readLong();
                byte port13 = byteBuf.readByte();
                short feeType13 = Short.reverseBytes(byteBuf.readShort());
                Short.reverseBytes(byteBuf.readShort());
                Short.reverseBytes(byteBuf.readShort());
                Short.reverseBytes(byteBuf.readShort());
                short elec13 = Short.reverseBytes(byteBuf.readShort());
                short max10MinuteW13 = Short.reverseBytes(byteBuf.readShort());
                // 0XFFFF
                short nMinuteAverageW13 = Short.reverseBytes(byteBuf.readShort());
                short averageW13 = Short.reverseBytes(byteBuf.readShort());
                short nowW13 = Short.reverseBytes(byteBuf.readShort());
                log.info("晓宇充电桩 9.1 充电主动上报端口状态 " +
                                "订单号：{} 插座号：{} " +
                                "计费类型：{} 度数:{} " +
                                "每 10分种最大功率：{} 前N分钟平均功：{} 平均功率：{} 当前功率：{}",
                        orderNum13, port13, feeType13, elec13, max10MinuteW13, nMinuteAverageW13, averageW13, nowW13);
                XiaoyuUtil.deviceByteLog(deviceNo, orderNum13, "9.1", "r", byteBuf);
                XiaoyuUtil.devicePortLog(deviceNo, orderNum13, port13, nowW13);
                break;
//            case 0x16: // 12、服务器查询某端口状态 心跳会上报
//                break;
            case 0x17: // 13、充电桩设备异常上报指令
                byte b1 = byteBuf.readByte();
                byte b2 = byteBuf.readByte();
                byte b3 = byteBuf.readByte();
                byte[] bytesReturn17 = new byte[1];
                ctx.channel().writeAndFlush(XiaoyuUtil.getSendBody(this.id, function, bytesReturn17));
                XiaoyuUtil.deviceByteLog(deviceNo, 0L, "13.1", "r", byteBuf);
                XiaoyuUtil.deviceByteLog(deviceNo, 0L, "13.2", "s", bytesReturn17);
                XiaoyuUtil.deviceLog(deviceNo, b1, b2, b3);
                break;
        }
    }

    private void updatePort(byte[] bytes) {
        portList.clear();
        for (int i = 0; i < bytes.length; i++) {
            portList.add(new XiaoyuPort(i, bytes[i]));
        }
        log.info("修改端口状态:{}", portList);
    }

    private void flushClosePort(int port) {
        try {
            XiaoyuPort xiaoyuPort = portList.get(port - 1);
            xiaoyuPort.setType(0);
        } catch (Exception e) {

        }
        log.info("flush修改端口状态:{}", portList);
    }

    private void flushOpenPort(int port) {
        try {
            XiaoyuPort xiaoyuPort = portList.get(port - 1);
            xiaoyuPort.setType(1);
        } catch (Exception e) {

        }
        log.info("flush修改端口状态:{}", portList);
    }

    private static void setOnline(String deviceNo) {
        XiaoyuUtil.setOnline(deviceNo);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public int getType() {
        return type;
    }

    public int getConnectType() {
        return connectType;
    }

    public List<XiaoyuPort> getPortList() {
        return portList;
    }

    public int getId() {
        return id;
    }

    public String getStep7() {
        return step7;
    }

    public void setStep7(String step7) {
        this.step7 = step7;
    }
}
