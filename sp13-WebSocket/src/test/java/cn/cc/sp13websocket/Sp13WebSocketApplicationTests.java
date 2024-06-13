package cn.cc.sp13websocket;

import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Sp13WebSocketApplicationTests {

    @Autowired
    RedissonClient redissonClient;

    @Test
    void contextLoads() {
        RBloomFilter<String> stringRBloomFilter = redissonClient.getBloomFilter("test_bloom_key");
        stringRBloomFilter.tryInit(1000, 0.03);
        for(int i=0;i<1000;i++){
            stringRBloomFilter.add("瓜田李下 " + i);
        }

        System.out.println("瓜田李下 1 是否存在: " + stringRBloomFilter.contains("瓜田李下 " + 1));
        System.out.println("海贼王 是否存在: " + stringRBloomFilter.contains("海贼王"));
        System.out.println("预估插入数量: " + stringRBloomFilter.getExpectedInsertions());
        System.out.println("容错率: " + stringRBloomFilter.getFalseProbability());
        System.out.println("hash函数的个数: " + stringRBloomFilter.getHashIterations());

    }

}
