/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-27 16:15
 * Copyright
 */

package com.cc.sp02thymeleaf.controller;

import com.cc.sp02thymeleaf.dto.CustomUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/authController")
public class AuthController {

    @GetMapping("/auth_1")
    public String auth_1(@RequestParam("string") String string , @RequestAttribute("customUser") CustomUser customUser){
        log.info("customUser-1 [{}] [{}]", customUser.toString(), string);
        return "成功进入";
    }

    @GetMapping("/auth_2")
    public String auth_2(@RequestParam("string") String string , @RequestAttribute("customUser") CustomUser customUser){
        log.info("customUser-2 [{}] [{}]", customUser.toString(), string);
        return "成功进入";
    }

}
