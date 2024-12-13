package cn.cc.mqtt.out.gateway;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import static org.springframework.integration.mqtt.support.MqttHeaders.QOS;
import static org.springframework.integration.mqtt.support.MqttHeaders.TOPIC;

@MessagingGateway(defaultRequestChannel = "outboundMessageChannel")
public interface MqttGateway {
    void sendToMqtt(@Header(TOPIC) String topic, @Header(QOS) int qos, @Payload String payload);
}