package cn.cc.mqtt.in.adapter;

import cn.cc.mqtt.in.producer.InboundProducer;
import cn.cc.mqtt.in.router.InboundRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.stereotype.Component;

@Component
public class InboundAdapter {

    @Autowired
    InboundProducer inboundProducer;

    @Autowired
    InboundRouter inboundRouter;

    @Bean
    public IntegrationFlow inboundIntegrationFlow() {
        return IntegrationFlows.from(inboundProducer.inboundMessageProducer())
                .route(inboundRouter.inboundMessageRouter())
                .get();
    }

}
