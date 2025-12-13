/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.cc.sp73readability.demos.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 演示路径变量与正则路径的映射，方便在调试时确认路由规则。
 * 与爬虫业务无关，可视为 Spring MVC 语法样例。
 */
@Controller
public class PathVariableController {

    // 基础的多段路径变量示例
    // 示例： http://127.0.0.1:8080/user/123/roles/222
    @RequestMapping(value = "/user/{userId}/roles/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public String getLogin(@PathVariable("userId") String userId, @PathVariable("roleId") String roleId) {
        return "User Id : " + userId + " Role Id : " + roleId;
    }

    // 带正则匹配的路径变量示例（仅允许小写字母和连字符）
    // 示例： http://127.0.0.1:8080/javabeat/somewords
    @RequestMapping(value = "/javabeat/{regexp1:[a-z-]+}", method = RequestMethod.GET)
    @ResponseBody
    public String getRegExp(@PathVariable("regexp1") String regexp1) {
        return "URI Part : " + regexp1;
    }
}
