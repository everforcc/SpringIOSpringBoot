package cn.cc.netty.learn.mqtt;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * MQTT 客户端启动器
 *
 * 负责初始化 Netty 的 Bootstrap, 配置 ChannelPipeline, 并发起初始连接。
 * 核心设计思想是，本类只负责发起第一次连接，一旦连接建立，
 * 连接的生命周期管理（心跳、断线重连）将完全交给 {@link MqttClientHandler} 来处理。
 * 这种分离使得客户端的启动逻辑和连接维持逻辑解耦，更加清晰。
 */
@Slf4j
public class MqttClient {

    private static final String MQTT_HOST = "192.168.3.39";
    private static final int MQTT_PORT = 1883;
    private static final String MQTT_USERNAME = "admin";
    private static final String MQTT_PASSWORD = "public";
    private static final String MQTT_TOPIC = "znkj/largeScreen/humidness/alarm";

    private final EventLoopGroup group;
    private final Bootstrap bootstrap;

    public MqttClient() {
        // 创建 Netty 的核心组件: EventLoopGroup 和 Bootstrap
        this.group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();

        bootstrap.group(group)
                 .channel(NioSocketChannel.class)
                 .option(ChannelOption.TCP_NODELAY, true) // 禁用 Nagle 算法，提高实时性
                 .handler(new ChannelInitializer<SocketChannel>() {
                     @Override
                     protected void initChannel(SocketChannel ch) {
                         ChannelPipeline pipeline = ch.pipeline();
                         // --- 协议处理 ---
                         // Netty Pipeline 中的 Handler 是有顺序的，处理入站消息时从前到后，处理出站消息时从后到前。
                         // 1. MqttDecoder: 入站时，将字节流解码为 MQTT 报文对象
                         pipeline.addLast("decoder", new MqttDecoder());
                         // 2. MqttEncoder: 出站时，将 MQTT 报文对象编码为字节流
                         pipeline.addLast("encoder", MqttEncoder.INSTANCE);

                         // --- 心跳与空闲检测 ---
                         // 3. IdleStateHandler: 必须在业务 handler 之前。
                         //    当45秒内没有发生任何写操作时，会触发一个 IdleStateEvent.WRITER_IDLE 事件。
                         //    这个事件会沿着 pipeline 向后传递，被我们的 MqttClientHandler 捕获并处理（发送心跳包）。
                         pipeline.addLast("idleStateHandler", new IdleStateHandler(0, 45, 0, TimeUnit.SECONDS));

                         // --- 业务逻辑 ---
                         // 4. 核心业务处理器。
                         //    ！！！关键点：每次有新连接建立时，都必须创建一个新的 Handler 实例。
                         //    Handler 不能在多个 Channel 之间共享，因为它是有状态的（如 isReconnecting 标志）。
                         pipeline.addLast("handler", new MqttClientHandler(MQTT_HOST, MQTT_PORT, MQTT_USERNAME, MQTT_PASSWORD, MQTT_TOPIC, bootstrap));
                     }
                 });
    }

    /**
     * 发起连接。
     * 此方法是异步的，它会立即返回，连接过程在后台进行。
     */
    public void connect() {
        log.info("正在连接到 MQTT Broker -> {}:{}", MQTT_HOST, MQTT_PORT);
        bootstrap.connect(MQTT_HOST, MQTT_PORT).addListener((ChannelFuture future) -> {
            if (future.isSuccess()) {
                log.info("首次连接成功. Channel: {}", future.channel());
            } else {
                log.warn("首次连接失败: {}. 将在5秒后尝试重连.", future.cause().getMessage());
                // 如果首次连接失败，则安排一个5秒后的重连任务。
                // 这里的重连是针对启动时无法连接到服务器的情况。
                // 一旦连接成功后，运行中的断线重连将由 MqttClientHandler 负责。
                future.channel().eventLoop().schedule(this::connect, 5, TimeUnit.SECONDS);
            }
        });
    }

    /**
     * 关闭客户端，释放资源。
     */
    public void shutdown() {
        log.info("正在关闭 MQTT 客户端...");
        group.shutdownGracefully();
        log.info("客户端已关闭。");
    }

    public static void main(String[] args) {
        MqttClient client = new MqttClient();

        // 发起连接
        client.connect();

        // 添加 JVM 关闭钩子，确保在程序退出时能优雅地关闭 Netty 客户端
        Runtime.getRuntime().addShutdownHook(new Thread(client::shutdown));
    }
}