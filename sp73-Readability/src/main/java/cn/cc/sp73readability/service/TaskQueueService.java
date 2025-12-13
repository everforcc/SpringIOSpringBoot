package cn.cc.sp73readability.service;

import cn.cc.sp73readability.model.ChapterTask;

import java.util.Optional;

/**
 * 负责与 Redis 交互的任务调度/去重接口。
 * 仅定义能力边界，具体实现可用 Spring Data Redis。
 */
public interface TaskQueueService {

    /** 推入目录页 URL，供 Worker 拉取解析目录。 */
    void pushCatalogUrl(String catalogUrl);

    /** 弹出目录页 URL。 */
    Optional<String> popCatalogUrl();

    /** 推入章节任务（包含小说 ID、章节序号与 URL）。 */
    void pushChapterTask(ChapterTask task);

    /** 弹出章节任务。 */
    Optional<ChapterTask> popChapterTask();

    /** 记录 URL 已抓取，用于去重。 */
    void markCrawled(String urlHash);

    /** 检查 URL 是否已抓取。 */
    boolean isCrawled(String urlHash);
}

