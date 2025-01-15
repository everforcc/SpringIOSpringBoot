/**
 * @Description
 * @Author everforcc
 * @Date 2022-08-02 17:55
 * Copyright
 */

package cn.cc.asyn.controller;

import cn.cc.asyn.service.AsynService;
import cn.cc.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


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
}
