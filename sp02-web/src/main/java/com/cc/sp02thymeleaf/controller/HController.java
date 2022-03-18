package com.cc.sp02thymeleaf.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HController {

    @GetMapping("/")
    public String H(){
        return "123";
    }

}
