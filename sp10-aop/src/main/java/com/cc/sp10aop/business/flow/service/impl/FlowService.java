package com.cc.sp10aop.business.flow.service.impl;

import com.cc.sp10aop.business.flow.service.IFlowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FlowService implements IFlowService {

    @Override
    public String testFlow(){
        log.info("3. 进入testFlow()");
        //throw new RuntimeException("自定义异常");
        return "abc";
    }

    @Override
    public String save(String a, String b){
        log.info("3. 进入testFlow()");
        //throw new RuntimeException("自定义异常");
        return "abc";
    }

    @Override
    public String save_a(String a, String b){
        log.info("3. 进入testFlow()");
        //throw new RuntimeException("自定义异常");
        return "abc";
    }

}
