package cn.cc.mqtt.out.channel;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.DirectChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.stereotype.Component;

@Component
public class OutboundChannel {

    @Bean("outboundMessageChannel")
    public DirectChannelSpec outboundMessageChannel() {
        return MessageChannels.direct();
    }

}
