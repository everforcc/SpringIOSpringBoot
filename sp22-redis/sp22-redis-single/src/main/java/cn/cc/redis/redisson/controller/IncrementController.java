package cn.cc.redis.redisson.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自增长的原子
 */
@Slf4j
@RestController
@RequestMapping("/atomic")
public class IncrementController {

    @Autowired
    RedissonClient redissonClient;

    private static String NAME = "atomicLong";

    /**
     * 先取出当前值，返回
     * 然后再加一
     *
     * @return
     */
    @GetMapping("/increment")
    public long increment() {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(NAME);
        long now = atomicLong.get();
        atomicLong.incrementAndGet();
        return now;
    }

    /**
     * 重置为0
     *
     * @return
     */
    @GetMapping("/expire")
    public long expire() {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(NAME);
        atomicLong.set(0);
        // 重置为0
        return atomicLong.get();
    }

}
