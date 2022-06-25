/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-06 12:07
 * Copyright
 */

package cn.cc.sp31usercraw.controller;

import cn.cc.sp31usercraw.service.INovelConfigTestService;
import com.cc.sp90utils.entity.ResultE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试json
 * 三个接口通过后
 * 放入配置文件
 */
@Slf4j
@RequestMapping("/test")
@RestController
public class NovelConfigTestController {

    @Autowired
    INovelConfigTestService iNovelConfigTestService;

    /**
     * 1. 获取小说基本信息
     *
     * @param json 配置信息和请求地址
     * @return 基本信息
     */
    @PostMapping("/msg")
    public ResultE<String> msg(@RequestBody String json) {
        return new ResultE<String>().execute(e ->
                e.setSuccess(iNovelConfigTestService.getMsg(json).toString())
        );
    }

    /**
     * 2. 目录集合
     *
     * @param json 首页信息
     * @return 返回集合json
     */
    @PostMapping("/menu")
    public ResultE<String> menu(@RequestBody String json) {
        return new ResultE<String>().execute(e ->
                e.setSuccess(iNovelConfigTestService.getMenu(json).toString())
        );
    }

    /**
     * 3. 获取某一个章节的信息
     *
     * @param json 章节地址
     * @return 章节内容
     */
    @PostMapping("/content")
    public ResultE<String> content(@RequestBody String json) {
        return new ResultE<String>().execute(e ->
                e.setSuccess(iNovelConfigTestService.getContent(json).toString())
        );
    }

}
