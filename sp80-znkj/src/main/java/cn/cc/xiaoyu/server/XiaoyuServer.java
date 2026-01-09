package cn.cc.xiaoyu.server;

import cn.cc.xiaoyu.server.handler.XiaoyuChannelHandler;
import cn.cc.xiaoyu.server.handler.XiaoyuDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XiaoyuServer {


    public static void start(int port) {
        new Thread(() -> {
            EventLoopGroup group = new NioEventLoopGroup();
            try {
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(group)
                        .channel(NioServerSocketChannel.class)
                        .localAddress(port)
                        .childHandler(new ChannelInitializer<Channel>() {
                            @Override
                            protected void initChannel(Channel ch) throws Exception {
                                ch.pipeline()
                                        //这里添加处理器
                                        .addLast(new IdleStateHandler(120, 0, 0))
                                        //这里添加处理器
                                        // 打印接受到的数据
                                        .addLast(new XiaoyuDecoder())
                                        .addLast(new XiaoyuChannelHandler())
                                ;

                            }
                        });
                ChannelFuture future = bootstrap.bind().sync();

                log.info("======= xiaoyu 启动: {}", port);
                future.channel().closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                group.shutdownGracefully();
            }
        }).start();

    }

}
