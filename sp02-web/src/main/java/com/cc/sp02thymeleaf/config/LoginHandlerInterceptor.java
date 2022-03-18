package com.cc.sp02thymeleaf.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 登录成功后，保存用户session
        HttpSession httpSession = request.getSession(true);
        String result = (String) httpSession.getAttribute("username");

        System.out.println("result: " + result);

        if("true".equals(result)){
            return true;
        }
        response.setContentType("text/plain;charset=utf-8");
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(("被拦截返回异常信息:" + result) .getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
        //return HandlerInterceptor.super.preHandle(request, response, handler);
        return false;
    }
}
