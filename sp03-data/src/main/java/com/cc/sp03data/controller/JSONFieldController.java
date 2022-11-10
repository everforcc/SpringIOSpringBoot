/**
 * @Description
 * @Author everforcc
 * @Date 2022-11-10 16:50
 * Copyright
 */

package com.cc.sp03data.controller;

import com.cc.sp03data.dto.NovelDto;
import com.cc.sp03data.service.JSONFieldService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/jsonfield")
public class JSONFieldController {

    @Resource
    JSONFieldService jsonFieldService;

    @PostMapping("/test")
    public NovelDto cNovelDto(@RequestBody NovelDto novelDto) {
        return jsonFieldService.jsonFieldT(novelDto);
    }

}
