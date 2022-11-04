/**
 * @Description
 * @Author everforcc
 * @Date 2022-11-04 10:14
 * Copyright
 */

package com.cc.sp03data.controller;

import com.cc.sp03data.dao.ITestProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/auto")
public class AutoSqlController {

    @Resource
    ITestProvider iTestProvider;

    /**
     * 测试
     * 1. 表a
     * 2. 表b
     * 3. 不存在的表
     *
     * @param from 表后缀
     * @param id   id
     * @return 公共字段
     */
    @GetMapping("/select")
    public String novelList(@RequestParam(value = "from", required = false) String from, @RequestParam("id") String id) {
        try {
            log.info("from:{},id:{} ", from, id);
            return iTestProvider.tSqlContext(from, id, "1", "1");
        } catch (Exception e) {
            log.error("异常: ", e.getMessage(), e);
        }
        return "error";
    }

}
