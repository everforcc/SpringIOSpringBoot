package cn.cc.sp42velocity.controller;

import cn.cc.sp42velocity.dto.DemoTemplate;
import cn.cc.sp42velocity.dto.ListDemo;
import cn.cc.sp42velocity.utils.VelocityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * @Description : 测试模板生成
 * @Author : GKL
 * @Date: 2024-05-21 10:08
 */
@RestController
public class VelocityController {

    @GetMapping("/velocityDemo")
    public String velocityDemo() {
        DemoTemplate demoTemplate = new DemoTemplate();
        demoTemplate.setId("1");
        demoTemplate.setTime("2023-08-01");
        ListDemo listDemo = new ListDemo();
        listDemo.setName("demo");
        ListDemo listDemo2 = new ListDemo();
        listDemo2.setName("demo2");
        demoTemplate.setListDemo(Arrays.asList(listDemo, listDemo2));
        return VelocityUtils.getXmlByData(demoTemplate);
    }
}