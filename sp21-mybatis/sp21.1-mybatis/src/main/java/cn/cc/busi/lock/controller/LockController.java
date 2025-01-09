package cn.cc.busi.lock.controller;

import cn.cc.busi.lock.service.LockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/lock")
public class LockController {

    @Resource
    LockService lockService;

    @GetMapping("/id/{id}")
    public void updateIncrease(@PathVariable("id") int id){
        lockService.updateIncrease(id);
    }

}
