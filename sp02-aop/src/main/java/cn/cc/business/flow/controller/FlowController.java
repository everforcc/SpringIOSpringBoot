package cn.cc.business.flow.controller;

import cn.cc.business.flow.service.IFlowService;
import cn.cc.core.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aop/flow")
public class FlowController {

    @Autowired
    IFlowService flowService;

    @GetMapping("/f")
    public R<String> flow() {
        return R.ok(flowService.testFlow());
    }

    @GetMapping("/save")
    public R<String> save() {
        return R.ok(flowService.save("", ""));
    }

    @GetMapping("/savea")
    public R<String> savea() {
        return R.ok(flowService.save_a("", ""));
    }
}
