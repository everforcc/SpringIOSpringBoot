package cn.cc.sp31usercraw.service;

import cn.cc.sp31usercraw.dto.NovelConfigDto;
import cn.cc.sp31usercraw.dto.NovelContentDto;
import cn.cc.sp31usercraw.dto.NovelMsgDto;

public interface INovelService {

    NovelMsgDto getTitle(String url, NovelConfigDto novelConfigDto);

    NovelMsgDto getMenu(String url, NovelConfigDto novelConfigDto);

    NovelContentDto getContent(String url, NovelConfigDto novelConfigDto);
}
