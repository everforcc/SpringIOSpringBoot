package cn.cc.init.controller;

import cn.cc.core.domain.R;
import cn.cc.init.pojo.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open/init")
public class InitController {

    @Autowired
    Dog dog;

    @GetMapping("/dog")
    public R<Dog> dog(){
        return R.ok(dog);
    }

}
