package cn.cc.spamqppublisher.webdemo;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProducerTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void testSendMessage() {
        rabbitTemplate.convertAndSend("boot_topic_exchange","message","双十一快开始了222");
    }

}
