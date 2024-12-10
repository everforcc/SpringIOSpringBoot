package cn.cc.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class RBloomFilterTests {

    @Autowired
    RedissonClient redissonClient;

    @Test
    void contextLoads() {
        int database = redissonClient.getConfig().useSingleServer().getDatabase();
        log.info("测试测试配置文件是否生效: {}", database);
        log.info("测试布隆过滤器");
        RBloomFilter<String> stringRBloomFilter = redissonClient.getBloomFilter("test_bloom_key");
        stringRBloomFilter.tryInit(50, 0.03);
        for (int i = 0; i < 50; i++) {
            log.info("第{}次", i);
            stringRBloomFilter.add("瓜田李下 " + i);
        }
        log.info("瓜田李下 1 是否存在: {}", stringRBloomFilter.contains("瓜田李下 " + 1));
        log.info("海贼王 是否存在: {}", stringRBloomFilter.contains("海贼王"));
        log.info("预估插入数量: {}", stringRBloomFilter.getExpectedInsertions());
        log.info("容错率: {}", stringRBloomFilter.getFalseProbability());
        log.info("hash函数的个数: {}", stringRBloomFilter.getHashIterations());
    }

}
