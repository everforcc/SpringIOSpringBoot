package cn.cc.redis.redisson.service.impl;

import cn.cc.redis.redisson.service.ILockService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LockServiceImpl implements ILockService {

    @Resource
    private RedissonClient redissonClient;

    private static int stockNum = 0;

    @Override
    public boolean tryLock(String uuid, int busiId) {
        Date lockstartDate = new Date();
        RLock lock = redissonClient.getLock("busi-" + busiId);
        boolean flag = false;
        try {
            log.info("{}: 尝试获取锁", uuid);
            flag = lock.tryLock(1, 1, TimeUnit.SECONDS);
            Date lockandDate = new Date();
            log.info("{}: 获取lock耗时: {}", uuid, (lockandDate.getTime() - lockstartDate.getTime()));
            log.info("{}: 是否获取到锁: {} ", uuid, flag);
            if (flag) {
                final RBucket<Integer> intRBucket = redissonClient.getBucket("lock-" + busiId);
                if (intRBucket.isExists()) {
                    stockNum = intRBucket.get();
                    stockNum++;
                    intRBucket.set(stockNum);
                    log.info("{}: busiId： {}, stockNum: {}", uuid, busiId, stockNum);
                } else {
                    intRBucket.set(stockNum);
                }
                Thread.sleep(2000);
            } else {
                log.info("{}: 没有获取到锁: {} ", uuid, flag);
            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /**
             * 1. 8500ms 的情况下全部都是自动释放锁
             * 2. 用的非本地erdis，延迟高
             */
            //if(false&&lock.isLocked()){
            log.info("{}: 解锁前校验 flag: {} isLocked: {} isHeldByCurrentThread: {}", uuid, flag, lock.isLocked(), lock.isHeldByCurrentThread());
            if (flag && lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.info("{}: 释放锁: flag: {} ", uuid, flag);
            } else {
                log.info("{}: 自动解锁", uuid);
            }
        }
        return flag;
    }

}
