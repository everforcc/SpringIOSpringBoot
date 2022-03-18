package com.cc.sp01init.controller;

import com.cc.sp01init.pojo.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HController {

    @Autowired
    Dog dogg;

    @GetMapping("/")
    public String h(){
        return dogg.toString();
    }

}
