package cn.cc.sp13websocket.demos.schedule;

import cn.cc.sp13websocket.demos.utils.MessageMap;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.util.Date;
import java.util.Map;

/**
 * @Description
 * @Author everforcc
 * @Date 2024-04-17 21:54
 * Copyright
 */
@Slf4j
@Component
public class DemoSchedule {

    @Autowired
    RedissonClient redissonClient;

    @Value("${server.port}")
    String port;

    @Scheduled(cron = "0 * * * * ?")
    public void demoBloom() {
        log.info("测试布隆过滤器");
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


    @Scheduled(cron = "0/25 * * * * ?")
    public void demoMethod() {
        log.info("redissonClient.getRemoteService().toString(): {}", redissonClient.getRemoteService().toString());
        log.info("new Date().toString(): {}", new Date().toString());

//        Map<String, Session> onlineSessionClientMap = MessageMap.getSessionMap();
//        log.info("onlineSessionClientMap.size(): {}", onlineSessionClientMap.size());

        final RBucket<Map<String, Session>> imapRBucket = redissonClient.getBucket(MessageMap.BUSI_TEST);
        if (imapRBucket.isExists()) {
            Map<String, Session> onlineSessionClientMap = imapRBucket.get();
            onlineSessionClientMap.forEach((onlineSid, toSession) -> {
                String message = onlineSid + " :当前时间是: " + new Date().toString();
                log.info("服务端: {} 给客户端群发消息 ==> sid = {}, toSid = {}, message = {}", port, "schedule", onlineSid, message);
                toSession.getAsyncRemote().sendText(message);
            });
        }


    }

}
