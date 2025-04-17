package cn.cc.netty.learn.ai.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ConcurrentHashMap;

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {
    private static ConcurrentHashMap<String, ChannelHandlerContext> users = new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("Received message: " + msg);
        for (ChannelHandlerContext user : users.values()) {
            user.writeAndFlush(msg + "\n");
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        users.put(ctx.channel().remoteAddress().toString(), ctx);
        System.out.println("User connected: " + ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        users.remove(ctx.channel().remoteAddress().toString());
        System.out.println("User disconnected: " + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}