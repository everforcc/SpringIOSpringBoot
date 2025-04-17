package cn.cc.netty.learn.d2.lengthfieldbasedframedecoder.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
public class MyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 1; i <= 5; i++) {
            String str = "msg No" + i;
            ByteBuf byteBuf = Unpooled.buffer(1024);
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            //设置长度域的值，为有效数据的长度
            byteBuf.writeInt(bytes.length);
            //设置有效数据
            byteBuf.writeBytes(bytes);
            ctx.writeAndFlush(byteBuf);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //接收服务端发送过来的消息
        ByteBuf byteBuf = (ByteBuf) msg;
        log.info("收到服务端: {} 的消息: {}", ctx.channel().remoteAddress(), byteBuf.toString(CharsetUtil.UTF_8));
    }
}
