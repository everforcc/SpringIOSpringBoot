package cn.cc.netty.learn.mqtt.mqtt.publish;

/**
 * MQTT 发布服务接口，专门负责封装 MQTT 消息发布逻辑。
 * 便于与 HTTP 服务解耦。
 */
public interface MqttPublishService {
    /**
     * 发布消息到指定 topic
     * @param topic 主题
     * @param payload 消息内容
     * @return 是否发送成功
     */
    boolean publish(String topic, String payload);
} 