/**
 * @Description
 * @Author everforcc
 * @Date 2022-08-02 17:55
 * Copyright
 */

package com.cc.sp02thymeleaf.controller;

import com.cc.sp02thymeleaf.service.AsynService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/open")
@Validated
@Slf4j
public class OpenAsynController {

    @Resource
    AsynService asynService;

    @GetMapping("/asyn")
    public void asyn() {
        asynService.asynTests();
    }

    @GetMapping("/asynCallBack")
    public void asynCallBack() {
        asynService.asyncCReturn();
    }
}
