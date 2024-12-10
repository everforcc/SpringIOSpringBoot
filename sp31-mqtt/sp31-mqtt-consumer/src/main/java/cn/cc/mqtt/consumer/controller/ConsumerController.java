package cn.cc.mqtt.consumer.controller;

import cn.cc.mqtt.consumer.utils.MQTTClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@Slf4j
@Component
//@RestController
//@RequestMapping("/consumer")
public class ConsumerController {

    @Value("${spring.mqtt.host-url}")
    private String brokerUrl;
    @Value("${spring.mqtt.username}")
    private String username;
    @Value("${spring.mqtt.password}")
    private String password;

    MQTTClientUtil mqttClientUtil;

    public MQTTClientUtil getMqttClientUtil() {
        if (mqttClientUtil != null) {
            return mqttClientUtil;
        }
        mqttClientUtil = MQTTClientUtil.create(brokerUrl, username, password);
        return mqttClientUtil;
    }

    @PostConstruct
    public void mqConsumer() {
        Thread thread1 = new Thread(() -> {
            getMqttClientUtil().subscribe("/test", (IMqttMessageListener) (topic, message) -> {
                String s = new String(message.getPayload());
                log.info("响应： {}", s);
            });
        });
        thread1.start();
        log.info("开始接受mqtt响应");
    }

}
