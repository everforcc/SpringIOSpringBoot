package cn.cc.netty.learn.redis.netty;

import cn.cc.netty.learn.redis.netty.server.RedisCommandDecoder;
import cn.cc.netty.learn.redis.netty.server.RedisCommandHandler;
import cn.cc.netty.learn.redis.netty.server.RedisReplyEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Launch {

    public static void main(String[] args) throws Exception {
        new Launch().start(6379);
    }

    public void start(int port) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap()
                    .group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(port)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new RedisCommandDecoder())
                                    .addLast(new RedisReplyEncoder())
                                    .addLast(new RedisCommandHandler());
                        }
                    });

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync();

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } finally {
            // Shutdown the EventLoopGroup, which releases all resources.
            group.shutdownGracefully();
        }
    }

}
