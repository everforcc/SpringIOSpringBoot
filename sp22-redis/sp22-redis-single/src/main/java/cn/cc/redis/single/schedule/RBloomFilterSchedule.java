package cn.cc.redis.single.schedule;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RBloomFilterSchedule {

    @Autowired
    RedissonClient redissonClient;

    @Scheduled(cron = "0 * * * * ?")
    public void demoBloom() {
        log.info("测试布隆过滤器");
        RBloomFilter<String> stringRBloomFilter = redissonClient.getBloomFilter("test_bloom_key");
        stringRBloomFilter.tryInit(1000, 0.03);
        for (int i = 0; i < 1000; i++) {
            stringRBloomFilter.add("瓜田李下 " + i);
        }
        log.info("瓜田李下 1 是否存在: {}", stringRBloomFilter.contains("瓜田李下 " + 1));
        log.info("海贼王 是否存在: {}", stringRBloomFilter.contains("海贼王"));
        log.info("预估插入数量: {}", stringRBloomFilter.getExpectedInsertions());
        log.info("容错率: {}", stringRBloomFilter.getFalseProbability());
        log.info("hash函数的个数: {}", stringRBloomFilter.getHashIterations());
    }

}
