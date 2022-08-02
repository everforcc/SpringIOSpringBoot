/**
 * @Description
 * @Author everforcc
 * @Date 2022-08-02 17:51
 * Copyright
 */

package com.cc.sp02thymeleaf.service;

import com.cc.sp02thymeleaf.asyn.MyAsyncTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 测试异步方法
 */
@Slf4j
@Service
public class AsynService {

    @Resource
    MyAsyncTask myAsyncTask;

    public void asynTests() {
        log.info("测试asyn开始");
        myAsyncTask.asyncCpsItemImportTask("param");
        log.info("测试asyn结束");
    }

    public void asyncCReturn() {
        log.info("测试 asyncCReturn 开始");
        Future<String> stringFuture = myAsyncTask.asyncCReturn("param");
        try {
            String result = stringFuture.get();
            log.info("result: {}", result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        log.info("测试 asyncCReturn 结束");
    }

}
