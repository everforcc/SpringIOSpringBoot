package com.cc.sp02thymeleaf.controller;

import com.alibaba.fastjson.JSONObject;
import com.cc.sp02thymeleaf.dto.ParamDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/open")
public class HController {

    @GetMapping("/")
    public String H(){
        return "123";
    }

    /**
     * 6. post-dto
     * 此处注意前端请求的时候要设置请求头
     * Content-Type: application/json
     * @param paramDto
     * @return
     */
    @PutMapping("/postObj")
    public ParamDto postObj(@RequestBody final ParamDto paramDto){
        System.out.println("postObj参数 param: {}" + paramDto.toString());
        return paramDto;
    }

    @PutMapping("/postJSON")
    public ParamDto postJSON(@RequestBody final String json){
        System.out.println("json: " + json);
        ParamDto paramDto = JSONObject.parseObject(json, ParamDto.class);
        System.out.println("postObj参数 param: {}" + paramDto.toString());
        return paramDto;
    }

}
