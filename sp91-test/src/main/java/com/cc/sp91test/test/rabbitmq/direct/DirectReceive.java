package com.cc.sp91test.test.rabbitmq.direct;
import com.cc.sp91test.test.rabbitmq.constant.RabbitMQConstant;
import com.rabbitmq.client.*;


public class DirectReceive {

    private final static String QUEUE_NAME = "xc_queue_name_4";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitMQConstant.host);
        factory.setPort(RabbitMQConstant.port);
        factory.setUsername(RabbitMQConstant.username);
        factory.setPassword(RabbitMQConstant.password);
        factory.setVirtualHost("/");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
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

        /*
         * 消费这条消息
         * 1. 消费哪个队列
         * 2. 消费成功后，自动应答
         * 3. 接收消息后的回调函数
         * 4. 取消消息的回调函数
         */
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }

}
