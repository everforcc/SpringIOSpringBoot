package cn.cc.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.time.Duration;

@Slf4j
@SpringBootTest
public class RedisTemplateTests {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void setIfAbsent() {
        for(int i = 0; i<10 ; i++) {
            Boolean flag = redisTemplate.opsForValue().setIfAbsent("id:", "1", Duration.ofSeconds(60));
            System.out.println(flag);
            if(Boolean.TRUE.equals(flag)){
                System.out.println("可以执行业务逻辑");
            }
        }
    }


}
