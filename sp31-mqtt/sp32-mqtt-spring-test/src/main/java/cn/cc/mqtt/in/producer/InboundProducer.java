package cn.cc.mqtt.in.producer;

import cn.cc.mqtt.config.MqttClientConfig;
import cn.cc.mqtt.config.MqttConstant;
import cn.cc.mqtt.in.channel.InboundChannel;
import cn.cc.mqtt.out.channel.OutboundChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.stereotype.Component;

@Component
public class InboundProducer {

//    @Autowired
//    MqttConfig mqttConfig;

    @Autowired
    MqttClientConfig mqttClientConfig;

    @Autowired
    OutboundChannel outboundChannel;

    @Bean
    public MessageProducerSupport inboundMessageProducer() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(MqttConstant.clientId, mqttClientConfig.mqttClientFactory());
        adapter.addTopics(MqttConstant.topic.toArray(new String[0]), new int[]{0, 0});
        adapter.setCompletionTimeout(MqttConstant.completionTimeout);
        adapter.setConverter(new DefaultPahoMessageConverter());
        // adapter.setOutputChannel();
        return adapter;
    }

}
