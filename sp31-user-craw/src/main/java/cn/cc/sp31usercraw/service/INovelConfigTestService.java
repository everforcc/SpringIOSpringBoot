package cn.cc.sp31usercraw.service;

import cn.cc.sp31usercraw.dto.NovelContentDto;
import cn.cc.sp31usercraw.dto.NovelMsgDto;

/**
 * 测试初始化
 * 小说配置json
 */
public interface INovelConfigTestService {

    NovelMsgDto getMsg(String json);

    NovelMsgDto getMenu(String json);

    NovelContentDto getContent(String url);
}
