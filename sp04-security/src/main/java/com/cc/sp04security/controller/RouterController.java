package com.cc.sp04security.controller;

import com.alibaba.fastjson.JSONObject;
import com.cc.sp04security.constant.SecurityConstant;
import com.cc.sp04security.dto.CustomUser;
import com.cc.sp04security.dto.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Slf4j
//@Controller
@RestController
public class RouterController {

    @RequestMapping({"/","/index"})
    public String index(){

        log.info("跳转首页");
        return "index";
    }

    @RequestMapping("/user/login")
    public String login(HttpServletResponse response){

        log.info("跳转login");

        CustomUser customUser = new CustomUser();
        customUser.setUsername("CustomUser");
        customUser.setPassword("passoword");

        customUser.setRoles(UserRole.stringList);

        response.setHeader(SecurityConstant.token, JSONObject.toJSONString(customUser));
        return "/user/login";
    }

    @RequestMapping("/security/t")
    public String tsecurity(@AuthenticationPrincipal CustomUser customUser){
        log.info(JSONObject.toJSONString(customUser));
        return "/security/t";
    }


    @RequestMapping("/user/out")
    public String logout(){

        log.info("跳转logout");
        return "views/login";
    }

    @RequestMapping("/level1/{index}")
    public String level1(@PathVariable String index){

        log.info("level1");
        return "views/level1/" + index;
    }

    @RequestMapping("/level2/{index}")
    public String level2(@PathVariable String index){

        log.info("level2");
        return "views/level2/" + index;
    }

    @RequestMapping("/level3/{index}")
    public String level3(@PathVariable String index){

        log.info("level3");
        return "views/level3/" + index;
    }


}
