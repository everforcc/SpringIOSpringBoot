package cn.cc.sp73readability.service;

import org.jsoup.nodes.Document;

import java.util.Optional;

/**
 * 目录分页探测接口：用于在目录页中找到“下一页/下一章列表”等分页链接。
 * 适用于章节列表跨页的场景，调用方可抓取下一页目录并合并章节链接。
 */
public interface CatalogPaginationDetector {

    /**
     * 检测目录页中的下一页链接。
     *
     * @param document 目录页的 Jsoup Document
     * @param baseUrl  用于补全相对链接的基准 URL
     * @return 下一页目录的绝对 URL，未找到则返回空
     */
    Optional<String> detect(Document document, String baseUrl);
}

