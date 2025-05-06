package cn.cc.netty.learn.redis.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 将响应写入到输出流中，返回给客户端。
 */
public class RedisReplyEncoder extends MessageToByteEncoder<RedisReply> {

    @Override
    protected void encode(ChannelHandlerContext ctx, RedisReply msg, ByteBuf out) throws Exception {
        System.out.println("RedisReplyEncoder: " + msg);
        msg.write(out);
    }

}