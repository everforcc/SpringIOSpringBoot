package cn.cc.sp13websocket.demos.bean;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Description : 获取初始化的bean
 * @Author : GKL
 * @Date: 2024-04-18 17:05
 */
@Component
@Slf4j
@Order(-1)
public class BeanInit {

    private static ApplicationContext applicationContext;

    @Bean
    public CommandLineRunner beanInitPrintCommandLineRunner(ApplicationContext context) {
        applicationContext = context;
        return args -> {
            log.info("spring bean 初始化bean");
        };
    }

    public static RedissonClient getRedissonClient(){
        return applicationContext.getBean(RedissonClient.class);
    }

}
