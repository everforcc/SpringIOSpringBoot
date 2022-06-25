/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-06 12:02
 * Copyright
 */

package cn.cc.sp31usercraw.service.impl;

import cn.cc.sp31usercraw.dto.NovelConfigDto;
import cn.cc.sp31usercraw.dto.NovelContentDto;
import cn.cc.sp31usercraw.dto.NovelMsgDto;
import cn.cc.sp31usercraw.flow.INovelCommonFlowService;
import cn.cc.sp31usercraw.service.INovelConfigTestService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NovelConfigTestServiceImpl implements INovelConfigTestService {

    @Autowired
    INovelCommonFlowService iNovelCommonFlowService;

    /**
     * 1. 获取小说基本信息
     *
     * @param json
     * @return
     */
    @Override
    public NovelMsgDto getMsg(String json) {

        log.info("msg param: {}", json);
        JSONObject jsonObject = JSONObject.parseObject(json);
        String url = jsonObject.getString("url");
        String config = jsonObject.getString("config");
        NovelConfigDto novelConfigDto = JSONObject.parseObject(config, NovelConfigDto.class);

        return iNovelCommonFlowService.getNovelMsg(url, novelConfigDto);
    }

    /**
     * 2. 获取小说章节信息
     *
     * @param json
     * @return
     */
    @Override
    public NovelMsgDto getMenu(String json) {
        // 1. 从 json 获取配置信息和url
        log.info("menu param: {}", json);
        JSONObject jsonObject = JSONObject.parseObject(json);
        String url = jsonObject.getString("url");
        String config = jsonObject.getString("config");
        NovelConfigDto novelConfigDto = JSONObject.parseObject(config, NovelConfigDto.class);
        //

        log.info("即将获取获取 [{}]", url);
        // 2. 获取小说基本信息
        NovelMsgDto novelMsgDto = iNovelCommonFlowService.getNovelMsg(url, novelConfigDto);

        // 3. 获取章节信息
        novelMsgDto = iNovelCommonFlowService.getMenu(url, novelConfigDto, novelMsgDto);
        List<String> novelCapterUrlList = novelMsgDto.getNovelCapterUrlList();

        // 4. 打印章节地址
        for (String capterUrl : novelCapterUrlList) {
            log.info("即将获取章节地址 [{}]", capterUrl);
        }

        log.info("获取完成 [{}]", url);
        return novelMsgDto;
    }

    /**
     * 2. 获取章节内容
     *
     * @param json
     * @return
     */
    @Override
    public NovelContentDto getContent(String json) {

        // 1. 从json获取配置文件信息
        log.info("content param: {}", json);
        JSONObject jsonObject = JSONObject.parseObject(json);
        String url = jsonObject.getString("url");
        String config = jsonObject.getString("config");
        NovelConfigDto novelConfigDto = JSONObject.parseObject(config, NovelConfigDto.class);
        // 2. 获取内容
        return iNovelCommonFlowService.getContent(url, novelConfigDto, null);
    }


}
