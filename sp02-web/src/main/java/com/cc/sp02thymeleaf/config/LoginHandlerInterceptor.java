package com.cc.sp02thymeleaf.config;

import com.cc.sp02thymeleaf.annotation.Authorization;
import com.cc.sp02thymeleaf.dto.CustomUser;
import com.cc.sp02thymeleaf.utils.IToken;
import com.cc.sp02thymeleaf.utils.impl.TokenJSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Slf4j
public class LoginHandlerInterceptor implements HandlerInterceptor {

    IToken iToken = new TokenJSON();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 登录成功后，保存用户session
        //HttpSession httpSession = request.getSession(true);
        //String result = (String) httpSession.getAttribute("username");
        //System.out.println("result: " + result);
        String token = request.getHeader("token");
        CustomUser customUser = iToken.stringToUser(token);
        if(Objects.isNull(customUser)){
            iToken.response(response, "没有用户信息");
            return false;
        }

        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            Authorization authorization = handlerMethod.getMethodAnnotation(Authorization.class);
            if(authorization != null){
                String[] auths = authorization.value();
                HashSet<String> funcSet = customUser.getFuncSet();
                for(String auth:auths){
                    if(funcSet.contains(auth)){
                     return true;
                    }
                }
                iToken.response(response, "没有权限");
            }
        }

        // 设置用户信息在,controller可以拿到
        request.setAttribute("customUser", customUser);
        return true;
    }
}
