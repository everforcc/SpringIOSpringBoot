package cn.cc.mqtt.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.stereotype.Component;

@Order(-1)
@Component
public class MqttClientConfig {

//    @Autowired
//    MqttConfig mqttConfig;

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{ MqttConstant.url });
        options.setUserName(MqttConstant.username);
        options.setPassword(MqttConstant.password.toCharArray());
        options.setMaxInflight(MqttConstant.maxInflight);
        options.setCleanSession(MqttConstant.cleanSession);
        options.setAutomaticReconnect(MqttConstant.automaticReconnect);
        factory.setConnectionOptions(options);
        return factory;
    }

}
