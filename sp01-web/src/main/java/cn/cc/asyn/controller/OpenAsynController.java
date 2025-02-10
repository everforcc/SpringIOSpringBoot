/**
 * @Description
 * @Author everforcc
 * @Date 2022-08-02 17:55
 * Copyright
 */

package cn.cc.asyn.controller;

import cn.cc.asyn.service.AsynService;
import cn.cc.core.domain.R;
import cn.cc.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;


@RestController
@RequestMapping("/open/async")
@Validated
@Slf4j
public class OpenAsynController {

    @Resource
    AsynService asynService;

    @GetMapping("/task")
    public void asyn() {
        String uuid32 = UUIDUtils.uuid32();
        asynService.asynTests(uuid32);
    }

    @GetMapping("/return")
    public void asynCallBack() {
        asynService.asyncCReturn();
    }

    @GetMapping("/count")
    public R<Integer> threadCount(){
// 获取所有线程的堆栈跟踪
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();

        log.info("Number of active threads:1 {}", allStackTraces.size());

        log.info("Number of active threads:2 {}", allStackTraces.size());
        // 打印线程数量
        System.out.println("Number of active threads: " + allStackTraces.size());

        // 如果需要，可以遍历并打印每个线程的堆栈跟踪
        for (Thread thread : allStackTraces.keySet()) {
            log.info("Thread: {}", thread.getName());
            System.out.println("Thread: " + thread.getName());
//            for (StackTraceElement element : allStackTraces.get(thread)) {
//                System.out.println("\t" + element);
//                log.info(":Thread {}", element);
//            }
        }
        return R.ok(allStackTraces.size());
    }

}
