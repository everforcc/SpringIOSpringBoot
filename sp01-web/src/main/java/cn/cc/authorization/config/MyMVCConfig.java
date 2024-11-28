package cn.cc.authorization.config;

import cn.cc.authorization.handler.LoginHandlerInterceptor;
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
