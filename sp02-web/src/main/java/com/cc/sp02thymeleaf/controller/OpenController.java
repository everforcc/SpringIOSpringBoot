package com.cc.sp02thymeleaf.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cc.sp02thymeleaf.annotation.EnumsValited;
import com.cc.sp02thymeleaf.dto.CustomUser;
import com.cc.sp02thymeleaf.dto.ParamDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/open")
@Validated
@Slf4j
public class OpenController {

//    @GetMapping("/")
//    public JSONArray he() {
//
//        return jsonArray2;
//    }

    @GetMapping("/a")
    public String H() {
//        List<ParamDto> paramDtoList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        ParamDto pre = new ParamDto();
        pre.setName("name");
        pre.setDate(new Date());

        for (int i = 0; i < 3; i++) {
            ParamDto paramDto = new ParamDto();
            paramDto.setDescription("第几个对象: " + i);
            pre.setName("name");
            jsonArray.add(paramDto);
        }

        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < 3; i++) {
            jsonObject.put("k", i);
            jsonObject.put("key0", jsonArray);
        }

        JSONArray jsonArray2 = new JSONArray();
        for (int i = 0; i < 3; i++) {
            jsonArray2.add(jsonObject);
        }

        return JSON.toJSONString(jsonArray2);
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

    /**
     * 上传对象json转对象
     *
     * @param json 对象json
     */
    @GetMapping("/json")
    public void getParamsJson(@RequestParam("json") String json) {
        CustomUser customUser = JSONObject.parseObject(json, CustomUser.class);
        System.out.println(customUser.toString());
    }

}
