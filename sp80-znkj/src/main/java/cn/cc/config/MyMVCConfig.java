package cn.cc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 所有请求都会经过 DispatcherServlet
 */
@Configuration
public class MyMVCConfig implements WebMvcConfigurer {

    /**
     * 实现视图解析器的类 ，我们就可以把他看作视图解析器
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

    }

    /**
     * 添加拦截器
     *
     * @param registry 注册进去
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);

        // 添加某个业务系统拦截器
        registry.addInterceptor(new LKLResConfig())
        ;

        //  也可以添加别的系统的拦截器
    }

}
