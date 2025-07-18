package cn.cc.netty.learn.mqtt.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public interface HttpRequestHandler {
    boolean supports(FullHttpRequest req);
    void handle(ChannelHandlerContext ctx, FullHttpRequest req);
} 