package cn.cc.mqtt.out.handler;

import cn.cc.mqtt.config.MqttClientConfig;
import cn.cc.mqtt.config.MqttConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

@Component
public class OutboundHandler {

    @Autowired
    MqttClientConfig mqttClientConfig;

//    @Autowired
//    MqttConfig mqttConfig;

    @Bean
    public MessageHandler outboundMessageHandler() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(MqttConstant.publishId, mqttClientConfig.mqttClientFactory());
        messageHandler.setAsync(true);
        return messageHandler;
    }

}
