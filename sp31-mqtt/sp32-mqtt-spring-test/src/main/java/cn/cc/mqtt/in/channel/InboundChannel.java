package cn.cc.mqtt.in.channel;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.DirectChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public class InboundChannel {

    @Bean("inboundMessageChannel1")
    public DirectChannelSpec inboundMessageChannel1() {
        return MessageChannels.direct();
    }

    @Bean(name = "CHANNEL_NAME_OUT")
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

//    @Bean("inboundMessageChannel2")
//    public DirectChannelSpec inboundMessageChannel2() {
//        return MessageChannels.direct();
//    }

}
