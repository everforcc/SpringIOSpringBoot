/**
 * @Description
 * @Author everforcc
 * @Date 2022-08-02 17:51
 * Copyright
 */

package cn.cc.asyn.service;

import cn.cc.asyn.config.MyAsyncTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
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

    public void asynTests(String uuid) {
        log.info("测试asyn开始： {}", uuid);
        myAsyncTask.asyncCpsItemImportTask(uuid);
        this.thisAsync(uuid);
        log.info("测试asyn结束： {}", uuid);
    }

    public void asyncCReturn() {
        log.info("测试 asyncCReturn 开始");
        // More than one TaskExecutor bean found within the context, and none is named 'taskExecutor'.
        // Mark one of them as primary or name it 'taskExecutor' (possibly as an alias) in order to use it for async processing:
        // [AsyncTaskExecutor1, AsyncTaskExecutor2]
        Future<String> stringFuture = myAsyncTask.asyncCReturn("param");
        try {
            String result = stringFuture.get();
            log.info("result: {}", result);
        } catch (InterruptedException e) {
            log.error("报错InterruptedException: {}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            log.error("报错ExecutionException: {}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        log.info("测试 asyncCReturn 结束");
    }

    @Async("AsyncTaskExecutor2")
    public void thisAsync(String uuid){
        log.info("测试本地方法，不能执行异步: {}", uuid);
    }

}
