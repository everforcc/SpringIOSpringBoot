/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-25 11:21
 * Copyright
 */

package cn.cc.service.impl;

import cn.cc.dodo.NovelConfigDo;
import cn.cc.flow.INovelCommonFlowService;
import cn.cc.config.SysConfig;
import cn.cc.dto.NovelConfigDto;
import cn.cc.dto.NovelMsgDto;
import cn.cc.service.INovelConfigService;
import cn.cc.utils.DownUtils;
import cn.cc.utils.commons.web.HttpParamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 在这个类配置和获取数据
 */
@Service
@Slf4j
public class NovelConfigServiceImpl implements INovelConfigService {

    @Autowired
    INovelCommonFlowService iNovelCommonFlowService;

    @Resource
    NovelConfigDo novelConfigDo;
    @Resource
    SysConfig sysConfig;

    @Override
    public String down(String url) {
        NovelConfigDto novelConfigDto = novelConfigDo.getConfig(url);
        // 1. 获取小说基本信息
        NovelMsgDto novelMsgDto = iNovelCommonFlowService.getNovelMsg(url, novelConfigDto);
        // 2. 获取章节信息
        novelMsgDto = iNovelCommonFlowService.getMenu(url, novelConfigDto, novelMsgDto);
        // 3. 根据章节下载数据
        List<String> novelCapterUrlList = novelMsgDto.getNovelCapterUrlList();
        //
        for(String capterUrl: novelCapterUrlList){
            log.info("即将获取章节地址 [{}]", capterUrl);
            iNovelCommonFlowService.getContent(capterUrl, novelConfigDto, novelMsgDto);
        }
        // 章节地址的根目录应该和域名根目录一致
        // 在获取章节内容的时候新增线程,在这边标记添加结束
        String rootUrl = HttpParamUtils.getRootUrl(url);
        DownUtils downUtils = DownUtils.instantce(rootUrl, sysConfig.getFileRoot());
        downUtils.markAddEnd();

        log.info("获取完成 [{}]", url);
        return null;
    }

}
