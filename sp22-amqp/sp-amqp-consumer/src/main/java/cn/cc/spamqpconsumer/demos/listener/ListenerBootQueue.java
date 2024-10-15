package cn.cc.spamqpconsumer.demos.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ListenerBootQueue {

    @RabbitListener(queues = "boot_queue")
    public void listen_message(String message) {
        log.info("模拟信息: {}", message);
    }

}
