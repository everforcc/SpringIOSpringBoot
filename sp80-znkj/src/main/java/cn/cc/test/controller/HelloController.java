package cn.cc.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    /**
     * 测试这个项目的访问情况
     */
    @GetMapping("/cc")
    public String index() {
        return "index";
    }

} 