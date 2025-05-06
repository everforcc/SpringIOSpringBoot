package cn.cc.netty.learn.redis.netty.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * 执行Command中的命令。
 */
@Slf4j
@ChannelHandler.Sharable
public class RedisCommandHandler extends SimpleChannelInboundHandler<RedisCommand> {

    private HashMap<String, byte[]> database = new HashMap<String, byte[]>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RedisCommand msg) throws Exception {
        System.out.println("RedisCommandHandler: " + msg);

        if (msg.getName().equalsIgnoreCase("set")) {
            if (database.put(new String(msg.getArg1()), msg.getArg2()) == null) {
                ctx.writeAndFlush(new IntegerReply(1));
            } else {
                ctx.writeAndFlush(new IntegerReply(0));
            }
        } else if (msg.getName().equalsIgnoreCase("get")) {
            byte[] value = database.get(new String(msg.getArg1()));
            if (value != null && value.length > 0) {
                ctx.writeAndFlush(new BulkReply(value));
            } else {
                ctx.writeAndFlush(BulkReply.NIL_REPLY);
            }
        } else if (msg.getName().equalsIgnoreCase("auth")) {
            String pas = new String(msg.getArg1());
            log.info("密码：" + pas);
            if ("cc".equals(pas)) {
                log.info("密码正确");
                ctx.writeAndFlush(new IntegerReply(1));
            } else {
                log.info("密码错误");
                ctx.writeAndFlush(new IntegerReply(0));
            }
        } else {
            log.info("连接操作");
            ctx.writeAndFlush(new IntegerReply(1));
        }
    }

}