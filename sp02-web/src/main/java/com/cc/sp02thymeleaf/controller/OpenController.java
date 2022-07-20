package com.cc.sp02thymeleaf.controller;

import com.alibaba.fastjson.JSONObject;
import com.cc.sp02thymeleaf.annotation.EnumsValited;
import com.cc.sp02thymeleaf.dto.ParamDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/open")
@Validated
@Slf4j
public class OpenController {

    @GetMapping("/")
    public String H() {
        return "123";
    }

    /**
     * 自定义数据校验
     *
     * @param pageSize 页面大小
     * @return 页面大小
     */
    @GetMapping("/valited/{pageSize}")
    public Long valited(@EnumsValited @PathVariable("pageSize") Long pageSize) {
        log.info("日志打印pageSize: 【{}】", pageSize);
        return pageSize;
    }

    /**
     * 6. post-dto
     * 此处注意前端请求的时候要设置请求头
     * Content-Type: application/json
     *
     * @param paramDto 参数
     * @return 返回值
     */
    @PutMapping("/postObj")
    public ParamDto postObj(@RequestBody final ParamDto paramDto) {
        System.out.println("postObj参数 param: {}" + paramDto.toString());
        return paramDto;
    }

    @PutMapping("/postJSON")
    public ParamDto postJSON(@RequestBody final String json) {
        System.out.println("json: " + json);
        ParamDto paramDto = JSONObject.parseObject(json, ParamDto.class);
        System.out.println("postObj参数 param: {}" + paramDto.toString());
        return paramDto;
    }

}
