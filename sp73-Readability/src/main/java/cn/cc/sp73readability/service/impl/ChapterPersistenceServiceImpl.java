package cn.cc.sp73readability.service.impl;

import cn.cc.sp73readability.model.AiReadabilityChapter;
import cn.cc.sp73readability.model.AiReadabilityNovel;
import cn.cc.sp73readability.mybatis.mapper.AiReadabilityChapterMapper;
import cn.cc.sp73readability.mybatis.mapper.AiReadabilityNovelMapper;
import cn.cc.sp73readability.service.ChapterPersistenceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
/**
 * 持久化实现：负责小说元数据与章节内容的写入。
 */
@Service
public class ChapterPersistenceServiceImpl implements ChapterPersistenceService {

    private final AiReadabilityNovelMapper aiReadabilityNovelMapper;
    private final AiReadabilityChapterMapper aiReadabilityChapterMapper;

    public ChapterPersistenceServiceImpl(AiReadabilityNovelMapper aiReadabilityNovelMapper, AiReadabilityChapterMapper aiReadabilityChapterMapper) {
        this.aiReadabilityNovelMapper = aiReadabilityNovelMapper;
        this.aiReadabilityChapterMapper = aiReadabilityChapterMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveOrUpdateNovel(AiReadabilityNovel aiReadabilityNovel) {
        if (aiReadabilityNovel == null || !StringUtils.hasText(aiReadabilityNovel.getCatalogUrl())) {
            return null;
        }
        AiReadabilityNovel db = aiReadabilityNovelMapper.selectByCatalogUrl(aiReadabilityNovel.getCatalogUrl());
        if (db == null) {
            aiReadabilityNovelMapper.insertNovel(aiReadabilityNovel);
            return aiReadabilityNovel.getNovelId();
        } else {
            // 只更新可用字段
            db.setAuthor(firstNonEmpty(aiReadabilityNovel.getAuthor(), db.getAuthor()));
            db.setTitle(firstNonEmpty(aiReadabilityNovel.getTitle(), db.getTitle()));
            db.setSynopsis(firstNonEmpty(aiReadabilityNovel.getSynopsis(), db.getSynopsis()));
            db.setStatus(firstNonEmpty(aiReadabilityNovel.getStatus(), db.getStatus()));
            aiReadabilityNovelMapper.updateNovel(db);
            return db.getNovelId();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveChapters(Long novelId, List<AiReadabilityChapter> aiReadabilityChapters) {
        if (novelId == null || CollectionUtils.isEmpty(aiReadabilityChapters)) {
            return;
        }
        aiReadabilityChapters.forEach(c -> c.setNovelId(novelId));
        aiReadabilityChapterMapper.batchInsertOrUpdate(aiReadabilityChapters);
    }

    private String firstNonEmpty(String a, String b) {
        if (StringUtils.hasText(a)) {
            return a;
        }
        return b;
    }
}

