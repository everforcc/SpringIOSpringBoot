/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-14 23:28
 * Copyright
 */

package cn.cc.sp31usercraw.controller;

import cn.cc.sp31usercraw.service.INovelConfigService;
import com.cc.sp90utils.entity.ResultE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 配制好的controller
 * 配置信息直接保存到后端
 * 直接请求就好
 */
@Slf4j
@RequestMapping("/novel")
@RestController
public class NovelConfigController {

    @Resource
    INovelConfigService iNovelConfigService;

    /**
     * 传入url获取简介等信息
     */
    public String msg() {
        return "";
    }

    /**
     * 目录清单等信息
     */
    public String menu() {
        return "";
    }

    /**
     * 章节内容
     */
    @GetMapping("/content")
    public ResultE<String> content(@RequestParam("url") String url) {
        return new ResultE<String>().execute(e ->
                e.setSuccess(iNovelConfigService.down(url))
        );
    }

}
