package cn.cc.mqtt.consumer.controller;

import cn.cc.mqtt.consumer.utils.MQTTClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publish")
public class PublishController {

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

    @GetMapping("/pub/{str}")
    public void pub(@PathVariable String str) {
        getMqttClientUtil().publishAway("/test", str);
    }

}
