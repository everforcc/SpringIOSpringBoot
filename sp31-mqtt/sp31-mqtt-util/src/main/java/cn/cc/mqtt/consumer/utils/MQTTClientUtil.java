package cn.cc.mqtt.consumer.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Getter
@NoArgsConstructor
@Component
public class MQTTClientUtil {

    @Value("${spring.mqtt.host-url}")
    private String brokerUrl;
    @Value("${spring.mqtt.username}")
    private String username;
    @Value("${spring.mqtt.password}")
    private String password;

    private String clientId;

    private MqttClient mqttClient;

    private final Map<String, IMqttMessageListener> topics = new LinkedHashMap<>();

    /**
     * 创建mqtt客户端
     *
     * @param brokerUrl mqtt服务的地址
     * @param username  用户名
     * @param password  密码
     * @return MQTTClientBean
     */
    public static MQTTClientUtil create(String brokerUrl, String username, String password) {
        MQTTClientUtil mqttClientUtil;

        try {
            String clientId = UUID.randomUUID().toString();
            //创建mqtt客户端
            MqttClient mqttClient = new MqttClient(brokerUrl, clientId, new MemoryPersistence());
            log.info("mqtt 客户端 {} brokerUrl--->{}", clientId, brokerUrl);
            log.info("mqtt 客户端 {} username--->{}", clientId, username);
            log.info("mqtt 客户端 {} password--->{}", clientId, password);
            //设置
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(username);
            if (password != null)
                options.setPassword(password.toCharArray());
            options.setConnectionTimeout(60);
            options.setKeepAliveInterval(60);
            options.setCleanSession(true);
            options.setAutomaticReconnect(true);
            mqttClient.connect(options);

            mqttClientUtil = new MQTTClientUtil();
            mqttClientUtil.brokerUrl = brokerUrl;
            mqttClientUtil.username = username;
            mqttClientUtil.password = password;
            mqttClientUtil.clientId = clientId;
            mqttClientUtil.mqttClient = mqttClient;

            mqttClient.setCallback(new MyMqttCallbackExtended(mqttClientUtil));

        } catch (Exception e) {
            log.error("MQTT创建错误", e);
            throw new RuntimeException("MQTT创建错误", e);
        }

        return mqttClientUtil;
    }

    /**
     * 发布
     *
     * @param topic   主题
     * @param content 消息
     */
    public void publish(String topic, String content) {
        try {
            if (mqttClient == null || !mqttClient.isConnected()) {
                throw new RuntimeException("MQTT客户端为空或未连接！");
            }


            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(0);
            log.info("mqtt 发布 {} {}--->{}", clientId, topic, content);
            mqttClient.publish(topic, message);
            mqttClient.disconnect();
            mqttClient.close();
            log.info("mqtt 发布 {} {}--->成功！", clientId, topic);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 发布
     *
     * @param topic   主题
     * @param content 消息
     */
    public void publishAway(String topic, String content) {
        try {
            if (mqttClient == null || !mqttClient.isConnected()) {
                throw new RuntimeException("MQTT客户端为空或未连接！");
            }


            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(0);
            log.info("mqtt 发布 {} {}--->{}", clientId, topic, content);
            mqttClient.publish(topic, message);
            log.info("mqtt 发布 {} {}--->成功！", clientId, topic);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    /**
     * 订阅
     *
     * @param topic    主题
     * @param listener 消息监听器
     */
    public void subscribe(String topic, IMqttMessageListener listener) {
        try {
            if (mqttClient == null || !mqttClient.isConnected()) {
                throw new RuntimeException("MQTT客户端为空或未连接！");
            }
            topics.put(topic, listener);
            mqttClient.subscribe(topic, 0, listener);
        } catch (Exception e) {
            log.error("错误", e);
            throw new RuntimeException("MQTT订阅失败！");
        }

    }


    public static class MyMqttCallbackExtended implements MqttCallbackExtended {
        private final MQTTClientUtil mqttClientUtil;

        public MyMqttCallbackExtended(MQTTClientUtil mqttClientUtil) {
            this.mqttClientUtil = mqttClientUtil;
        }

        @Override
        public void connectComplete(boolean b, String s) {
            log.info("回调连接成功:{}", mqttClientUtil.getClientId());
            Map<String, IMqttMessageListener> topics = mqttClientUtil.getTopics();
            Set<String> keys = topics.keySet();
            for (String key : keys) {
                mqttClientUtil.subscribe(key, topics.get(key));
            }
        }

        @Override
        public void connectionLost(Throwable throwable) {
            log.info("回调连接丢失:{}", mqttClientUtil.getClientId());
        }

        @Override
        public void messageArrived(String s, MqttMessage mqttMessage) {

        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

        }
    }
}
