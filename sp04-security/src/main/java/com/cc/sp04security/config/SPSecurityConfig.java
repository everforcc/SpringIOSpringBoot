package com.cc.sp04security.config;


import com.alibaba.fastjson.JSON;
import com.cc.sp04security.constant.SecurityConstant;
import com.cc.sp04security.dto.CustomUser;
import com.cc.sp04security.utils.RedisUtils;
import com.sun.org.apache.bcel.internal.classfile.Code;
import com.sun.org.apache.bcel.internal.classfile.CodeException;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Optional;

import static com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat;

@Slf4j
// @EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
// 使用 @EnableGlobalMethodSecurity 注解来启用基于注解的安全性
// @EnableGlobalMethodSecurity(securedEnabled = true) // 启用注解：@Secured；[@Secured("ROLE_USER"), @Secured("IS_AUTHENTICATED_ANONYMOUSLY")]
// @EnableGlobalMethodSecurity(prePostEnabled = true) // 启用注解：@PreAuthorize；[@PreAuthorize("hasAuthority('ROLE_USER')"), @PreAuthorize("isAnonymous()")]
@EnableWebSecurity
public class SPSecurityConfig{

    /**
     * 开放无需登录的接口
     */
    public static class OpenSecurityConfig{

    }

    @Order(1)
    @Configuration(proxyBeanMethods = false)
    @EnableGlobalMethodSecurity(prePostEnabled=true)
    public static class LoginSecurityConfig extends WebSecurityConfigurerAdapter{

        /**
         *
         * @param http
         * @throws Exception
         */
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            //super.configure(http);
            /**
             * 处理登录401，403，200，error等五种情况
             */
            final AuthHandler authHandler = new AuthHandler();
            http
                    /**
                     * 设置允许跨域
                     */
                    .csrf(
                    httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable()
                    )
                    /**
                     * http在系统中的链路追踪
                     */
                    .headers(
                            headers -> headers
                                    .addHeaderWriter((req, res) -> res.addHeader("X-B3-TraceId", MDC.get("X-B3-TraceId")))
                                    .xssProtection()
                            // .and().cacheControl() // 启用缓存
                    )
                    /**
                     * 用户访问未经授权的rest API，返回错误码401（未经授权）
                     */
                    .exceptionHandling().authenticationEntryPoint(authHandler).accessDeniedHandler(authHandler)
                    /**
                     * 指定可以直接访问的路径
                     * 可以追加多个,
                     * 除了指定的方法外，其他的方法都需要认证
                     */
                    .and().authorizeRequests()
                    .antMatchers("/open/**").permitAll()
                    .antMatchers("/user/**").permitAll()
                    //.antMatchers("/").permitAll()
                    .anyRequest().authenticated()
                    /**
                     * basi,postman或者http测试时候添加到请求头
                     */
                    .and().httpBasic()
                    /**
                     * 跳转登录页，但是没有用，下面的 successHandler需要这个返回的类
                     */
                    .and().formLogin()
                    .successHandler(authHandler)
                    .failureHandler(authHandler)
                    // 配置退出参数,下面单独列出
                    ;

            /**
             * 过滤器 before
             */
            http.addFilterBefore(
                    new JsonUsernamePasswordAuthenticationFilter(authenticationManager(), authHandler, authHandler),
                    UsernamePasswordAuthenticationFilter.class
            );

            /**
             * 过滤器 After
             */
            http.addFilterAfter(authTokenFilter, BasicAuthenticationFilter.class);

            /**
             * http退出配置
             */
            http.logout()
                    .logoutUrl("/user/out")
                    .logoutSuccessHandler(authHandler)
                    // 指定是否在注销时让HttpSession无效, 默认设置为 true
                    .invalidateHttpSession(true)
                    // 删除 Cookies
                    //.deleteCookies("Cookies")
                    .addLogoutHandler(new SecurityContextLogoutHandler()) // 添加一个LogoutHandler, // 清除 session
                    // 向客户端发送 清除 “cookie、storage、缓存” 消息
                    .addLogoutHandler((request, response, authentication) -> {// 添加一个LogoutHandler, 退出操作需要删除 redis token 缓存
                        final String token = request.getHeader("token");
                        if (!"".equals(token)&&null!=token) {
                            try {
                                log.info("移除用户有token");
                                // 从缓存中删除 用户
                                RedisUtils.removeUser(token);
                            } catch (Exception e) {
                                log.error(e.getMessage(), e);
                                // 有可能 token 已经过期了， 所以需要捕获异常，不向前端抛出
                            }
                        }else {
                            /**
                             * 已经进入，说明 .logoutUrl( 配置正确
                             */
                            log.info("测试是否进入logout");
                        }
                    })
            ;
        }

        /**
         * token 验证模式
         */
        OncePerRequestFilter authTokenFilter = new OncePerRequestFilter() {

            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                if (StringUtils.isBlank(request.getHeader(SecurityConstant.token))) {
                    filterChain.doFilter(request, response);
                    return;
                }
                try {
                    final String authJSON = request.getHeader(SecurityConstant.token);
//                        if (log.isTraceEnabled()) {
//                            final Enumeration<String> headerNames = request.getHeaderNames();
//                            final LinkedHashMap<String, String> obj = new LinkedHashMap<>();
//                            while (headerNames.hasMoreElements()) {
//                                val key = headerNames.nextElement();
//                                obj.put(key, request.getHeader(key));
//                            }
//                            log.trace(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> headers\n{}", JSON.toJSONString(obj, PrettyFormat));
//                        }

                    CustomUser customUser = CustomUser.tokenToUser(authJSON);
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(customUser, null, customUser.getAuthorities()));
                    filterChain.doFilter(request, response);
                    Optional.ofNullable(request.getSession(false)).ifPresent(session -> {
                        session.setMaxInactiveInterval(1); // 兼容默认的 session 模式，禁止 token 模式创建 session，设置 session 超时时间为1s
                    });
                } catch (Exception e) {
//                        if (e instanceof CodeException) {
//                            log.warn(e.getMessage());
//                        } else {
//                            log.error(e.getMessage(), e);
//                        }
                    log.warn(e.getMessage());
                    response.setContentType(SecurityConstant.CONTENT_TYPE);
                    PrintWriter writer = response.getWriter();
                    writer.write("登录出了异常");
                    writer.flush();
                    writer.close();
                }
            }
        };

    }

}
