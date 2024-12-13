//package cn.cc.mqtt.config;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//
//import java.util.List;
//@Order(-2)
//@Getter
//@Setter
//@Configuration
//@ConfigurationProperties(prefix = "spring.mqtt")
//public class MqttConfig {
//
//    private String url;
//
//    private String username;
//
//
//    private String password;
//
//    /**
//     * the number of maxInfligt messages
//     * maxflight消息的数量
//     * 设置“最大飞行”。请在高流量*环境中增加此值。
//     */
//    private Integer maxInflight;
//
//    /**
//     * Set to True to enable cleanSession
//     * 设置为True以启用清理
//     */
//    private Boolean cleanSession;
//
//    /**
//     * If set to True, Automatic Reconnect will be enabled
//     * 如果设置为True，将启用自动重新连接
//     */
//    private Boolean automaticReconnect;
//
//    private String clientId;
//
//    private String publishId;
//
//    private List<String> topic;
//
//    private Integer completionTimeout;
//
//}