package cn.cc.netty.learn.mqtt.mqtt.publish;

import cn.cc.netty.learn.mqtt.mqtt.handler.MqttClientHandler;

/**
 * MQTT 发布服务实现，内部持有 MqttClientHandler 实例，负责实际消息发布。
 */
public class MqttPublishServiceImpl implements MqttPublishService {
    private final MqttClientHandler handler;

    /**
     * 构造方法
     * @param handler MQTT 客户端 Handler
     */
    public MqttPublishServiceImpl(MqttClientHandler handler) {
        this.handler = handler;
    }

    @Override
    public boolean publish(String topic, String payload) {
        if (handler != null) {
            return handler.publishMessage(topic, payload);
        }
        return false;
    }
} 