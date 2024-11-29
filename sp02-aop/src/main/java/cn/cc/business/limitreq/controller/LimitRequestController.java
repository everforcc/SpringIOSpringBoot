package cn.cc.business.limitreq.controller;

import cn.cc.aop.annotation.LimitRequest;
import cn.cc.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/aop/limit")
public class LimitRequestController {

    //@LimitRequest(count = 2)
    @LimitRequest
    @GetMapping("/one")
    public R<String> one() {
        return R.ok("未被拦截");
    }

    @LimitRequest(count = 2)
    @GetMapping("/two")
    public R<String> two() {
        return R.ok("未被拦截");
    }

}
