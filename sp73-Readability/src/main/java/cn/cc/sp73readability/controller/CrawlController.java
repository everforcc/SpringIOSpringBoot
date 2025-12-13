package cn.cc.sp73readability.controller;

import cn.cc.sp73readability.service.CrawlOrchestrator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * 简易抓取入口：传入目录 URL，解析并保存小说与章节。
 * 说明：同步示例，便于演示；生产可改为异步任务。
 */
@RestController
@RequestMapping("/api/crawl")
@Validated
@Slf4j
public class CrawlController {

    private final CrawlOrchestrator orchestrator;

    public CrawlController(CrawlOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    /**
     * 测试参数 数据量小一点
     *
     * @param req http://www.xvipxs.net/199_199849/
     */
    @PostMapping("/catalog")
    public ResponseEntity<CrawlOrchestrator.CrawlResult> crawl(@RequestBody @Validated CrawlRequest req) {
        log.info("开始抓取目录：{}", req.getCatalogUrl());
        CrawlOrchestrator.CrawlResult result = orchestrator.crawl(
                req.getCatalogUrl(),
                Boolean.TRUE.equals(req.getFollowPagination()),
                Boolean.TRUE.equals(req.getFollowNextChapter()),
                req.getMaxNextDepth() == null ? 3 : req.getMaxNextDepth()
        );
        return ResponseEntity.ok(result);
    }

    /**
     * 请求体模型。
     */
    public static class CrawlRequest {
        @NotBlank(message = "catalogUrl 不能为空")
        private String catalogUrl;
        private Boolean followPagination = Boolean.TRUE;
        private Boolean followNextChapter = Boolean.TRUE;
        @Min(value = 0, message = "maxNextDepth 不能为负数")
        private Integer maxNextDepth = 3;

        public String getCatalogUrl() {
            return catalogUrl;
        }

        public void setCatalogUrl(String catalogUrl) {
            this.catalogUrl = catalogUrl;
        }

        public Boolean getFollowPagination() {
            return followPagination;
        }

        public void setFollowPagination(Boolean followPagination) {
            this.followPagination = followPagination;
        }

        public Boolean getFollowNextChapter() {
            return followNextChapter;
        }

        public void setFollowNextChapter(Boolean followNextChapter) {
            this.followNextChapter = followNextChapter;
        }

        public Integer getMaxNextDepth() {
            return maxNextDepth;
        }

        public void setMaxNextDepth(Integer maxNextDepth) {
            this.maxNextDepth = maxNextDepth;
        }
    }
}

