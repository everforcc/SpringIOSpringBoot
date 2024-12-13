package cn.cc.mqtt.out.adapter;

import cn.cc.mqtt.out.channel.OutboundChannel;
import cn.cc.mqtt.out.handler.OutboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.stereotype.Component;

@Component
public class OutboundAdapter {

    @Autowired
    OutboundHandler outboundHandler;

    @Autowired
    OutboundChannel outboundChannel;

    @Bean
    public IntegrationFlow outboundIntegrationFlow() {
        return IntegrationFlows.from(outboundChannel.outboundMessageChannel())
                .handle(outboundHandler.outboundMessageHandler())
                .get();
    }

}
