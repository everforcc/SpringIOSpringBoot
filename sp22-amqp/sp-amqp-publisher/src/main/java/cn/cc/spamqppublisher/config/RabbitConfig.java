package cn.cc.spamqppublisher.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    private final String EXCHANGE_NAME = "boot_topic_exchange";

    private final String QUEUE_NAME = "boot_queue";

    /**
     * @return 创建交换机
     */
    @Bean("bootExchange")
    public Exchange getExchange() {
        return ExchangeBuilder
                .topicExchange(EXCHANGE_NAME) // 交换机类型
                .durable(true) // 是否持久化
                .build();
    }

    /**
     * @return 创建队列
     */
    @Bean("bootQueue")
    public Queue getMessageQueue() {
        return new Queue(QUEUE_NAME); // 队列名
    }

    @Bean
    public Binding bindMessageQueue(@Qualifier("bootExchange") Exchange exchange, @Qualifier("bootQueue") Queue queue) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("#.message.#")
                .noargs();
    }

}
