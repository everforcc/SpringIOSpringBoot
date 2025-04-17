package cn.cc.netty.learn.d2.lineencoder.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //发送消息到服务端
        for (int i = 0; i < 5; i++) {
            ctx.writeAndFlush(Unpooled.copiedBuffer("msg No" + i + StringUtil.LINE_FEED, CharsetUtil.UTF_8));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //接收服务端发送过来的消息
        ByteBuf byteBuf = (ByteBuf) msg;
        log.info("收到服务端: {} 的消息: {}", ctx.channel().remoteAddress(), byteBuf.toString(CharsetUtil.UTF_8));
    }
}
