package cn.cc.xiaoyu.client.handler;

import cn.cc.xiaoyu.util.CharUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XiaoYuClientHandler extends ChannelInboundHandlerAdapter {
    // TODO 连接建立好了以后触发

    /**
     * 1. 模拟充电桩登录
     * <p>
     * ,
     * <p>
     * (byte) 0xd5, 0x5d,
     * 0x52, 0x0, 0x1, (byte) 0x81,
     * 0x1,
     * 0x0, 0x18,
     * 0x38, 0x39, 0x38, 0x36,
     * 0x30, 0x34, 0x36, 0x31,
     * 0x31, 0x36, 0x31, 0x39,
     * 0x37, 0x32, 0x37, 0x37,
     * 0x34, 0x32, 0x39, 0x30,
     * 0xb, 0x1b, 0x14, 0xf,
     * 0x72, (byte) 0xed,
     * (byte)0xb3, 0x3b
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf byteBuf = ctx.alloc().buffer(37);
        // byteBuf 写入十六进制 [0, d5, 5d, 52, 0, 1, 81, 1, 0, 18, 38, 39, 38, 36, 30, 34, 36, 31, 31, 36, 31, 39, 37, 32, 37, 37, 34, 32, 39, 30, b, 1b, 14, f, 72, ed, b3, 3b, d5, 5d, 52, 0, 1, 81, 1, 0, 18, 38, 39, 38, 36, 30, 34, 36, 31, 31, 36, 31, 39, 37, 32, 37, 37, 34, 32, 39, 30, b, 1b, 14, f, 72, ed, b3, 3b]
        byteBuf.writeBytes(new byte[]{
                0x0, (byte) 0xd5, 0x5d,
                0x52, 0x0, 0x1, (byte) 0x81,
                0x1,
                0x0, 0x18,

                0x38, 0x39, 0x38, 0x36,
                0x30, 0x34, 0x36, 0x31,
                0x31, 0x36, 0x31, 0x39,
                0x37, 0x32, 0x37, 0x37,
                0x34, 0x32, 0x39, 0x30,
                0xb, 0x1b, 0x14, 0xf,

                0x72, (byte) 0xed,
                (byte) 0xb3, 0x3b}
        );
        ctx.writeAndFlush(byteBuf);
        log.info("模拟充电桩客户端发送登录请求...");
    }

    // 接收服务器响应
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // todo-文档响应 1.登录 2.XXX
        // 接收服务器返回的数据
        ByteBuf byteBuf = (ByteBuf) msg;
        log.info("收到服务器响应: {}", CharUtils.byteBufToHex(byteBuf));

        byteBuf.resetReaderIndex();
        // 处理响应数据
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);

        // 打印十六进制格式
        log.info("响应数据长度: {}", bytes.length);
        log.info("响应数据(十六进制): {}", CharUtils.bytesToHex(bytes));

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
