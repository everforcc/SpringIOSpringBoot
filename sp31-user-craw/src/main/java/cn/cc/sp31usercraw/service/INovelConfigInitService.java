package cn.cc.sp31usercraw.service;

import cn.cc.sp31usercraw.dto.NovelContentDto;
import cn.cc.sp31usercraw.dto.NovelMsgDto;

public interface INovelConfigInitService {

    /**
     * 获取小说基本信息
     *
     * @param novelUrl 地址
     * @return 小说对象
     */
    NovelMsgDto getMsg(String novelUrl);

    /**
     * 获取小说目录集合
     *
     * @param novelUrl 地址
     * @return 小说对象
     */
    NovelMsgDto getMenu(String novelUrl);

    /**
     * 获取小说某一章的内容
     *
     * @param novelCapterUrl 章节地址
     * @return 小说对象
     */
    NovelContentDto getContent(String novelCapterUrl);

    /**
     * 组合以上信息下载小说
     *
     * @param novelUrl 小说地址
     */
    void down(String novelUrl);

}
