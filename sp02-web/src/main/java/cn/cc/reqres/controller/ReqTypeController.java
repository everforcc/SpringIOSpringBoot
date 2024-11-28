package cn.cc.reqres.controller;

import cn.cc.authorization.dto.CustomUser;
import cn.cc.reqres.dto.ParamDto;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 测试各种类型的请求
 */
@RestController
@RequestMapping("/open/req")
@Validated
@Slf4j
public class ReqTypeController {

    /**
     * 6. post-dto
     * 此处注意前端请求的时候要设置请求头
     * Content-Type: application/json
     *
     * @param paramDto 参数
     * @return 返回值
     */
    @PutMapping("/postObj")
    public ParamDto postObj(@RequestBody final ParamDto paramDto) {
        log.info("postObj参数 param: {}", paramDto.toString());
        return paramDto;
    }

    @PutMapping("/postJSON")
    public ParamDto postJSON(@RequestBody final String json) {
        log.info("json: {}", json);
        ParamDto paramDto = JSONObject.parseObject(json, ParamDto.class);
        log.info("postObj参数 param: {}", paramDto.toString());
        return paramDto;
    }

    /**
     * 上传对象json转对象
     *
     * @param json 对象json
     */
    @GetMapping("/json")
    public void getParamsJson(@RequestParam("json") String json) {
        CustomUser customUser = JSONObject.parseObject(json, CustomUser.class);
        log.info("customUser.toString(): {}", customUser.toString());
    }

}
