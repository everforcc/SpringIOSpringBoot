package com.cc.sp01init.controller;

import com.cc.sp01init.pojo.Dog;
import com.cc.sp01init.service.ValitedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HController {

    @Autowired
    Dog dogg;

    @Autowired
    ValitedService valitedService;

    @GetMapping("/")
    public String h(){
        return valitedService.valitDto(dogg);
        //return dogg.toString();
    }

    @GetMapping("/v")
    public String v(){
        Dog dog = new Dog();
        dog.setAge(1);
        return valitedService.valitDto(dog);
        //return dogg.toString();
    }

}
