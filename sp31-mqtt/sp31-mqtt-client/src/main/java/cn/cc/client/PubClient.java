package cn.cc.client;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;

/**
 * mqtt 发布消息
 */
public class PubClient {

    public static final String messageStr = "消息: " + System.currentTimeMillis();

    public static void main(String[] args) {
        try {
//            CountDownLatch latch = new CountDownLatch(1);
            // 第一次名字写错了
            //  创建MQTT客户端
            MqttClient mqttClient = new MqttClient(MQTTConstant.serverURI, MQTTConstant.pub_clientId, new MemoryPersistence());

            //
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setCleanSession(true);
            mqttConnectOptions.setUserName(MQTTConstant.userName);
            mqttConnectOptions.setPassword(MQTTConstant.password);


            mqttClient.connect(mqttConnectOptions);


            // 创建消息对象
            MqttMessage message = new MqttMessage(messageStr.getBytes(StandardCharsets.UTF_8));
            // 设置服务质量
            message.setQos(2);
            int messageId = 123;
            message.setId(messageId);

            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {

                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("messageArrived: " + topic);
                    System.out.println("messageArrived: " + new String(message.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    if (token.isComplete()) {
                        try {
                            // 如果消息发送成功，这里message是null
                            System.out.println("token.getMessage(): " + token.getMessage() + " ,token.getMessageId(): " + token.getMessageId());
                            if (messageId == token.getMessageId()) {
                                System.out.println("消息确认发布成功");
                            }
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // todo 没确定如何模拟
                        System.out.println("token.getException(): " + token.getException());
                    }
                }
            });

            System.out.println("发布消息");
            // 发布
            mqttClient.publish(MQTTConstant.topic, message);
//            System.out.println(mqttClient.getPendingDeliveryTokens().length);
//            System.out.println("mmmm" + mqttClient.getPendingDeliveryTokens()[0].getMessageId());

            // 发布
            mqttClient.publish(MQTTConstant.topic, message);
//            System.out.println(mqttClient.getPendingDeliveryTokens().length);
//            System.out.println("mmmm" + mqttClient.getPendingDeliveryTokens()[0].getMessageId());

            System.out.println("mqttClient.getPendingDeliveryTokens(): " + mqttClient.getPendingDeliveryTokens().length);

            for (IMqttDeliveryToken iMqttDeliveryToken : mqttClient.getPendingDeliveryTokens()) {
                System.out.println("mmm: " + iMqttDeliveryToken.getMessageId());
            }

            mqttClient.disconnect();
            mqttClient.close();

            System.out.println("发布消息完毕，等待");
//            latch.await();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
