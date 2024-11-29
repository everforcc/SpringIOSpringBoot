package cn.cc.business.field.controller;

import cn.cc.business.field.dto.SaveDto;
import cn.cc.business.field.service.SaveService;
import cn.cc.core.domain.R;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/aop/field")
public class FieldController {

    @Autowired
    SaveService saveService;

    @GetMapping("/save")
    public R<SaveDto> save() {
        log.info("测试是否全局唯一");
        log.info("X-B3-TraceId: " + MDC.get("X-B3-TraceId"));
        SaveDto saveDto = new SaveDto();
        return R.ok(saveService.save(saveDto));
    }

    @PostMapping("/json")
    public R<SaveDto> post(@RequestBody String json) {
        log.info("测试是否全局唯一 【{}】", json);
        log.info("X-B3-TraceId: " + MDC.get("X-B3-TraceId"));

        SaveDto saveDto = JSONObject.parseObject(json, SaveDto.class);
        return R.ok(saveService.save(saveDto, saveDto.getId()));
    }

    @PostMapping("/obj")
    public R<SaveDto> postObj(@RequestBody SaveDto saveDto) {
        log.info("测试是否全局唯一 【{}】", saveDto.toString());
        log.info("X-B3-TraceId: " + MDC.get("X-B3-TraceId"));
        return R.ok(saveService.save(saveDto, saveDto.getId()));
    }

}