/**
 * @Description
 * @Author everforcc
 * @Date 2022-12-14 10:02
 * Copyright
 */

package cn.cc.xiaoyu.client;

import cn.cc.xiaoyu.client.handler.XiaoYuClientHandler;
import cn.cc.xiaoyu.client.handler.XiaoYuHeartHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 1. 模拟充电桩发送请求
 */
@Slf4j
public class XiaoYuClient {

    public static void start() {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(worker);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    // 流水线添加事件
                    ChannelPipeline pipeline = ch.pipeline();
                    /**
                     * 功能：监控连接的读写空闲状态
                     * 参数说明：
                     * 第1个参数(0)：读空闲时间，0表示不检测读空闲
                     * 第2个参数(30)：写空闲时间，30秒内无写操作触发写空闲事件
                     * 第3个参数(0)：总空闲时间，0表示不检测总空闲
                     */
                    pipeline.addLast(new IdleStateHandler(0, 60, 0));
                    /**
                     * 功能：处理空闲事件，发送心跳包
                     * 依赖：需要 IdleStateHandler 产生的空闲事件
                     * 触发条件：当检测到写空闲时发送心跳数据
                     */
                    pipeline.addLast(new XiaoYuHeartHandler());
                    /**
                     * 功能：处理入站事件
                     * 主要方法：
                     * channelActive：连接建立后发送初始数据包
                     * channelRead：接收并处理服务器响应
                     * exceptionCaught：处理连接异常
                     */
                    pipeline.addLast(new XiaoYuClientHandler());
                }
            });

            // 链接服务器
            // https://dev-znyd.zgzhongnan.com/cc 80
            // 125.40.67.238 16999
//            ChannelFuture channelFuture = bootstrap.connect("192.168.1.188", 9999).sync();
//            ChannelFuture channelFuture = bootstrap.connect("125.40.67.238", 16999).sync();
            ChannelFuture channelFuture = bootstrap.connect("zzhx.zgzhongnan.com", 16999).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (Exception e) {
            log.error("client error", e);
        } finally {
            worker.shutdownGracefully();
        }
    }


}
