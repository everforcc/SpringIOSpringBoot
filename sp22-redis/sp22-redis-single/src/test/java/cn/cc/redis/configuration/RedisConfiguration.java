package cn.cc.redis.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * RedisProperties 有同样配置
 *
 * @Description : redis配置信息
 * @Author : GKL
 * @Date: 2024-04-18 15:10
 */
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Component
//@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfiguration {

    private String host;

    private String port;

    private String password;

    private int timeout;

    private int scanInterval;

    private int maxRedirects;

    public String dealAddress() {
        return "redis://" + host + ":" + port;
    }

}
