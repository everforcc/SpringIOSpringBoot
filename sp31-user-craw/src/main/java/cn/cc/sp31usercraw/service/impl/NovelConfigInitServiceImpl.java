/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-26 00:03
 * Copyright
 */

package cn.cc.sp31usercraw.service.impl;

import cn.cc.sp31usercraw.dodo.NovelConfigDo;
import cn.cc.sp31usercraw.dto.NovelConfigDto;
import cn.cc.sp31usercraw.dto.NovelContentDto;
import cn.cc.sp31usercraw.dto.NovelMsgDto;
import cn.cc.sp31usercraw.flow.INovelCommonFlowService;
import cn.cc.sp31usercraw.service.INovelConfigInitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class NovelConfigInitServiceImpl implements INovelConfigInitService {

    /* 获取数据接口 */
    @Autowired
    INovelCommonFlowService iNovelCommonFlowService;

    /* 加载配置文件 */
    @Resource
    NovelConfigDo novelConfigDo;

    @Override
    public NovelMsgDto getMsg(String url) {
        log.info("加载配置文件获取小说基本信息 [{}]", url);
        NovelConfigDto novelConfigDto = novelConfigDo.getConfig(url);
        return iNovelCommonFlowService.getNovelMsg(url, novelConfigDto);
    }

    @Override
    public NovelMsgDto getMenu(String url) {
        log.info("加载配置文件获取小说章节信息 [{}]", url);
        NovelConfigDto novelConfigDto = novelConfigDo.getConfig(url);
        // 1. 获取基本信息
        NovelMsgDto novelMsgDto = iNovelCommonFlowService.getNovelMsg(url, novelConfigDto);
        // 2. 获取章节信息
        novelMsgDto = iNovelCommonFlowService.getMenu(url, novelConfigDto, novelMsgDto);
        // 3. 取出章节
        List<String> novelCapterUrlList = novelMsgDto.getNovelCapterUrlList();
        // 4. 打印章节地址
        for (String capterUrl : novelCapterUrlList) {
            log.info("即将获取章节地址 [{}]", capterUrl);
        }
        return novelMsgDto;
    }

    @Override
    public NovelContentDto getContent(String url) {
        log.info("加载配置文件获取小说内容 信息 [{}]", url);
        NovelConfigDto novelConfigDto = novelConfigDo.getConfig(url);
        return iNovelCommonFlowService.getContent(url, novelConfigDto, null);
    }
}
