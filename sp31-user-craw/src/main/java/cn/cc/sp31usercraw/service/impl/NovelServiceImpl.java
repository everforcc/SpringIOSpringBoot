/**
 * Project:TODO ADD PROJECT NAME SpringIOSpringBoot
 *
 * @Description
 * @Author Author Date Description
 * ------ ------ ------
 *    TODO 开发人员邮箱前缀 调整时间 年-月-日 主要改动点>5字符
 * @Date 2022-06-06 12:02
 * Copyright
 */

package cn.cc.sp31usercraw.service.impl;

import cn.cc.sp31usercraw.dto.NovelConfigDto;
import cn.cc.sp31usercraw.dto.NovelContentDto;
import cn.cc.sp31usercraw.dto.NovelMsgDto;
import cn.cc.sp31usercraw.flow.INovelCommonFlowService;
import cn.cc.sp31usercraw.service.INovelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NovelServiceImpl implements INovelService {

    @Autowired
    INovelCommonFlowService iNovelCommonFlowService;

    @Override
    public NovelMsgDto getTitle(String url, NovelConfigDto novelConfigDto) {
        //novelConfigDto.setNovelMsgTileXR("//div[@id='content']//tr[@align='center']//td//table//tbody//tr//td//span//b//@text()");
        return iNovelCommonFlowService.getNovelMsg(url, novelConfigDto);
    }

    @Override
    public NovelMsgDto getMenu(String url, NovelConfigDto novelConfigDto) {
        log.info("即将获取获取 [{}]", url);
        NovelMsgDto novelMsgDto = iNovelCommonFlowService.getMenu(url, novelConfigDto);
        List<String> novelCapterUrlList = novelMsgDto.getNovelCapterUrlList();
        for(String capterUrl: novelCapterUrlList){
            log.info("即将获取章节地址 [{}]", capterUrl);
            iNovelCommonFlowService.getContent(capterUrl, novelConfigDto, novelMsgDto);
        }
        log.info("获取完成 [{}]", url);
        return novelMsgDto;
    }

    @Override
    public NovelContentDto getContent(String url, NovelConfigDto novelConfigDto, NovelMsgDto novelMsgDto) {
        return iNovelCommonFlowService.getContent(url, novelConfigDto, novelMsgDto);
    }


}
