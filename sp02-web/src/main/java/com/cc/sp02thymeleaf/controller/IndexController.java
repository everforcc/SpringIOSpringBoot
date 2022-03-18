package com.cc.sp02thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;


@RestController
public class IndexController {

    // 需要模板引擎的支持
    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("msg","templates-thymelaf");

        model.addAttribute("list", Arrays.asList("1","a"));
        return "index";
    }

    @GetMapping("/test/{str}")
    public String test(@PathVariable String str){
        return str;
    }

    @GetMapping("/open/{str}")
    public String open(@PathVariable String str){
        return str;
    }

    @GetMapping("/login/{str}")
    public String login(@PathVariable String str, HttpServletRequest request){

        System.out.println("request.getRemoteAddr(): " + request.getRemoteAddr());
        HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute("username",str);
        return "123";
    }

    @GetMapping("/loginout/{str}")
    public String loginout(@PathVariable String str, HttpServletRequest request){

        System.out.println("request.getRemoteAddr(): " + request.getRemoteAddr());
        HttpSession httpSession = request.getSession(true);
        httpSession.removeAttribute("username");
        return "123";
    }

}
