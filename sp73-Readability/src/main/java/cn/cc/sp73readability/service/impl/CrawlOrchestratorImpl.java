package cn.cc.sp73readability.service.impl;

import cn.cc.sp73readability.model.*;
import cn.cc.sp73readability.model.AiReadabilityNovel;
import cn.cc.sp73readability.service.CatalogPaginationDetector;
import cn.cc.sp73readability.service.CatalogParser;
import cn.cc.sp73readability.service.ChapterPersistenceService;
import cn.cc.sp73readability.service.CrawlOrchestrator;
import cn.cc.sp73readability.service.NextPageDetector;
import cn.cc.sp73readability.service.ReadabilityService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * 串联目录解析、分页探测、正文抽取与持久化的编排实现。
 * 说明：
 * - 同步、单线程示例，便于接口调用；后续可改为异步 Worker。
 * - 支持目录分页（逻辑一）与正文翻页（逻辑二）的基础链路。
 */
@Slf4j
@Service
public class CrawlOrchestratorImpl implements CrawlOrchestrator {

    private static final String USER_AGENT = "Mozilla/5.0 (sp73-readability-bot)";
    private static final int TIMEOUT_MS = 10_000;

    private final CatalogParser catalogParser;
    private final CatalogPaginationDetector catalogPaginationDetector;
    private final NextPageDetector nextPageDetector;
    private final ReadabilityService readabilityService;
    private final ChapterPersistenceService chapterPersistenceService;

    public CrawlOrchestratorImpl(CatalogParser catalogParser,
                                 CatalogPaginationDetector catalogPaginationDetector,
                                 NextPageDetector nextPageDetector,
                                 ReadabilityService readabilityService,
                                 ChapterPersistenceService chapterPersistenceService) {
        this.catalogParser = catalogParser;
        this.catalogPaginationDetector = catalogPaginationDetector;
        this.nextPageDetector = nextPageDetector;
        this.readabilityService = readabilityService;
        this.chapterPersistenceService = chapterPersistenceService;
    }

    @Override
    public CrawlResult crawl(String catalogUrl, boolean followPagination, boolean followNext, int maxNextDepth) {
        Objects.requireNonNull(catalogUrl, "catalogUrl不能为空");
        CrawlResult result = new CrawlResult();

        // 1) 拉全目录（含分页）
        CatalogAggregate aggregate = fetchAllCatalogPages(catalogUrl, followPagination);
        if (aggregate.aiReadabilityNovel == null || CollectionUtils.isEmpty(aggregate.chapters)) {
            return result;
        }
        log.info("小说元数据：{}", aggregate.chapters.size());

        // 2) 持久化小说元数据
        Long novelId = chapterPersistenceService.saveOrUpdateNovel(aggregate.aiReadabilityNovel);
        if (novelId == null) {
            return result;
        }

        // 3) 抓取章节正文
        List<AiReadabilityChapter> aiReadabilityChapters = fetchChaptersWithBody(aggregate.chapters, followNext, maxNextDepth);
        if (!aiReadabilityChapters.isEmpty()) {
            chapterPersistenceService.saveChapters(novelId, aiReadabilityChapters);
        }

        result.setNovel(aggregate.aiReadabilityNovel);
        result.setChapterCount(aiReadabilityChapters.size());
        return result;
    }

    /**
     * 抓取目录及其分页，合并所有章节链接。
     */
    private CatalogAggregate fetchAllCatalogPages(String catalogUrl, boolean followPagination) {
        CatalogAggregate aggregate = new CatalogAggregate();
        Set<String> visited = new HashSet<>();
        Deque<String> queue = new ArrayDeque<>();
        queue.add(catalogUrl);

        while (!queue.isEmpty()) {
            String url = queue.poll();
            if (!visited.add(url)) {
                continue;
            }
            Document doc = fetch(url);
            if (doc == null) {
                continue;
            }
            CatalogPageResult page = catalogParser.parse(doc.outerHtml(), url);
            if (aggregate.aiReadabilityNovel == null && page.getNovel() != null) {
                aggregate.aiReadabilityNovel = page.getNovel();
            }
            if (!CollectionUtils.isEmpty(page.getChapters())) {
                aggregate.chapters.addAll(page.getChapters());
            }
            if (followPagination) {
                Optional<String> next = catalogPaginationDetector.detect(doc, url);
                next.filter(n -> !visited.contains(n)).ifPresent(queue::add);
            }
            log.info("已抓取目录页：{}", page.toString());
        }
        return aggregate;
    }

    /**
     * 抓取章节正文，并支持正文翻页（下一章/下一页）。
     */
    private List<AiReadabilityChapter> fetchChaptersWithBody(List<ChapterLink> links, boolean followNext, int maxNextDepth) {
        List<AiReadabilityChapter> aiReadabilityChapters = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        for (ChapterLink link : links) {
            String url = link.getUrl();
            if (!StringUtils.hasText(url) || !visited.add(url)) {
                continue;
            }
            processSingleChapter(link.getChapterIndex(), link.getTitle(), url, aiReadabilityChapters, visited, followNext, maxNextDepth);
            log.info("已抓取章节：{}", link.toString());
        }
        return aiReadabilityChapters;
    }

    /**
     * 抓取单章正文，必要时递归跟进“下一章/下一页”。
     */
    private void processSingleChapter(Integer index,
                                      String linkTitle,
                                      String url,
                                      List<AiReadabilityChapter> sink,
                                      Set<String> visited,
                                      boolean followNext,
                                      int remainingDepth) {
        Document doc = fetch(url);
        if (doc == null) {
            return;
        }
        ReadabilityResult readability = readabilityService.parse(doc.outerHtml(), url);
        AiReadabilityChapter aiReadabilityChapter = new AiReadabilityChapter();
        aiReadabilityChapter.setChapterIndex(index);
        // 标题优先用正文解析结果，fallback 链接标题
        aiReadabilityChapter.setChapterTitle(StringUtils.hasText(readability.getTitle()) ? readability.getTitle() : linkTitle);
        aiReadabilityChapter.setContent(readability.getContent());
        aiReadabilityChapter.setSourceUrl(url);
        sink.add(aiReadabilityChapter);

        // 跟进“下一章/下一页”链接
        if (followNext && remainingDepth > 0) {
            Optional<String> next = nextPageDetector.detect(doc, url);
            if (next.isPresent()) {
                String nextUrl = next.get();
                if (visited.add(nextUrl)) {
                    processSingleChapter(index == null ? null : index + 1, linkTitle, nextUrl, sink, visited, true, remainingDepth - 1);
                }
            }
        }
    }

    private Document fetch(String url) {
        try {
            return Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .timeout(TIMEOUT_MS)
                    .get();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 累积目录解析结果。
     */
    private static class CatalogAggregate {
        private AiReadabilityNovel aiReadabilityNovel;
        private final List<ChapterLink> chapters = new ArrayList<>();
    }
}

