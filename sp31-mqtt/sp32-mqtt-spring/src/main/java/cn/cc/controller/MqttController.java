package cn.cc.controller;

import cn.cc.config.IMqttSender;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * MQTT消息发送
 *
 * @author BBF
 */
@Controller
@RequestMapping(value = "/")
public class MqttController {

    /**
     * 注入发送MQTT的Bean
     */
    @Resource
    private IMqttSender iMqttSender;

    /**
     * @param message 消息内容
     * @return 返回
     * @see <a href="https://segmentfault.com/a/1190000017811919">原文地址</a>
     * 发送MQTT消息
     */
    @ResponseBody
    @GetMapping(value = "/mqtt", produces = "text/html")
    public ResponseEntity<String> sendMqtt(@RequestParam(value = "msg") String message) {
        iMqttSender.sendToMqtt(message);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
