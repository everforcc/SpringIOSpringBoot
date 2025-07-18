package cn.cc.netty.learn.mqtt;

import cn.cc.netty.learn.mqtt.http.NettyHttpServer;
import cn.cc.netty.learn.mqtt.mqtt.MqttClient;
import cn.cc.netty.learn.mqtt.mqtt.handler.MqttClientHandler;
import cn.cc.netty.learn.mqtt.mqtt.publish.MqttPublishService;
import cn.cc.netty.learn.mqtt.mqtt.publish.MqttPublishServiceImpl;

/**
 * 主程序：启动 MQTT 客户端和 Netty HTTP 服务。
 * 1. 先启动 MQTT 客户端，建立与 Broker 的连接。
 * 2. 再启动 HTTP 服务，接收外部请求并发布 MQTT 消息。
 */
public class MainWithHttp {
    public static void main(String[] args) throws Exception {
        // 1. 启动 MQTT 客户端
        MqttClient mqttClient = new MqttClient();
        mqttClient.connect();
        // 等待连接建立（实际可用更优雅的同步方式）
        Thread.sleep(3000);
        MqttClientHandler handler = mqttClient.getHandler();

        // 2. 封装发布服务
        MqttPublishService publishService = new MqttPublishServiceImpl(handler);

        // 3. 启动 Netty HTTP 服务（端口 8080）
        new Thread(() -> {
            try {
                new NettyHttpServer(8080, publishService).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // 4. 添加 JVM 关闭钩子，优雅关闭
        Runtime.getRuntime().addShutdownHook(new Thread(mqttClient::shutdown));
    }
} 