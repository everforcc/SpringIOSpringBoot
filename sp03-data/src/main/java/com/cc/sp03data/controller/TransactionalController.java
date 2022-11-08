/**
 * @Description
 * @Author everforcc
 * @Date 2022-11-08 14:59
 * Copyright
 */

package com.cc.sp03data.controller;

import com.cc.sp03data.service.TransactionalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/transactional")
public class TransactionalController {

    @Resource
    TransactionalService transactionalService;

    @GetMapping("/throw")
    public void tthrow(@RequestParam(value = "id", required = false) String id) {
        try {
            log.info("throw E");
            transactionalService.throwMethod(id);
        } catch (Exception e) {
            log.error("异常: ", e.getMessage(), e);
        }
    }

    @GetMapping("/nothrow")
    public void nothrow(@RequestParam(value = "id", required = false) String id) {
        try {
            log.info("no throw E");
            transactionalService.tryMethod(id);
        } catch (Exception e) {
            log.error("异常: ", e.getMessage(), e);
        }
    }

}
