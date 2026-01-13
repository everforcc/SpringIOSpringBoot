package cn.cc.xiaoyu.client.handler;

import cn.cc.xiaoyu.client.instant.IXiaoYuClient;
import cn.cc.xiaoyu.util.XiaoyuUtil;
import cn.cc.xiaoyu.util.charutil.AsciiUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

/**
 * 模拟充电桩接收指令，发送请求
 */
@Slf4j
public class XiaoYuClientHandler extends ChannelInboundHandlerAdapter {

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
    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf byteBuf = ctx.alloc().buffer(37);
        // byteBuf 写入十六进制 [0, d5, 5d, 52, 0, 1, 81, 72, ed, b3, 3b]
        byteBuf.writeBytes(iXiaoYuClient.getLoginData());
        ctx.writeAndFlush(byteBuf);
        log.info("模拟充电桩客户端发送登录请求: {}", AsciiUtils.hexString(iXiaoYuClient.getLoginData()));
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

        // 打印十六进制格式
        log.info("响应数据长度: {}", bytes.length);

        byte function = bytes[6];
        switch (function) {
            case 0x10:
                if(dataLength == 0X0010) {
                    log.info("晓宇充电桩 7.1 服务器命令充电桩开启充电");
                    long orderNum = byteBuf.readLong();
                    byte[] orderNums = ByteBuffer.allocate(8).putLong(orderNum).array();
                    byte port = byteBuf.readByte();
                    byte[] openData = new byte[11];
                    System.arraycopy(orderNums, 0, openData, 0, 8);
                    openData[8] = port;
                    openData[9] = (byte) 1;
                    openData[10] = (byte) 1;
                    ctx.channel().writeAndFlush(XiaoyuUtil.getSendBody(id, function, openData));
                    log.info("晓宇充电桩 7.2 充电桩开启后回复服务器信息: {}", AsciiUtils.hexString(openData));
                }else if(dataLength == 0X000A) {
                    log.info("晓宇充电桩 7.3 服务器回复充电桩确认收到指令");
                    long orderNum = byteBuf.readLong();
                    byte[] orderNums = ByteBuffer.allocate(8).putLong(orderNum).array();
                    byte port = byteBuf.readByte();
                    log.info("晓宇充电桩 7.3 服务器回复充电桩确认收到指令 订单号: {}, 插座号: {}", orderNums, port);
                }
                break;
            case 0x11:
                log.info("晓宇充电桩 7.4 服务器命令充电桩关闭充电");
                log.info("晓宇充电桩 7.5 充电桩向服务器回复结束指");
                log.info("晓宇充电桩 7.6 服务器回复充电桩确认收到停止信息");
                break;
            case 0x12:
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
