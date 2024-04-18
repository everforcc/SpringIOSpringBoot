package cn.cc.common.redis.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description : redis配置信息
 * @Author : GKL
 * @Date: 2024-04-18 15:10
 */
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfiguration {

    /**
     * 当前redis类型
     * cluster
     * single
     */
    private String type;

    private String host;

    private String port;

    private Cluster cluster;

    private String password;

    private int timeout;

    private int scanInterval;

    private int maxRedirects;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Cluster {
        private String nodes;

        public String[] dealAddress() {
            try {

                String[] cluster = nodes.split(",");
                int length = cluster.length;
                String[] stringList = new String[length];
                for (int i = 0; i < length; i++) {
                    String[] hostAndPort = cluster[i].split(":");
                    stringList[i] = "redis://" + hostAndPort[0] + ":" + hostAndPort[1];
                }
                return stringList;
            } catch (Exception e) {
                log.error("redis配置文件解析异常: {}", e.getMessage());
                throw new RuntimeException("redis配置文件解析异常");
            }
        }

    }

    public String dealAddress() {
        return "redis://" + host + ":" + port;
    }

}
