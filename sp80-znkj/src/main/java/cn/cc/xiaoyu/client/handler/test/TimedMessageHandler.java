package cn.cc.xiaoyu.client.handler.test;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TimedMessageHandler extends ChannelInboundHandlerAdapter {
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    private int seconds;

    public TimedMessageHandler(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 连接建立后启动定时任务
        scheduler.scheduleAtFixedRate(() -> {
            if (ctx.channel().isActive()) {
                log.info("发送定时消息");
//                ctx.writeAndFlush(message);
            }
        }, 0, seconds, TimeUnit.SECONDS); // 每30秒发送一次

        super.channelActive(ctx);
    }
}
