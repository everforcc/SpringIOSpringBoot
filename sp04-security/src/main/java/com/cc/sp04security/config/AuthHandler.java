package com.cc.sp04security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class AuthHandler implements
        AccessDeniedHandler,
        AuthenticationEntryPoint,
        AuthenticationSuccessHandler,
        AuthenticationFailureHandler,
        LogoutSuccessHandler {

    /**
     * AccessDeniedHandler-403-无权限
     * @param request
     * @param response
     * @param accessDeniedException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        response.setContentType("text/plain;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write("{\"key\",\"AccessDeniedHandler-403-无权限\"}");
        writer.flush();
        writer.close();
    }

    /**
     * AuthenticationEntryPoint 用来解决匿名用户访问无权限资源时的异常
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;utf-8");
        PrintWriter writer = response.getWriter();
        writer.write("{\"key\",\"AuthenticationEntryPoint 用来解决匿名用户访问无权限资源时的异常\"}");
        writer.flush();
        writer.close();
    }

    /*@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }*/

    /**
     * AuthenticationSuccessHandler，登录成功后执行
     * loginSuccessUrl 失效
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("onAuthenticationSuccess( " + SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        response.setContentType("application/json;utf-8");
        PrintWriter writer = response.getWriter();
        writer.write("{\"key\",\"AuthenticationSuccessHandler，登录成功后执行\"}");
        writer.flush();
        writer.close();
    }

    /**
     * AuthenticationFailureHandler登录失败后执行
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        log.info("具体使用时，需要手动捕获异常信息");

        response.setContentType("application/json;utf-8");
        PrintWriter writer = response.getWriter();
        writer.write("{\"key\",\"AuthenticationFailureHandler登录失败后执行\"}");
        writer.flush();
        writer.close();
    }

    /**
     * LogoutSuccessHandler  推出成功后执行逻辑
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;utf-8");
        PrintWriter writer = response.getWriter();
        writer.write("{\"key\",\"LogoutSuccessHandler  登录成功后执行逻辑\"}");
        writer.flush();
        writer.close();
    }
}
