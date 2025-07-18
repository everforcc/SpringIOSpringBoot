package cn.cc.netty.learn.mqtt;

import cn.cc.netty.learn.mqtt.handler.HttpRequestHandler;
import cn.cc.netty.learn.mqtt.handler.impl.PublishHandler;
import cn.cc.netty.learn.mqtt.mqtt.publish.MqttPublishService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Netty HTTP 服务，专门负责接收 HTTP 请求并调用 MQTT 发布。
 * 业务与 MQTT 客户端解耦。
 */
public class NettyHttpServer {
    private final int port;
    private final MqttPublishService publishService;
    private final List<HttpRequestHandler> handlers = new ArrayList<>();

    /**
     * 构造方法
     * @param port HTTP 服务端口
     * @param publishService MQTT 发布服务
     */
    public NettyHttpServer(int port, MqttPublishService publishService) {
        this.port = port;
        this.publishService = publishService;
        // 注册所有 handler
        handlers.add(new PublishHandler(publishService));
        // 以后可以继续 add 新的 handler
    }

    /**
     * 启动 HTTP 服务
     */
    public void start() throws Exception {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, worker)
             .channel(NioServerSocketChannel.class)
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 protected void initChannel(SocketChannel ch) {
                     ch.pipeline().addLast(new HttpServerCodec());
                     ch.pipeline().addLast(new HttpObjectAggregator(65536));
                     ch.pipeline().addLast(new SimpleChannelInboundHandler<FullHttpRequest>() {
                         @Override
                         protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) {
                             for (HttpRequestHandler handler : handlers) {
                                 if (handler.supports(req)) {
                                     handler.handle(ctx, req);
                                     return;
                                 }
                             }
                             ctx.writeAndFlush(new DefaultFullHttpResponse(
                                     HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND));
                         }

                         @Override
                         public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                             // 只打印警告，不抛出，避免日志刷屏
                             System.err.println("[NettyHttpServer] 连接异常: " + cause.getMessage());
                             ctx.close();
                         }
                     });
                 }
             });
            Channel ch = b.bind(port).sync().channel();
            System.out.println("HTTP服务已启动，端口：" + port);
            ch.closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
