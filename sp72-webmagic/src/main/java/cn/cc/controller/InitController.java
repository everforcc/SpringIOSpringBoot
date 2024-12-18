package cn.cc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open/webmagic")
public class InitController {

    @GetMapping("/craw")
    public String craw() {
        return "";
    }

}
