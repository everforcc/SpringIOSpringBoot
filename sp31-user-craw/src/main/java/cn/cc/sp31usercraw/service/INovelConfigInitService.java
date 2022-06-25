package cn.cc.sp31usercraw.service;

import cn.cc.sp31usercraw.dto.NovelContentDto;
import cn.cc.sp31usercraw.dto.NovelMsgDto;

public interface INovelConfigInitService {

    NovelMsgDto getMsg(String url);

    NovelMsgDto getMenu(String url);

    NovelContentDto getContent(String url);

}
