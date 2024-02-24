package com.cc.sp91test.test.rabbitmq.fanout;

import com.cc.sp91test.test.rabbitmq.constant.RabbitMQConstant;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class FanoutProvider {

    private final static String EXCHANGE_NAME = "xc_exchange_fanout_name";

    private final static String queueName_1 = "xc_queue_name_fanout_1";
    private final static String queueName_2 = "xc_queue_name_fanout_2";
    private final static String queueName_3 = "xc_queue_name_fanout_3";
    private final static String queueName_4 = "xc_queue_name_fanout_4";

    private final static String key_1 = "key_1";
    private final static String key_3 = "key_3";
    private final static String key_4 = "key_4";

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
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT, false, false, null);

            /*
             * 生成队列
             * 1. 队列名称
             * 2. 队列是否需要持久化， 队列名称等元数据的持久化，不是队列中消息的持久化
             * 3. 表示队列私有化， 如果私有，只有创建它的应用程序，才能消费消息
             * 4. 队列在没有消费者订阅的情况下，是否自动删除
             * 5. 队列的一些结构化信息，比如声明为 死信队列，磁盘队列会用到
             */
            channel.queueDeclare(queueName_1, false, false, false, null);
            channel.queueDeclare(queueName_2, false, false, false, null);
            channel.queueDeclare(queueName_3, false, false, false, null);
            channel.queueDeclare(queueName_4, false, false, false, null);

            /*
             * 将队列和交换机绑定
             * 1. 队列名称
             * 2. 交换机名称
             * 3. 路由键，在我们直连模式下，可以为我们的队列名称
             */
            channel.queueBind(queueName_1, EXCHANGE_NAME, key_1);
            channel.queueBind(queueName_2, EXCHANGE_NAME, key_1);
            channel.queueBind(queueName_3, EXCHANGE_NAME, key_3);
            channel.queueBind(queueName_4, EXCHANGE_NAME, key_4);

            /*
             * 1. 发送到哪个交换机
             * 2. 队列名称
             * 3. 其他参数信息
             * 4. 发送消息的消息体
             */
            channel.basicPublish(EXCHANGE_NAME, key_1, null, "key_1 fanout message".getBytes(StandardCharsets.UTF_8));
        }
    }

}
