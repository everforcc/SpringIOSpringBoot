//package com.cc.sp04security.config;
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.MDC;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
//import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
//
//@Slf4j
//@EnableWebSecurity
//public class SPDemoSecurityConfig extends WebSecurityConfigurerAdapter {
//
//
//    /**
//     * 1. 授权
//     * @param http
//     * @throws Exception
//     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        //super.configure(http);
//
//        http
//                /**
//                 * 未登录可以访问的地址
//                 */
//                .requestMatchers(matchers -> {
//                    matchers.antMatchers("/")
//                    // open 前缀的 url 不需要登录
//                    .antMatchers("/open/**")
//                    // druid 监控
//                    .antMatchers("/druid/**");
//
//                    /**
//                     * 某些自定义功能的页面的地址
//                     */
//                    if("".equals("true")){
//                        matchers.antMatchers("/custom/**");
//                    }
//                })
//                /**
//                 * CSRF spring默认防攻击.这里可以开启允许跨域
//                 */
//                .csrf(csrf->{
//                    csrf.disable();
//                })
//                /**
//                 * TODO 待分析
//                 */
//                .headers(headers ->
//                        headers.addHeaderWriter((req, res) -> res.addHeader("X-B3-TraceId", MDC.get("X-B3-TraceId")))
//                                .xssProtection())
//                .anonymous().and()
//                .servletApi().and()
//                .headers().and()
//                .authorizeRequests()
//                .anyRequest().permitAll()
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
//        ;
//
//        /**
//         * 如果需要添加
//         */
//        //http.addFilter();
//
//        /**
//         * 1. 仅用来测试，前后端分离不用
//         *
//         * 1.1首页所有人可以访问
//          */
//
//        http.authorizeHttpRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("/level1/**").hasRole("vip1")
//                .antMatchers("/level2/**").hasRole("vip2")
//                .antMatchers("/level3/**").hasRole("vip3")
//        ;
//
//
//        /**
//         *  1.2 没有权限跳转登录页
//         */
////        http.formLogin();
//
//        /**
//         * 1.3 推出
//         */
////        http.logout().logoutSuccessUrl("/");
//
//        /**
//         * 1.4 记住我，默认保存两周
//         */
//        //http.rememberMe();
//
//    }
//
//    /**
//     * 2. 认证
//     *
//     * <p>There is no PasswordEncoder mapped for the id "null"</p>
//     * 密码需要加密
//     * 在Spring Security 5.0+ 中新增加了很多的加密方法
//     * @param auth
//     * @throws Exception
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        //super.configure(auth);
//        // 1. 数据库
//        //auth.jdbcAuthentication()
//        // 2. 内存定好
//        auth.inMemoryAuthentication().passwordEncoder(bCryptPasswordEncoder)
//                .withUser("admin").password(bCryptPasswordEncoder.encode("123456")).roles("vip1","vip2")
//                .and()
//                .withUser("root").password(bCryptPasswordEncoder.encode("123456")).roles("vip1","vip2","vip3");
//    }
//
//
//
//}
