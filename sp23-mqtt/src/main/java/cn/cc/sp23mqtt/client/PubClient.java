package cn.cc.sp23mqtt.client;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;

/**
 * mqtt 发布消息
 */
public class PubClient {

    public static void main(String[] args) {
        try {
            String messageStr = "我是消息";

            // 第一次名字写错了
            //  创建MQTT客户端
            MqttClient mqttClient = new MqttClient("tcp://8.146.199.165:1883", "pub-cli-01", new MemoryPersistence());

            //
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setCleanSession(true);

            mqttClient.connect(mqttConnectOptions);

            // 创建消息对象
            MqttMessage message = new MqttMessage(messageStr.getBytes(StandardCharsets.UTF_8));
            // 设置服务质量
            message.setQos(2);

            // 发布
            mqttClient.publish("sd", message);

            mqttClient.disconnect();
            mqttClient.close();

            System.out.println("发布消息完毕，断开");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
