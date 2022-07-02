package com.cc.sp10aop.business.taop.controller;

import com.cc.sp10aop.business.taop.service.SaveService;
import com.cc.sp10aop.common.dto.CommonFiledDto;
import com.cc.sp10aop.userinterface.LimitRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AOPController {

    @Autowired
    SaveService saveService;

    //@LimitRequest(count = 2)
    @LimitRequest
    @GetMapping("/")
    public String aspect(){
        return "未被拦截";
    }



    @GetMapping("/save")
    public String save(){
        log.info("测试是否全局唯一");
        log.info("X-B3-TraceId: " + MDC.get("X-B3-TraceId"));

        CommonFiledDto commonFiledDto =new CommonFiledDto(10086);
        return saveService.save(commonFiledDto);
    }

    @PostMapping("/post")
    public String post(@RequestBody String json){
        log.info("测试是否全局唯一 【{}】", json);
        log.info("X-B3-TraceId: " + MDC.get("X-B3-TraceId"));

        CommonFiledDto commonFiledDto =new CommonFiledDto(10086);
        return saveService.save(commonFiledDto);
    }

    @PostMapping("/postObj")
    public String postObj(@RequestBody CommonFiledDto commonFiledDto){
        log.info("测试是否全局唯一 【{}】", commonFiledDto.toString());
        log.info("X-B3-TraceId: " + MDC.get("X-B3-TraceId"));

        return saveService.save(commonFiledDto);
    }

}
