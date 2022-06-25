/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-25 23:22
 * Copyright
 */

package cn.cc.sp31usercraw.controller;

import cn.cc.sp31usercraw.dodo.NovelConfigDo;
import cn.cc.sp31usercraw.dto.NovelContentDto;
import cn.cc.sp31usercraw.dto.NovelMsgDto;
import cn.cc.sp31usercraw.service.INovelConfigInitService;
import com.cc.sp90utils.entity.ResultE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 初始化配置文件
 */
@Slf4j
@RequestMapping("/init")
@RestController
public class NovelConfigInitController {

    /* 小说配置 */
    @Resource
    NovelConfigDo novelConfigDo;

    /* 获取小说内容 */
    @Autowired
    INovelConfigInitService iNovelConfigInitService;

    /**
     * 初始化了几个配置
     */
    @GetMapping("/size")
    public ResultE<Integer> size() {
        return new ResultE<Integer>().execute(e ->
                e.setSuccess(novelConfigDo.size())
        );
    }

    @GetMapping("/contains")
    public ResultE<Boolean> contains(@RequestParam("url") String url) {
        return new ResultE<Boolean>().execute(e ->
                e.setSuccess(novelConfigDo.contains(url))
        );
    }

    @GetMapping("/msg")
    public ResultE<NovelMsgDto> msg(@RequestParam("url") String url) {
        return new ResultE<NovelMsgDto>().execute(e ->
                e.setSuccess(iNovelConfigInitService.getMsg(url))
        );
    }

    @GetMapping("/menu")
    public ResultE<NovelMsgDto> menu(@RequestParam("url") String url) {
        return new ResultE<NovelMsgDto>().execute(e ->
                e.setSuccess(iNovelConfigInitService.getMenu(url))
        );
    }

    @GetMapping("/contnet")
    public ResultE<NovelContentDto> content(@RequestParam("url") String url) {
        return new ResultE<NovelContentDto>().execute(e ->
                e.setSuccess(iNovelConfigInitService.getContent(url))
        );
    }

}
