/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-27 20:34
 * Copyright
 */

package com.cc.sp02thymeleaf.controller;

import com.cc.sp02thymeleaf.dto.CustomUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping("/u")
    public String login(@RequestBody CustomUser customUser) {
        HashSet<String> stringSet = new HashSet<>();
        stringSet.add("auth_1");
        stringSet.add("auth_2");
        customUser.setFuncSet(stringSet);
        String json = customUser.toString();
        log.info("json [{}]", json);
        return json;
    }

}
