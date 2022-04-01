package com.cc.sp10aop.business.flow.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FlowService {

    public String testFlow(){
        log.info("3. 进入testFlow()");
        //throw new RuntimeException("自定义异常");
        return "abc";
    }

}
