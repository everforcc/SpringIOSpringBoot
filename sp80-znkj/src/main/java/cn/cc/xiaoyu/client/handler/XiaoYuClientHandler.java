package cn.cc.xiaoyu.client.handler;

import cn.cc.xiaoyu.client.instant.IXiaoYuClient;
import cn.cc.xiaoyu.util.XiaoyuUtil;
import cn.cc.xiaoyu.util.charutil.AsciiUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * 模拟充电桩接收指令，发送请求
 */
@Slf4j
public class XiaoYuClientHandler extends ChannelInboundHandlerAdapter {

    public static Map<Integer, String> step7Map = new HashMap<>();

    public static Map<Integer, byte[]> orderMap = new HashMap<>();

    private IXiaoYuClient iXiaoYuClient;

    public XiaoYuClientHandler() {

    }

    public XiaoYuClientHandler(IXiaoYuClient iXiaoYuClient) {
        this.iXiaoYuClient = iXiaoYuClient;
    }

    /**
     * 1. 模拟充电桩登录
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf = ctx.alloc().buffer(37);
        // byteBuf 写入十六进制 [0, d5, 5d, 52, 0, 1, 81, 72, ed, b3, 3b]
        byteBuf.writeBytes(iXiaoYuClient.getLoginData());
        ctx.writeAndFlush(byteBuf);
        log.info("模拟充电桩客户端发送登录请求: {}", AsciiUtils.hexString(iXiaoYuClient.getLoginData()));

        /**
         * 作用: 调用父类的 channelActive 方法
         * 目的: 确保继承链中父类的相应处理逻辑得到执行
         * 重要性: 维护 Netty 框架的标准行为
         */
        super.channelActive(ctx);
        /**
         * todo 记录
         * 作用: 将 channelActive 事件传递给管道中的下一个处理器
         * 目的: 触发管道中后续处理器的 channelActive 方法
         * 效果: 保证事件在管道中的正常传播
         */
//        ctx.fireChannelActive();
    }

    /**
     * 根据不同响应，走不同逻辑，向服务器发送指令
     * 接收下发的各种指令
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // todo-文档响应 1.登录 2.XXX
        // 接收服务器返回的数据
        ByteBuf byteBuf = (ByteBuf) msg;
//        log.info("收到服务器响应: {}", AsciiUtils.byteBufToHex(byteBuf));
        AsciiUtils.printByteBuf(byteBuf);
        byteBuf.resetReaderIndex();
        // 处理响应数据
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        byteBuf.resetReaderIndex();

        byteBuf.readShort();
        int id = byteBuf.readInt();
        byte cmd = byteBuf.readByte();
        short dataLength = byteBuf.readShort();
        log.info("响应数据: id: {}, cmd: {}, dataLength: {}", id, cmd, dataLength);
        byte[] orderNums = new byte[0];
        long orderNum = 0;
        byte port = 0;
        if(0x01 != cmd && 0x02 != cmd) { // todo - bug
            orderNum = byteBuf.readLong();
            orderNums = ByteBuffer.allocate(8).putLong(orderNum).array();

            port = byteBuf.readByte();
            if (!step7Map.containsKey(id)) {
                step7Map.put(id, "7.1");
            }
        }

        // 打印十六进制格式
        log.info("响应数据长度: {}", bytes.length);

        byte function = bytes[6];
        switch (function) {
            case 0x10:
                if("7.1".equals(step7Map.get(id))) {
                    log.info("晓宇充电桩 7.1 服务器命令充电桩开启充电");
                    byte[] openData = new byte[11];
                    System.arraycopy(orderNums, 0, openData, 0, 8);
                    openData[8] = port;
                    openData[9] = (byte) 1;
                    openData[10] = (byte) 1;
                    ctx.channel().writeAndFlush(XiaoyuUtil.getSendBody(id, function, openData));
                    log.info("晓宇充电桩 7.2 充电桩开启后 回复服务器信息: {}", AsciiUtils.hexString(openData));
                    step7Map.put(id, "7.3");
                }else if("7.3".equals(step7Map.get(id))) {
                    orderMap.put(id, orderNums);
                    log.info("晓宇充电桩 7.3 服务器回复充电桩确认收到指令");
                    log.info("晓宇充电桩 7.3 服务器回复充电桩确认收到指令 订单号: {}, 插座号: {}", orderNum, port);
                    step7Map.put(id, "7.4");
                }else if("7.4".equals(step7Map.get(id))) {
                    log.info("晓宇充电桩 7.4 服务器命令充电桩关闭充电");
                    byte[] openData = new byte[25];
                    System.arraycopy(orderNums, 0, openData, 0, 8);
                    openData[8] = port;
                    openData[9] = (byte) 0;
                    log.info("晓宇充电桩 7.4 服务器命令充电桩关闭充电 订单号: {}, 插座号: {}", orderNum, port);
                    // 回复服务器
                    openData[10] = (byte) 1; // 这个先随便写，用不到

                    // 服务器计费下面都写0
                    byte[] defaultFee = ByteBuffer.allocate(8).putShort((short) 0).array();
                    openData[11] = defaultFee[0];
                    openData[12] = defaultFee[1];

                    openData[13] = defaultFee[0];
                    openData[14] = defaultFee[1];

                    openData[15] = defaultFee[0];
                    openData[16] = defaultFee[1];

                    openData[17] = defaultFee[0];
                    openData[18] = defaultFee[1];

                    // 最大功率，单位 W
                    byte[] defaultElecMaxW = ByteBuffer.allocate(8).putShort((short) 2200).array();
                    openData[19] = defaultElecMaxW[0];
                    openData[20] = defaultElecMaxW[1];

                    byte[] nMinuteAverageW = ByteBuffer.allocate(8).putShort((short) 1100).array();
                    openData[21] = nMinuteAverageW[0];
                    openData[22] = nMinuteAverageW[1];

                    byte[] averageW = ByteBuffer.allocate(8).putShort((short) 200).array();
                    openData[23] = averageW[0];
                    openData[24] = averageW[1];
                    ctx.channel().writeAndFlush(XiaoyuUtil.getSendBody(id, function, openData));
                    log.info("晓宇充电桩 7.4 服务器命令充电桩关闭充电后 回复服务器信息: {}", AsciiUtils.hexString(openData));
                    step7Map.put(id, "7.6");
                }else if("7.6".equals(step7Map.get(id))) {
                    log.info("晓宇充电桩 7.6 服务器回复充电桩确认收到停止信息");
                    byte end = byteBuf.readByte();
                    log.info("晓宇充电桩 7.6 服务器回复充电桩确认收到停止信息 订单号: {}, 插座号: {}, 结束:0X02: {}", orderNum, port, end == 0x02);
                    step7Map.put(id, "7.1");
                    orderMap.remove(id);
                }
                break;
            case 0x12:
                log.info("晓宇充电桩 8.2 充电桩主动结束充电 服务器回复充电桩接收结束数据 订单号: {}, 插座号: {}", orderNum, port);
                break;
            default:
                break;
        }
        // 释放ByteBuf资源
        byteBuf.release();
    }

    // 连接异常处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.info("连接异常: {}", cause.getMessage());
        ctx.close();
    }


}
