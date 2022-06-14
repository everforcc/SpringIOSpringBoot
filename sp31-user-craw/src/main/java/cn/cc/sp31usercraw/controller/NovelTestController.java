/**
 * Project:TODO ADD PROJECT NAME SpringIOSpringBoot
 *
 * @Description
 * @Author Author Date Description
 * ------ ------ ------
 *    TODO 开发人员邮箱前缀 调整时间 年-月-日 主要改动点>5字符
 * @Date 2022-06-06 12:07
 * Copyright
 */

package cn.cc.sp31usercraw.controller;

import cn.cc.sp31usercraw.dto.NovelConfigDto;
import cn.cc.sp31usercraw.dto.NovelMsgDto;
import cn.cc.sp31usercraw.service.INovelService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试用的接口
 */
@Slf4j
@RequestMapping(value = "/test")
@RestController
public class NovelTestController {

    @Autowired
    INovelService iNovelService;

    /**
     * 获取小说基本信息
     * @param json 配置信息和请求地址
     * @return 基本信息
     */
    @PostMapping("/msg")
    public String msg(@RequestBody String json){
        log.info("msg param: {}", json);
        JSONObject jsonObject = JSONObject.parseObject(json);
        String url = jsonObject.getString("url");
        String config = jsonObject.getString("config");
        NovelConfigDto novelConfigDto = JSONObject.parseObject(config, NovelConfigDto.class);
        return iNovelService.getTitle(url, novelConfigDto).toString();
    }

    /**
     * 目录集合
     *
     * @param json 首页信息
     * @return 返回集合json
     */
    @PostMapping("/menu")
    public String menu(@RequestBody String json){
        log.info("menu param: {}", json);
        JSONObject jsonObject = JSONObject.parseObject(json);
        String url = jsonObject.getString("url");
        String config = jsonObject.getString("config");
        NovelConfigDto novelConfigDto = JSONObject.parseObject(config, NovelConfigDto.class);

        return iNovelService.getMenu(url, novelConfigDto).toString();
    }

    /**
     * 获取某一个章节的信息
     * @param json 章节地址
     * @return 章节内容
     */
    @PostMapping("/content")
    public String content(@RequestBody String json){
        log.info("content param: {}", json);
        JSONObject jsonObject = JSONObject.parseObject(json);
        String url = jsonObject.getString("url");
        String config = jsonObject.getString("config");
        String novelMsg = jsonObject.getString("novelMsg");

        NovelConfigDto novelConfigDto = JSONObject.parseObject(config, NovelConfigDto.class);
        NovelMsgDto novelMsgDto = JSONObject.parseObject(novelMsg, NovelMsgDto.class);

        return iNovelService.getContent(url, novelConfigDto,  novelMsgDto).toString();
    }

}
