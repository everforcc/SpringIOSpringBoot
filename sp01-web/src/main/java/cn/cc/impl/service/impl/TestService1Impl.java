package cn.cc.impl.service.impl;

import cn.cc.impl.service.ITestService;
import org.springframework.stereotype.Service;

@Service
public class TestService1Impl implements ITestService {
    @Override
    public String getName(String name) {
        name += "111";
        return name;
    }
}
