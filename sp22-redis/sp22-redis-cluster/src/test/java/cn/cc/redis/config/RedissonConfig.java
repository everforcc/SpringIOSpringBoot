package cn.cc.redis.config;

import cn.cc.redis.configuration.RedisConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RedissonConfig {

    @Autowired
    RedisConfiguration redisConfiguration;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        ClusterServersConfig clusterServersConfig = config.useClusterServers();

        clusterServersConfig.addNodeAddress(redisConfiguration.getCluster().dealAddress());
        log.debug("当前redis为cluster： {}", clusterServersConfig.getNodeAddresses());
        clusterServersConfig.setScanInterval(redisConfiguration.getScanInterval());
        clusterServersConfig.setPassword(redisConfiguration.getPassword());
        clusterServersConfig.setTimeout(redisConfiguration.getTimeout());

        log.info("初始化 Redisson : {}", config.toString());
        return Redisson.create(config);
    }

}
