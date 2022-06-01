package com.cc.sp10aop.business.flow.controller;

import com.cc.sp10aop.business.flow.service.IFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aop/flow")
public class FlowController {

    @Autowired
    IFlowService flowService;

    @GetMapping("/f")
    public String flow(){
        return flowService.testFlow();
    }

    @GetMapping("/save")
    public String save(){
        return flowService.save("", "");
    }

    @GetMapping("/savea")
    public String savea(){
        return flowService.save_a("", "");
    }
}
