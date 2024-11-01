/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-29 19:17
 * Copyright
 */

package com.cc.sp02thymeleaf.controller;

import com.cc.sp02thymeleaf.dto.ParamDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/open")
@Validated
@Slf4j
public class OpenDateController {

    @GetMapping("/date")
    public ParamDto getParam() {
        ParamDto paramDto = new ParamDto();
        paramDto.setDate(new Date());
        return paramDto;
    }

}
