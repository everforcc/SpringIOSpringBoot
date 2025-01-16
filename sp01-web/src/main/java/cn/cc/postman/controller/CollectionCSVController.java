package cn.cc.postman.controller;

import cn.cc.core.domain.R;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open/postman")
@Slf4j
public class CollectionCSVController {

    /**
     * 测试postman断言
     * 返回正确和失败
     *
     * @param jsonObject 入参
     * @return 返回
     */
    @PostMapping("/csv")
    public R<Void> csv(@RequestBody JSONObject jsonObject) {
        log.info("jsonObject: {}", jsonObject.toString());
        if ("a333".equals(jsonObject.getString("a"))) {
            return R.fail("自定义失败内容");
        }
        return R.ok();
    }

}
