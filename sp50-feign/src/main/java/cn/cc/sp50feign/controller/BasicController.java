package cn.cc.sp50feign.controller;

import cn.cc.sp50feign.feign.IFeignDemo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class BasicController {

    @Autowired
    IFeignDemo iFeignDemo;

    @RequestMapping("/s")
    @ResponseBody
    public String s() {
        log.info(".../s");
        return "string: " + iFeignDemo.feignString();
    }

    @RequestMapping("/j")
    @ResponseBody
    public String j() {
        log.info(".../j");
        return "dto: " + iFeignDemo.feignJson();
    }

    @RequestMapping("/d")
    @ResponseBody
    public String d() {
        log.info(".../d");
        return "dto: " + iFeignDemo.feignDto();
    }


}
