/**
 * @Description
 * @Author everforcc
 * @Date 2022-08-02 17:52
 * Copyright
 */

package com.cc.sp02thymeleaf.asyn;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 测试异步方法
 */
@Slf4j
@Component
public class MyAsyncTask {

    /**
     * 不需要发挥参数
     *
     * @param param 业务参数
     */
    @Async //使用自定义的线程池(执行器)
    public void asyncCpsItemImportTask(String param) {
        log.info("进入异步方法 {}", param);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("进入异步方法---结束");
    }

    @Async //使用自定义的线程池(执行器)
    public Future<String> asyncCReturn(String param) {
        log.info("进入异步 asyncCReturn 方法 {}", param);
        Callable callable = () -> {
            try {
                log.info("进入异步方法 {}", param);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("进入异步方法---结束");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "异步返回值";
        };
        log.info("进入异步 asyncCReturn 方法---结束");

        FutureTask<String> ft1 = new FutureTask<String>(callable);
        new Thread(ft1).start();
        return ft1;
    }

}
