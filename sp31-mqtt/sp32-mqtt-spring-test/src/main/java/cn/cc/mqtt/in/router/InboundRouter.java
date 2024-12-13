package cn.cc.mqtt.in.router;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.router.HeaderValueRouter;
import org.springframework.stereotype.Component;

import static org.springframework.integration.mqtt.support.MqttHeaders.RECEIVED_TOPIC;

@Component
public class InboundRouter {

    @Bean
    public HeaderValueRouter inboundMessageRouter() {
        HeaderValueRouter router = new HeaderValueRouter(RECEIVED_TOPIC);
        router.setChannelMapping("topic1", "inboundMessageChannel1");
        router.setChannelMapping("topic2", "inboundMessageChannel2");
        return router;
    }

}
