package com.cc.sp02thymeleaf.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * 所有请求都会经过 DispatcherServlet
 */
@Configuration
public class MyMVCConfig implements WebMvcConfigurer {
    //

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 启用 FastJson
     */
    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask(); // 解决循环引用问题
        final FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        // 字符集
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        // 字段排序
        converter.getFastJsonConfig().setFeatures(Feature.OrderedField);
        //
        converter.setSupportedMediaTypes(Collections.singletonList(
                MediaType.APPLICATION_JSON
        ));
        return converter;
    }

    /**
     * dto枚举字段 不匹配映射为null
     */
    @PostConstruct
    public void myObjectMapper() {
        // 解决enum不匹配问题 默认值为false
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        //objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE, true);
    }

    /**
     * 实现视图解析器的类 ，我们就可以把他看作视图解析器
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //WebMvcConfigurer.super.addViewControllers(registry);
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
    }

    /**
     * 添加拦截器
     *
     * @param registry 注册进去
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //WebMvcConfigurer.super.addInterceptors(registry);

        // 添加某个业务系统拦截器
        registry.addInterceptor(new LoginHandlerInterceptor())
                // 需要登录的路径
                .addPathPatterns("/**")
                // 除了下面的路径
                .excludePathPatterns("/login/**")
                .excludePathPatterns("/open/**");

        //  也可以添加别的系统的拦截器

    }

    /*@Bean
    public ViewResolver myViewResolver(){
        return new MyViewResolver();
    }


    // 自定义视图解析器
    public static class MyViewResolver implements ViewResolver{

        @Override
        public View resolveViewName(String viewName, Locale locale) throws Exception {
            return null;
        }
    }*/

}
