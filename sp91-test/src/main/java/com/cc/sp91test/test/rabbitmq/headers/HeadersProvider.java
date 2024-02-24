package com.cc.sp91test.test.rabbitmq.headers;

import com.cc.sp91test.test.rabbitmq.constant.RabbitMQConstant;
import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HeadersProvider {

    private final static String EXCHANGE_NAME = "xc_exchange_headers_name";

    private final static String queueName_1 = "xc_queue_name_headers_1";

    public static void main(String[] args) throws Exception {
        // 1. 创建链接
        ConnectionFactory factory = new ConnectionFactory();
        // 2. 设置连接参数
        factory.setHost(RabbitMQConstant.host);
        factory.setPort(RabbitMQConstant.port);
        factory.setUsername(RabbitMQConstant.username);
        factory.setPassword(RabbitMQConstant.password);
        //factory.setVirtualHost("/");

        // 自动关闭
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            /*
             * 交换机
             * 1. 交换机名称
             * 2. 交换机类型
             * 3. 是否需要持久化
             *    设置为true 会持久化
             * 4. 定义交换机在没有队列绑定时，是否需要删除，设置为false，表示不删除
             * 5. Map<String,Obj>类型，用来指定交换机的其他结构滑参数
             */
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.HEADERS, false, false, null);

            /*
             * 生成队列
             * 1. 队列名称
             * 2. 队列是否需要持久化， 队列名称等元数据的持久化，不是队列中消息的持久化
             * 3. 表示队列私有化， 如果私有，只有创建它的应用程序，才能消费消息
             * 4. 队列在没有消费者订阅的情况下，是否自动删除
             * 5. 队列的一些结构化信息，比如声明为 死信队列，磁盘队列会用到
             */
            channel.queueDeclare(queueName_1, false, false, false, null);

            Map<String,Object> headerMap = new HashMap<>();
            headerMap.put("name","xiaochuanxx");
//            headerMap.put("name","xiaochuan");
            headerMap.put("sex","男");
            AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties.Builder().headers(headerMap);

            /*
             * 1. 发送到哪个交换机
             * 2. 队列名称
             * 3. 其他参数信息
             * 4. 发送消息的消息体
             */
            channel.basicPublish(EXCHANGE_NAME, "", properties.build(), "all message".getBytes(StandardCharsets.UTF_8));
            System.out.println("发送成功");
        }
    }

}
