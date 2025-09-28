package cn.cc.controller;

import cn.cc.service.MqttScheduledService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * MQTT定时任务控制器
 * 提供手动触发和状态查询接口
 * 
 * @author cc
 */
@Slf4j
@RestController
@RequestMapping("/api/mqtt/scheduled")
public class MqttScheduledController {

    @Autowired
    private MqttScheduledService mqttScheduledService;

    /**
     * 手动发送消息
     * 
     * @param topic MQTT主题
     * @param message 消息内容
     * @return 发送结果
     */
    @PostMapping("/send")
    public Map<String, Object> sendMessage(@RequestParam String topic, 
                                         @RequestParam String message) {
        Map<String, Object> result = new HashMap<>();
        try {
            mqttScheduledService.sendManualMessage(topic, message);
            result.put("success", true);
            result.put("message", "消息发送成功");
            result.put("topic", topic);
            result.put("content", message);
            result.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "消息发送失败: " + e.getMessage());
            log.error("手动发送消息失败", e);
        }
        return result;
    }

    /**
     * 发送默认消息
     * 
     * @param message 消息内容
     * @return 发送结果
     */
    @PostMapping("/send/default")
    public Map<String, Object> sendDefaultMessage(@RequestParam String message) {
        Map<String, Object> result = new HashMap<>();
        try {
            mqttScheduledService.sendManualMessage("/everforcc", message);
            result.put("success", true);
            result.put("message", "默认消息发送成功");
            result.put("topic", "/everforcc");
            result.put("content", message);
            result.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "默认消息发送失败: " + e.getMessage());
            log.error("发送默认消息失败", e);
        }
        return result;
    }

    /**
     * 获取定时任务状态
     * 
     * @return 状态信息
     */
    @GetMapping("/status")
    public Map<String, Object> getStatus() {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("success", true);
            result.put("messageCount", mqttScheduledService.getMessageCount());
            result.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            result.put("status", "定时任务运行正常");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取状态失败: " + e.getMessage());
            log.error("获取定时任务状态失败", e);
        }
        return result;
    }

    /**
     * 重置消息计数器
     * 
     * @return 重置结果
     */
    @PostMapping("/reset")
    public Map<String, Object> resetCounter() {
        Map<String, Object> result = new HashMap<>();
        try {
            mqttScheduledService.resetMessageCount();
            result.put("success", true);
            result.put("message", "消息计数器已重置");
            result.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "重置失败: " + e.getMessage());
            log.error("重置消息计数器失败", e);
        }
        return result;
    }

    /**
     * 发送测试消息
     * 
     * @return 发送结果
     */
    @PostMapping("/test")
    public Map<String, Object> sendTestMessage() {
        Map<String, Object> result = new HashMap<>();
        try {
            String testMessage = "测试消息 - " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            mqttScheduledService.sendManualMessage("/test", testMessage);
            result.put("success", true);
            result.put("message", "测试消息发送成功");
            result.put("topic", "/test");
            result.put("content", testMessage);
            result.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "测试消息发送失败: " + e.getMessage());
            log.error("发送测试消息失败", e);
        }
        return result;
    }
}
