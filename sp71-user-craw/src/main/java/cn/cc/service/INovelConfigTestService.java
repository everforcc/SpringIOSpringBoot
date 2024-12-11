package cn.cc.service;

import cn.cc.dto.NovelContentDto;
import cn.cc.dto.NovelMsgDto;

/**
 * 测试初始化
 * 小说配置json
 */
public interface INovelConfigTestService {

    NovelMsgDto getMsg(String json);

    NovelMsgDto getMenu(String json);

    NovelContentDto getContent(String url);
}
