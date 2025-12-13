package cn.cc.sp73readability.service;

import cn.cc.sp73readability.model.AiReadabilityChapter;
import cn.cc.sp73readability.model.AiReadabilityNovel;

import java.util.List;

/**
 * 负责将解析结果持久化到 MySQL。
 */
public interface ChapterPersistenceService {

    /**
     * 保存/更新小说元数据，返回数据库中的小说 ID。
     */
    Long saveOrUpdateNovel(AiReadabilityNovel aiReadabilityNovel);

    /**
     * 批量保存章节内容。
     */
    void saveChapters(Long novelId, List<AiReadabilityChapter> aiReadabilityChapters);
}

