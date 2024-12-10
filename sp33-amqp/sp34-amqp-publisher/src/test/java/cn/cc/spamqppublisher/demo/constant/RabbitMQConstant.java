package cn.cc.spamqppublisher.demo.constant;

public class RabbitMQConstant {

    public static String host = "8.146.199.165";

    /**
     * com.rabbitmq.client.impl.ForgivingExceptionHandler - An unexpected connection driver error occured
     * 5672: 	RabbitMQ的通讯端口
     * 25672:	RabbitMQ的节点间的CLI通讯端口是
     * 15672:	RabbitMQ HTTP_API的端口，管理员用户才能访问，用于管理RabbitMQ,需要启动Management插件。
     * 1883，8883：	MQTT插件启动时的端口。
     * 61613、61614：	STOMP客户端插件启用的时候的端口。
     * 15674、15675：	基于webscoket的STOMP端口和MOTT端口
     */
    public static int port = 5672;

    public static String username = "c.c.";
    public static String password = "c.c.c.c.";
}
