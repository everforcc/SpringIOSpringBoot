package cn.cc.mqtt.config;

import java.util.Arrays;
import java.util.List;

public class MqttConstant {

    public static final String url = "tcp://8.146.199.165:1883";
    public static final String username = "c.c.";
    public static final String password = "rootcccc";
    /**
     * the number of maxInfligt messages
     * maxflight消息的数量
     * 设置“最大飞行”。请在高流量*环境中增加此值。
     */
    public static final Integer maxInflight = 5000;
    /**
     * Set to True to enable cleanSession
     * 设置为True以启用清理
     */
    public static final Boolean cleanSession = true;
    /**
     * If set to True, Automatic Reconnect will be enabled
     * 如果设置为True，将启用自动重新连接
     */
    public static final Boolean automaticReconnect = true;
    public static final String clientId = "clientId8031";
    public static final String publishId = "publishId8031";
    public static final List<String> topic = Arrays.asList("topic1", "topic2");
    public static final Integer completionTimeout = 10;

}
