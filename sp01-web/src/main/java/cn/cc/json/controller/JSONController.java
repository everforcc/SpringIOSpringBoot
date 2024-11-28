package cn.cc.json.controller;

import cn.cc.json.dto.JSONDto;
import cn.cc.json.service.JSONService;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open/json")
@Validated
@Slf4j
public class JSONController {

    @Autowired
    JSONService jsonService;

    @GetMapping("/circular")
    public JSONArray circular() {
        return jsonService.circular();
    }

    @GetMapping("/date")
    public JSONDto formatDate() {
        return jsonService.formatDate();
    }

}
