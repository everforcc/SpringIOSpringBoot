package com.cc.sp02thymeleaf.router;

import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

/**
 * @Description : 响应式编程handler
 * @Author : GKL
 * @Date: 2024-04-23 14:32
 */

@Component
public class RouterHandler {

    public ServerResponse getExample(ServerRequest request) {
        // 获取参数信息
        String id = request.pathVariable("id");
        // 处理获取示例的逻辑
        return ServerResponse.ok().body("GET-查询请求, 参数信息:[" + id + "]: Get example");
    }

    public ServerResponse createExample(ServerRequest request) {
        // 处理创建示例的逻辑
        return ServerResponse.ok().body("POST-创建请求: Create example");
    }

    public ServerResponse updateExample(ServerRequest request) {
        // 获取参数信息
        MultiValueMap<String, String> params = request.params();
        // 处理更新示例的逻辑
        return ServerResponse.ok()
                .body("UPDATE-修改请求, 参数信息:[" + params.toSingleValueMap() + "]: Update example");
    }

    public ServerResponse deleteExample(ServerRequest request) {
        // 接收参数
        String delId = request.pathVariable("delId");
        // 处理删除示例的逻辑
        return ServerResponse.ok().body("Delete-删除请求, 参数信息:[" + delId + "]: Delete example");
    }

    public ServerResponse getInfo(ServerRequest request) {
        // 处理获取示例的逻辑
        return ServerResponse.ok().body("GET-查询请求: Get getInfo");
    }

}
