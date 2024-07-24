package cn.cc.sp23mqtt.client;


import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * mqtt 接收消息
 */
public class SubClient {

    public static void main(String[] args) {
        try {
            MqttClient mqttClient = new MqttClient("tcp://8.146.199.165:1883", "sub-cli-01", new MemoryPersistence());

            // 服务质量 心跳
            // 创建连接配置
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            // 死了活过来认为是新的
            mqttConnectOptions.setCleanSession(true);

            // 将配置设置到客户端
            mqttClient.connect(mqttConnectOptions);

            // 配置回调函数，等待消息
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                    System.out.println("连接断开");
                    throwable.printStackTrace();
                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                    System.out.println("主题: " + s);
                    System.out.println("接收消息: " + new String(mqttMessage.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    System.out.println("传递消息完成");
                }
            });

            mqttClient.subscribe("sd");
            System.out.println("订阅已经准备好了");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
