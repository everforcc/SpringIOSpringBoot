package cn.cc.init.controller;

import cn.cc.core.domain.R;
import cn.cc.init.pojo.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/open/init")
public class InitController {

    @Autowired
    Dog dog;

    @GetMapping("/dog")
    public R<Dog> dog() {
        return R.ok(dog);
    }

    // 配合 webmagic 测试提取链接
    @GetMapping("/dog1")
    public R<Dog> dog2() {
        Dog dog = new Dog();
        dog.setAge(99);
        dog.setEmail("eee");
        dog.setList(Arrays.asList("1", "2", "3"));
        dog.setName("nnn");
        dog.setStr("sss");
        return R.ok(dog);
    }

}
