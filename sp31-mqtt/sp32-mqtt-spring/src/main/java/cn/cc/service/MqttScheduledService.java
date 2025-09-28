package cn.cc.service;

import cn.cc.config.IMqttSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * MQTT定时任务服务
 * 定时向MQTT topic发送消息
 * 
 * @author cc
 */
@Slf4j
@Service
public class MqttScheduledService {

    @Autowired
    private IMqttSender mqttSender;

    @Value("${mqtt.producer.defaultTopic:/everforcc}")
    private String defaultTopic;

    @Value("${mqtt.scheduled.topic:/scheduled}")
    private String scheduledTopic;

    @Value("${mqtt.scheduled.enabled:true}")
    private boolean scheduledEnabled;

    // 消息计数器
    private final AtomicInteger messageCounter = new AtomicInteger(0);

    /**
     * 定时发送消息 - 每30秒执行一次
     * 发送到默认topic
     * 修改为两分钟跑一次
     */
    @Scheduled(fixedRate = 2 * 60 * 1000)
    public void sendScheduledMessage() {
        if (!scheduledEnabled) {
            return;
        }
        
        try {
            int count = messageCounter.incrementAndGet();
//            String message = String.format("定时消息 #%d - 时间: %s",
//                count,
//                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            Long[] idList = {
                    1971550551432806401L, 1971550551432806403L, 1971550551432806405L, 1971550551432806407L,
                    1971550551432806409L, 1971550551432806411L, 1971550551432806413L, 1971550551432806415L,
                    1971550551432806504L, 1971550551432806417L, 1971550551432806419L, 1971550551432806421L,
                    1971550551432806423L, 1971550551432806425L, 1971550551432806427L, 1971550551432806429L,
                    1971550551432806431L, 1971550551432806433L, 1971550551432806435L, 1971550551432806437L,
                    1971550551432806439L, 1971550551432806441L, 1971550551432806443L, 1971550551432806445L,
                    1971550551432806447L, 1971550551432806402L, 1971550551432806404L, 1971550551432806406L,
                    1971550551432806408L, 1971550551432806410L, 1971550551432806412L, 1971550551432806414L,
                    1971550551432806416L, 1971550551432806505L, 1971550551432806418L, 1971550551432806420L,
                    1971550551432806422L, 1971550551432806424L, 1971550551432806426L, 1971550551432806428L,
                    1971550551432806430L, 1971550551432806432L, 1971550551432806434L, 1971550551432806436L,
                    1971550551432806438L, 1971550551432806440L, 1971550551432806442L, 1971550551432806444L,
                    1971550551432806446L
            };

            JSONObject jsonObject = new JSONObject();
            for(Long id:idList){
                // 1到10的随机数
                int randomNum = (int)(Math.random() * 10) + 1;
                jsonObject.put(id + "", randomNum);
            }
            String message = jsonObject.toString();
            mqttSender.sendToMqtt(defaultTopic, message);
            log.info("定时发送消息到topic [{}]: {}", defaultTopic, message);
        } catch (Exception e) {
            log.error("定时发送消息失败", e);
        }
    }

    /**
     * 定时发送消息 - 每1分钟执行一次
     * 发送到专门的定时任务topic
     */
//    @Scheduled(cron = "0 */1 * * * ?")
    public void sendCronMessage() {
        if (!scheduledEnabled) {
            return;
        }
        
        try {
            int count = messageCounter.incrementAndGet();
            String message = String.format("Cron定时消息 #%d - 时间: %s", 
                count, 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            
            mqttSender.sendToMqtt(scheduledTopic, message);
            log.info("Cron定时发送消息到topic [{}]: {}", scheduledTopic, message);
        } catch (Exception e) {
            log.error("Cron定时发送消息失败", e);
        }
    }

    /**
     * 定时发送系统状态消息 - 每5分钟执行一次
     */
//    @Scheduled(fixedRate = 300000)
    public void sendSystemStatusMessage() {
        if (!scheduledEnabled) {
            return;
        }
        
        try {
            String statusMessage = String.format(
                "系统状态报告 - 时间: %s, 内存使用: %dMB, 已发送消息数: %d",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                getMemoryUsage(),
                messageCounter.get()
            );
            
            mqttSender.sendToMqtt("/system/status", statusMessage);
            log.info("发送系统状态消息: {}", statusMessage);
        } catch (Exception e) {
            log.error("发送系统状态消息失败", e);
        }
    }

    /**
     * 获取内存使用情况
     */
    private long getMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        return usedMemory / 1024 / 1024; // 转换为MB
    }

    /**
     * 手动发送消息方法
     */
    public void sendManualMessage(String topic, String message) {
        try {
            mqttSender.sendToMqtt(topic, message);
            log.info("手动发送消息到topic [{}]: {}", topic, message);
        } catch (Exception e) {
            log.error("手动发送消息失败", e);
        }
    }

    /**
     * 获取消息计数器
     */
    public int getMessageCount() {
        return messageCounter.get();
    }

    /**
     * 重置消息计数器
     */
    public void resetMessageCount() {
        messageCounter.set(0);
        log.info("消息计数器已重置");
    }
}
