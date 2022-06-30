package com.cc.sp02thymeleaf.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

/**
 * 所有请求都会经过 DispatcherServlet
 */
@Configuration
public class MyMVCConfig implements WebMvcConfigurer {
    //

    @Autowired
    private ObjectMapper objectMapper;

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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //WebMvcConfigurer.super.addInterceptors(registry);

        registry.addInterceptor(new LoginHandlerInterceptor())
                // 需要登录的路径
                .addPathPatterns("/**")
                // 除了下面的路径
                .excludePathPatterns("/login/**")
                .excludePathPatterns("/open/**");
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
