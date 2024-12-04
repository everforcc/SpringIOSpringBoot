/**
 * @Description
 * @Author everforcc
 * @Date 2022-11-08 14:59
 * Copyright
 */

package cn.cc.busi.transa.controller;

import cn.cc.busi.transa.service.TransactionalService;
import cn.cc.core.domain.R;
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
    public R<Void> tthrow(@RequestParam(value = "id", required = false) String id) {
        log.info("throw E");
        transactionalService.throwMethod(id);
        return R.ok();
    }

    @GetMapping("/nothrow")
    public R<Void> nothrow(@RequestParam(value = "id", required = false) String id) {
        log.info("no throw E");
        transactionalService.tryMethod(id);
        return R.ok();
    }

}
