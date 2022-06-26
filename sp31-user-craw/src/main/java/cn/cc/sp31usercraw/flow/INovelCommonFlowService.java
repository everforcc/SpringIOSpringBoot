package cn.cc.sp31usercraw.flow;

import cn.cc.sp31usercraw.dto.NovelConfigDto;
import cn.cc.sp31usercraw.dto.NovelContentDto;
import cn.cc.sp31usercraw.dto.NovelMsgDto;

public interface INovelCommonFlowService {

    /**
     * 根据小说页链接 获取小说基本信息
     *
     * @param url            小说页链接
     * @param novelConfigDto 配置信息
     * @return 小说基本信息
     */
    NovelMsgDto getNovelMsg(String url, NovelConfigDto novelConfigDto);

    /**
     * 根据小说地址获取小说目录集合
     *
     * @param url            小说地址
     * @param novelConfigDto 配置信息
     * @return 小说目录集合
     */
    NovelMsgDto getMenu(String url, NovelConfigDto novelConfigDto, NovelMsgDto novelMsgDto);

    /**
     * 根据章节链接，上个接口返回的信息，
     * 获取内容信息
     *
     * @param url            章节地址
     * @param novelConfigDto 配置信息
     * @param novelMsgDto    测试的时候允许为空
     * @return 章节内容
     */
    NovelContentDto getContent(String url, NovelConfigDto novelConfigDto, NovelMsgDto novelMsgDto);

}
