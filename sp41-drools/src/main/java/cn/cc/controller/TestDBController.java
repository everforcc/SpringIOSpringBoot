package cn.cc.controller;

import cn.cc.service.IDBRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description : 测试drools
 * @Author : GKL
 * @Date: 2024-04-29 10:01
 */
@Slf4j
@RestController
@RequestMapping("/testdb")
public class TestDBController {

    @Resource
    IDBRedisService idbRedisService;

    /**
     * 测试drools
     */
    @GetMapping("/result/{id}")
    public void result(@PathVariable int id) {
        String result = idbRedisService.selectSysUser(id);
        log.info("result: {}", result);
    }

}
