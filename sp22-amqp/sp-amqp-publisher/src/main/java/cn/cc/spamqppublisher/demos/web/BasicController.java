/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.cc.spamqppublisher.demos.web;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/test")
    public void test() {
        String queueName = "simple.queue";
        String message = "hello, spring amqp!";
        rabbitTemplate.convertAndSend(queueName, message);
    }

    /**
     * 1. 消息只能消费一次
     * 2. 消息平均分配至消费者
     */
    @GetMapping("/work")
    public void work() throws InterruptedException {
        String queueName = "work.queue";
        for (int i = 1; i < 51; i++) {
            String message = "hello, spring amqp-" + i;
            rabbitTemplate.convertAndSend(queueName, message);
            Thread.sleep(20);
        }
    }

    /**
     * 1. 发送到 fanout 交换机
     */
    @GetMapping("/fanout")
    public void fanout() throws InterruptedException {
        String exchange = "cc.fanout";
        String message = "hello, spring amqp!";
        rabbitTemplate.convertAndSend(exchange, null, message);
    }

    /**
     * 发送到 fanout 交换机
     */
    @GetMapping("/direct/{type}")
    public void direct(@PathVariable String type) throws InterruptedException {
        String exchange = "cc.direct";
        String message = type + "-警告";
        rabbitTemplate.convertAndSend(exchange, type, message);
    }

    /**
     * 发送到 fanout 交换机
     */
    @GetMapping("/topic/news")
    public void topicNews() throws InterruptedException {
        String exchange = "cc.topic";
        String message = "岛国新闻";
        rabbitTemplate.convertAndSend(exchange, "china.news", message);
    }

    @GetMapping("/topic/weather")
    public void topicWeather() throws InterruptedException {
        String exchange = "cc.topic";
        String message = "中国天气";
        rabbitTemplate.convertAndSend(exchange, "china.weather", message);
    }

}
