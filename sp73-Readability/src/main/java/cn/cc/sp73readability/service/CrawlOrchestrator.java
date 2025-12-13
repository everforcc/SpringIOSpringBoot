package cn.cc.sp73readability.service;

import cn.cc.sp73readability.model.AiReadabilityNovel;

/**
 * 负责从目录 URL 出发，串联目录解析、分页探测、正文解析与持久化。
 */
public interface CrawlOrchestrator {

    /**
     * 从目录 URL 抓取并入库小说及章节。
     *
     * @param catalogUrl       目录页 URL
     * @param followPagination 是否跟踪目录分页
     * @param followNext       是否在正文页跟踪“下一章/下一页”
     * @param maxNextDepth     正文翻页最大深度，避免无限循环
     * @return 汇总结果（元数据、章节数等）
     */
    CrawlResult crawl(String catalogUrl, boolean followPagination, boolean followNext, int maxNextDepth);

    /**
     * 任务执行结果描述。
     */
    class CrawlResult {
        private AiReadabilityNovel aiReadabilityNovel;
        private int chapterCount;

        public AiReadabilityNovel getNovel() {
            return aiReadabilityNovel;
        }

        public void setNovel(AiReadabilityNovel aiReadabilityNovel) {
            this.aiReadabilityNovel = aiReadabilityNovel;
        }

        public int getChapterCount() {
            return chapterCount;
        }

        public void setChapterCount(int chapterCount) {
            this.chapterCount = chapterCount;
        }
    }
}

