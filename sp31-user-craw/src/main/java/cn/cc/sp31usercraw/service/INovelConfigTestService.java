package cn.cc.sp31usercraw.service;

import cn.cc.sp31usercraw.dto.NovelConfigDto;
import cn.cc.sp31usercraw.dto.NovelContentDto;
import cn.cc.sp31usercraw.dto.NovelMsgDto;

public interface INovelConfigTestService {

    NovelMsgDto getMsg(String json);

    NovelMsgDto getMenu(String json);

    NovelContentDto getContent(String url);
}
