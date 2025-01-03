package cn.cc.redis.redisson.controller;

import cn.cc.redis.redisson.service.ILockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 测试分布式锁
 */
@Slf4j
@RestController
@RequestMapping("/lock")
public class LockController {

    @Autowired
    ILockService iLockService;

    @GetMapping("/busi/{id}")
    public boolean tryLock(@PathVariable int id) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return iLockService.tryLock(uuid, id);
    }

}
