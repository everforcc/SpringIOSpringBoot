/**
 * @Description
 * @Author everforcc
 * @Date 2022-11-08 14:51
 * Copyright
 */

package com.cc.sp03data.service;

import com.cc.sp03data.dao.TransactionalDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Service
public class TransactionalService {

    @Resource
    TransactionalDao transactionalDao;

    //@Transactional
    @Transactional(rollbackFor = Exception.class)
    public void throwMethod(String id) throws Exception {
        String timeStamp = String.valueOf(System.currentTimeMillis());
        log.info("抛出异常，事务失效: {}", timeStamp);

        transactionalDao.insertNovel(timeStamp);
        transactionalDao.updateNovel(id, timeStamp);
        throw new Exception("throws 主动抛出异常");
    }

    @Transactional(rollbackFor = Exception.class)
    public void tryMethod(String id) {
        log.info("非 持久化 抛出异常，事务不失效");
        String timeStamp = String.valueOf(System.currentTimeMillis());
        log.info("抛出异常，事务失效: {}", timeStamp);

        int result = transactionalDao.insertNovel(timeStamp);
        transactionalDao.updateNovel(id, timeStamp);
        if (1 == result) {
            //throw new Exception("主动抛出异常");
        }
    }

}
