package cn.cc.sp42velocity.controller;

import cn.cc.sp42velocity.dto.ListDemo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Description : 测试模板生成
 * @Author : GKL
 * @Date: 2024-05-21 10:08
 */
@Slf4j
@RequestMapping("/feign")
@RestController
public class FeignTestController {

    @GetMapping("/string")
    public String string() {
        log.info(".../feign/string");
        return "feign";
    }

    @GetMapping("/dto")
    public ListDemo dto() {
        log.info(".../feign/dto");
        ListDemo listDemo = new ListDemo();
        listDemo.setName("feign");
        return listDemo;
    }

}