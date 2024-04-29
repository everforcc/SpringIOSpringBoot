package cn.cc.common.redis.config;

import cn.cc.common.redis.configuration.RedisConfiguration;
import cn.cc.common.redis.constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
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
        if (RedisConstant.cluster.equals(redisConfiguration.getType())) {
            clusterServersConfig.addNodeAddress(redisConfiguration.getCluster().dealAddress());
            log.debug("当前redis为cluster： {}", clusterServersConfig.getNodeAddresses());
            clusterServersConfig.setScanInterval(redisConfiguration.getScanInterval());
            clusterServersConfig.setPassword(redisConfiguration.getPassword());
            clusterServersConfig.setTimeout(redisConfiguration.getTimeout());
        } else if (RedisConstant.single.equals(redisConfiguration.getType())) {
            SingleServerConfig singleServerConfig = config.useSingleServer();
            singleServerConfig.setAddress(redisConfiguration.dealAddress());
            log.debug("当前redis为single： {}", config.useSingleServer().getAddress());
            singleServerConfig.setTimeout(redisConfiguration.getTimeout());
            singleServerConfig.setPassword(redisConfiguration.getPassword());
        }
        log.info("初始化 Redisson : {}", config.toString());
        return Redisson.create(config);
    }

}
