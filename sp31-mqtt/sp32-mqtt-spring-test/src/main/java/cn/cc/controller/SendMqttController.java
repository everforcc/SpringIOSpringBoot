package cn.cc.controller;

import cn.cc.mqtt.config.MqttConstant;
import cn.cc.mqtt.out.gateway.MqttGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class SendMqttController {

//    @Autowired
//    MqttConfig mqttConfig;

    @Resource
    private MqttGateway mqttGateway;

    @RequestMapping("/sendMessage")
    @ResponseBody
    public String sendMessage() {
        MqttConstant.topic.forEach(topic -> {
            String payload1 = "hello, topic 1";
            mqttGateway.sendToMqtt(topic, 0, payload1);
            log.info("send a message, topic: {}, qos: 0, payload: {}", topic, payload1);
        });
        return "success";
    }

}
