package cn.cc.mqtt.in.activator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

import static org.springframework.integration.mqtt.support.MqttHeaders.RECEIVED_QOS;
import static org.springframework.integration.mqtt.support.MqttHeaders.RECEIVED_TOPIC;

@Slf4j
@Component
public class InboundActivator {

    @Bean
    @ServiceActivator(inputChannel = "inboundMessageChannel1")
    public MessageHandler inboundMessageHandler1() {
        return message -> log.info("received a message, topic: {}, qos: {}, payload: {}", message.getHeaders().get(RECEIVED_TOPIC), message.getHeaders().get(RECEIVED_QOS),  message.getPayload());
    }

//    @Bean
//    @ServiceActivator(inputChannel = "inboundMessageChannel2")
//    public MessageHandler inboundMessageHandler2() {
//        return message -> log.info("received a message, topic: {}, qos: {}, payload: {}", message.getHeaders().get(RECEIVED_TOPIC), message.getHeaders().get(RECEIVED_QOS),  message.getPayload());
//    }

}
