package cn.cc.netty.learn.mqtt;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.codec.mqtt.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * MqttClientHandler 是 Netty 客户端的核心业务处理器。
 * 它负责管理 MQTT 连接的整个生命周期，包括：
 * 1. 建立连接 (CONNECT)
 * 2. 处理连接响应 (CONNACK)
 * 3. 发送和处理订阅 (SUBSCRIBE, SUBACK)
 * 4. 接收发布的消息 (PUBLISH) 并根据 QoS 等级回复 (PUBACK)
 * 5. 维持心跳 (PINGREQ, PINGRESP)
 * 6. 处理连接断开和实现自动重连
 */
@Slf4j
public class MqttClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * MQTT 协议的 Keep Alive 时间（秒）。
     * 客户端会基于这个值，通过 IdleStateHandler 定期发送 PINGREQ 心跳包来维持长连接。
     */
    private static final int KEEP_ALIVE_SECONDS = 60;

    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final String topic;
    private boolean isReconnecting = false;
    private final Bootstrap bootstrap;

    /**
     * 构造函数
     * @param host MQTT Broker 地址
     * @param port MQTT Broker 端口
     * @param username MQTT 用户名
     * @param password MQTT 密码
     * @param topic    要订阅的主题
     * @param bootstrap Netty 客户端启动器，用于重连
     */
    public MqttClientHandler(String host, int port, String username, String password, String topic, Bootstrap bootstrap) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.topic = topic;
        this.bootstrap = bootstrap;
    }

    /**
     * 当TCP连接建立，通道激活时调用。
     * 此方法负责发送 MQTT CONNECT 报文以请求建立应用层连接。
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("Netty通道已激活，发送CONNECT报文请求建立MQTT连接...");
        MqttConnectMessage connectMessage = createConnectMessage();
        ctx.writeAndFlush(connectMessage).addListener(future -> {
            if (future.isSuccess()) {
                log.info("CONNECT报文发送成功，等待CONNACK响应...");
            } else {
                log.error("CONNECT报文发送失败", future.cause());
                ctx.close();
            }
        });
    }

    /**
     * 当从服务端接收到新消息时调用。
     * 这是处理所有入站 MQTT 报文的中心。
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            if (!(msg instanceof MqttMessage)) {
                log.warn("收到非MQTT消息: {}", msg.getClass().getName());
                return;
            }

            MqttMessage message = (MqttMessage) msg;
            MqttFixedHeader fixedHeader = message.fixedHeader();
            log.info("收到MQTT报文, 类型: {}", fixedHeader.messageType());

            switch (fixedHeader.messageType()) {
                case CONNACK:
                    handleConnAck(ctx, (MqttConnAckMessage) message);
                    break;
                case SUBACK:
                    handleSubAck((MqttSubAckMessage) message);
                    break;
                case PUBLISH:
                    handlePublish(ctx, (MqttPublishMessage) message);
                    break;
                case PINGRESP:
                    log.info("收到心跳响应 PINGRESP");
                    break;
                default:
                    log.warn("收到未处理的MQTT报文类型: {}", fixedHeader.messageType());
                    break;
            }
        } finally {
            // 确保消息的引用计数被正确释放，防止内存泄漏
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * 处理 CONNACK 连接确认报文。
     * 这是建立连接流程的关键一步。
     */
    private void handleConnAck(ChannelHandlerContext ctx, MqttConnAckMessage message) {
        MqttConnectReturnCode returnCode = message.variableHeader().connectReturnCode();
        if (returnCode == MqttConnectReturnCode.CONNECTION_ACCEPTED) {
            log.info("MQTT连接已成功建立 (CONNACK returnCode={})", returnCode);
            isReconnecting = false; // 连接成功，重置重连标记
            // 立即发送订阅请求
            MqttSubscribeMessage subscribeMessage = createSubscribeMessage();
            ctx.writeAndFlush(subscribeMessage).addListener(future -> {
                if (future.isSuccess()) {
                    log.info("SUBSCRIBE报文发送成功，订阅主题: {}", topic);
                } else {
                    log.error("SUBSCRIBE报文发送失败", future.cause());
                }
            });
        } else {
            log.error("MQTT连接失败，返回码: {}. 5秒后将再次尝试重连.", returnCode);
            // 如果连接被拒绝（例如，错误的用户名/密码），我们需要安排下一次重连。
            // 直接关闭会触发 channelInactive，但那里的重连逻辑可能因为 isReconnecting=true 而不执行。
            // 因此，在这里主动调度下一次重连是保证重连循环在认证失败时依然持续的关键。
            ctx.channel().eventLoop().schedule(this::doReconnect, 5, TimeUnit.SECONDS);
            ctx.close();
        }
    }

    /**
     * 处理 SUBACK 订阅确认报文。
     */
    private void handleSubAck(MqttSubAckMessage message) {
        log.info("主题订阅成功: messageId={}, grantedQoSLevels={}",
                message.variableHeader().messageId(),
                message.payload().grantedQoSLevels());
    }

    /**
     * 处理 PUBLISH 发布报文，即收到的业务消息。
     */
    private void handlePublish(ChannelHandlerContext ctx, MqttPublishMessage message) {
        String actualTopic = message.variableHeader().topicName();
        String content = message.payload().toString(CharsetUtil.UTF_8);
        log.info("收到消息: 主题=[{}], 内容=[{}]", actualTopic, content);

        // 对于QoS 1的消息，客户端必须回复一个PUBACK报文
        if (message.fixedHeader().qosLevel() == MqttQoS.AT_LEAST_ONCE) {
            MqttPubAckMessage pubAckMessage = createPubAckMessage(message.variableHeader().packetId());
            ctx.writeAndFlush(pubAckMessage).addListener(future -> {
                if (future.isSuccess()) {
                    log.info("PUBACK报文发送成功, 确认 messageId: {}", message.variableHeader().packetId());
                } else {
                    log.error("PUBACK报文发送失败, messageId: {}", message.variableHeader().packetId(), future.cause());
                }
            });
        }
    }

    /**
     * 创建 CONNECT 报文。
     */
    private MqttConnectMessage createConnectMessage() {
        MqttFixedHeader fixedHeader = new MqttFixedHeader(
                MqttMessageType.CONNECT, false, MqttQoS.AT_MOST_ONCE, false, 0);

        MqttConnectVariableHeader variableHeader = new MqttConnectVariableHeader(
                "MQTT", // 协议名
                4,      // 协议级别: MQTT 3.1.1
                true,   // 包含用户名
                true,   // 包含密码
                false,  // 无遗嘱消息
                0,      // 遗嘱QoS
                false,  // 无遗嘱标志
                true,   // 清理会话 (Clean Session)
                KEEP_ALIVE_SECONDS); // 心跳时间

        // 客户端ID必须唯一，这里使用UUID保证
        String clientId = "netty-client-" + UUID.randomUUID().toString().substring(0, 8);
        log.info("生成客户端ID: {}", clientId);
        MqttConnectPayload payload = new MqttConnectPayload(
                clientId,
                null, null, username, password.getBytes(CharsetUtil.UTF_8));

        return (MqttConnectMessage) MqttMessageFactory.newMessage(fixedHeader, variableHeader, payload);
    }

    /**
     * 创建 SUBSCRIBE 报文。
     */
    private MqttSubscribeMessage createSubscribeMessage() {
        MqttTopicSubscription subscription = new MqttTopicSubscription(topic, MqttQoS.AT_LEAST_ONCE);
        MqttSubscribePayload payload = new MqttSubscribePayload(Collections.singletonList(subscription));
        MqttFixedHeader fixedHeader = new MqttFixedHeader(
                MqttMessageType.SUBSCRIBE, false, MqttQoS.AT_LEAST_ONCE, false, 0);
        MqttMessageIdVariableHeader variableHeader = MqttMessageIdVariableHeader.from(1);
        return new MqttSubscribeMessage(fixedHeader, variableHeader, payload);
    }

    /**
     * 创建 PUBACK 报文。
     * @param messageId 需要确认的 PUBLISH 报文的 ID
     */
    private MqttPubAckMessage createPubAckMessage(int messageId) {
        MqttFixedHeader fixedHeader = new MqttFixedHeader(MqttMessageType.PUBACK, false, MqttQoS.AT_MOST_ONCE, false, 0);
        MqttMessageIdVariableHeader variableHeader = MqttMessageIdVariableHeader.from(messageId);
        return new MqttPubAckMessage(fixedHeader, variableHeader);
    }

    /**
     * 当TCP连接断开，通道变为非激活状态时调用。
     * 这是触发自动重连机制的入口。
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.warn("TCP连接已断开, 通道 inactive.");
        // 防止在重连过程中重复触发
        if (!isReconnecting) {
            isReconnecting = true;
            log.info("准备在5秒后开始第一次重连尝试...");
            ctx.channel().eventLoop().schedule(this::doReconnect, 5, TimeUnit.SECONDS);
        }
        super.channelInactive(ctx);
    }

    /**
     * 执行重连的核心逻辑。
     * 使用 bootstrap 重新发起连接。
     */
    private void doReconnect() {
        if (bootstrap == null) {
            log.error("Bootstrap 实例为空，无法执行重连。");
            isReconnecting = false; // 停止重连循环
            return;
        }

        EventLoopGroup group = bootstrap.config().group();
        if (group == null || group.isShutdown() || group.isShuttingDown()) {
            log.warn("EventLoopGroup 已关闭，无法执行重连。");
            isReconnecting = false; // 停止重连循环
            return;
        }

        log.info("正在执行重连 -> {}:{}", this.host, this.port);
        bootstrap.connect(this.host, this.port).addListener((ChannelFuture future) -> {
            if (future.isSuccess()) {
                // TCP连接已成功建立，接下来需要等待MQTT的CONNACK报文
                log.info("重连成功，新的Netty通道已建立。等待MQTT CONNACK响应...");
                // isReconnecting 标志将在收到成功的 CONNACK 后被重置为 false
            } else {
                log.warn("重连失败: {}. 5秒后将进行下一次尝试。", future.cause().getMessage());
                // 如果连接失败，安排下一次重连
                future.channel().eventLoop().schedule(this::doReconnect, 5, TimeUnit.SECONDS);
            }
        });
    }

    /**
     * 当 IdleStateHandler 检测到通道空闲事件时调用。
     * 我们用它来发送心跳包。
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            // 当通道在指定时间内没有写操作时，触发写空闲事件
            if (e.state() == IdleState.WRITER_IDLE) {
                log.info("通道写空闲，发送PINGREQ心跳包以保持连接...");
                MqttMessage pingreqMessage = new MqttMessage(
                        new MqttFixedHeader(MqttMessageType.PINGREQ, false, MqttQoS.AT_MOST_ONCE, false, 0)
                );
                ctx.writeAndFlush(pingreqMessage);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    /**
     * 在处理过程中发生异常时调用。
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("通道处理发生未捕获异常，将关闭连接", cause);
        ctx.close();
    }
}