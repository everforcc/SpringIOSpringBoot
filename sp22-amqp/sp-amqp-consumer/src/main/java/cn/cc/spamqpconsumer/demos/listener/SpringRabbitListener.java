package cn.cc.spamqpconsumer.demos.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SpringRabbitListener {

    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueueMessage(String msg) {
        log.info("spring 消费者接收到消息: {}", msg);
        log.info("消息处理完成");
    }

    @RabbitListener(queues = "work.queue")
    public void listenWorQueue1(String msg) throws InterruptedException {
        log.info("spring 消费者1 接收到消息: {}", msg);
        log.info("消息处理完成");
        Thread.sleep(20);
    }

    @RabbitListener(queues = "work.queue")
    public void listenWorQueue2(String msg) throws InterruptedException {
        log.info("spring 消费者2 接收到消息: {}", msg);
        log.info("消息处理完成");
        Thread.sleep(200);
    }

    @RabbitListener(queues = "fanout.queue1")
    public void listenFanoutQueue1(String msg) throws InterruptedException {
        log.info("fanout 消费者1 接收到消息: {}", msg);
        log.info("消息处理完成");
        Thread.sleep(20);
    }

    @RabbitListener(queues = "fanout.queue2")
    public void listenFanoutQueue2(String msg) throws InterruptedException {
        log.info("fanout 消费者2 接收到消息: {}", msg);
        log.info("消息处理完成");
        Thread.sleep(200);
    }

    @RabbitListener(queues = "direct.queue1")
    public void listenDirectQueue1(String msg) throws InterruptedException {
        log.info("direct 消费者1 接收到消息: {}", msg);
        log.info("消息处理完成");
        Thread.sleep(20);
    }

    @RabbitListener(queues = "direct.queue2")
    public void listenDirectQueue2(String msg) throws InterruptedException {
        log.info("direct 消费者2 接收到消息: {}", msg);
        log.info("消息处理完成");
        Thread.sleep(200);
    }

    @RabbitListener(queues = "topic.queue1")
    public void listenTopicQueue1(String msg) throws InterruptedException {
        log.info("topic 消费者1 接收到消息: {}", msg);
        log.info("消息处理完成");
        Thread.sleep(20);
    }

    @RabbitListener(queues = "topic.queue2")
    public void listenTopicQueue2(String msg) throws InterruptedException {
        log.info("topic 消费者2 接收到消息: {}", msg);
        log.info("消息处理完成");
        Thread.sleep(200);
    }

}
