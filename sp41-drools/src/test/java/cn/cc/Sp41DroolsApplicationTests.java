package cn.cc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class Sp41DroolsApplicationTests {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void contextLoads() {
        redisTemplate.keys("*");
    }

}
