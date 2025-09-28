package cn.cc.impl.controller;

import cn.cc.impl.service.ITestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/impl")
public class TestServiceController {

    @Resource
    List<ITestService> testServiceList;

    @GetMapping("/name/{name}")
    public String getName(@PathVariable String name) {
        for (ITestService testService : testServiceList) {
            log.info("使用服务：{}", testService.getClass().getName());
            name += testService.getName(name);
        }
        return name;
    }

}
