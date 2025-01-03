package cn.cc.redis.redisson.service;

public interface ILockService {

    boolean tryLock(String uuid, int busiId);

}
