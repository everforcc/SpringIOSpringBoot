package cn.cc.redis.config;

import cn.cc.redis.configuration.RedisConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
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

    /**
     * 手动配置过程
     * 但是SingleServerConfig也会自动配置
     *
     * @return
     */
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress(redisConfiguration.dealAddress());
        log.debug("当前redis为single： {}", config.useSingleServer().getAddress());
        singleServerConfig.setTimeout(redisConfiguration.getTimeout());
        singleServerConfig.setPassword(redisConfiguration.getPassword());
        log.info("初始化 Redisson : {}", config.toString());
        return Redisson.create(config);
    }

}
