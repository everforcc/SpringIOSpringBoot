package cn.cc.xiaoyu.client.handler.test;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class TimedSendHandler extends ChannelInboundHandlerAdapter {

    private long intervalSeconds;

    public TimedSendHandler(long intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 启动定时发送任务
        ctx.executor().scheduleAtFixedRate(() -> {
            if (ctx.channel().isActive()) {
                log.info("定时发送消息");
//                ctx.writeAndFlush(message);
            }
        }, 0, intervalSeconds, TimeUnit.SECONDS);

        super.channelActive(ctx);
    }
}

