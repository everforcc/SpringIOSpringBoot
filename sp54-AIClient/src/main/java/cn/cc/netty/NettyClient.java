package cn.cc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.function.Consumer;
import io.netty.channel.Channel;

public class NettyClient {
    private final String host;
    private final int port;
    private final Consumer<String> messageCallback;
    private Channel channel;

    public NettyClient(String host, int port, Consumer<String> messageCallback) {
        this.host = host;
        this.port = port;
        this.messageCallback = messageCallback;
    }

    public void startWithFirstMsg(String firstMsg) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ClientInitializer(this));
            ChannelFuture f = b.connect(host, port).sync();
            channel = f.channel();
            channel.writeAndFlush(firstMsg);
            System.out.println("Netty 客户端已连接: " + host + ":" + port);
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public void onMessage(String msg) {
        if (messageCallback != null) {
            messageCallback.accept(msg);
        }
    }

    public void send(String msg) {
        if (channel != null) {
            channel.writeAndFlush(msg);
        }
    }
} 