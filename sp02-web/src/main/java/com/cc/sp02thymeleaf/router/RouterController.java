package com.cc.sp02thymeleaf.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

/**
 * @Description : 响应式编程
 * @Author : GKL
 * @Date: 2024-04-23 14:27
 */

@Configuration
public class RouterController {

    @Bean
    public RouterFunction<ServerResponse> routeExampleHandler(RouterHandler exampleHandler) {
        RouterFunctions.Builder route = RouterFunctions.route();
        route = route.GET("/open/router/{id}", exampleHandler::getExample);
        route = route.POST("/open/router", exampleHandler::createExample);
        // 也可以链式写法
        route = route.PUT("/open/router/update", exampleHandler::updateExample)
                .DELETE("/open/router/{delId}", exampleHandler::deleteExample);
        return route.build();
    }

}