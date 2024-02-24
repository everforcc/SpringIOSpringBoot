package com.cc.sp91test.test.rabbitmq.headers;
import com.cc.sp91test.test.rabbitmq.constant.RabbitMQConstant;
import com.rabbitmq.client.*;

import java.util.HashMap;
import java.util.Map;


public class HeadersReceive {

    private final static String EXCHANGE_NAME = "xc_exchange_headers_name";

    private final static String queueName_1 = "xc_queue_name_headers_1";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitMQConstant.host);
        factory.setPort(RabbitMQConstant.port);
        factory.setUsername(RabbitMQConstant.username);
        factory.setPassword(RabbitMQConstant.password);
        factory.setVirtualHost("/");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName_1, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        /*
         * 接收消息后，回调函数
         */
        DeliverCallback deliverCallback = (consumerTag, deliver) -> {
            String message = new String(deliver.getBody(), "UTF-8");
            System.out.println("接收到消息 '" + message + "'");
        };

        /*
         * 取消消息回调函数
         */
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("取消消息: " + consumerTag);
        };

        Map<String,Object> headerMap = new HashMap<>();
        //headerMap.put("x-match","all");
        headerMap.put("x-match","any");
        headerMap.put("name","xiaochuan");
        headerMap.put("sex","男");

        /*
         * 将队列和交换机绑定
         * 1. 队列名称
         * 2. 交换机名称
         * 3. 路由键，在我们直连模式下，可以为我们的队列名称
         */
        channel.queueBind(queueName_1, EXCHANGE_NAME, "", headerMap);

        /*
         * 消费这条消息
         * 1. 消费哪个队列
         * 2. 消费成功后，自动应答
         * 3. 接收消息后的回调函数
         * 4. 取消消息的回调函数
         */
        channel.basicConsume(queueName_1, true, deliverCallback, cancelCallback);
    }

}
