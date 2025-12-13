package cn.cc.sp73readability.service;

import org.jsoup.nodes.Document;

import java.util.Optional;

/**
 * 翻页探测接口：用于在章节页中找到“下一章/下一页”等链接。
 * 目的：为自动连续阅读提供下一章节的 URL，调用方可将其入队。
 */
public interface NextPageDetector {

    /**
     * 检测章节页中的下一页链接。
     *
     * @param document 章节页的 Jsoup Document
     * @param baseUrl  用于补全相对链接的基准 URL
     * @return 下一章 URL（已补全为绝对地址）或空
     */
    Optional<String> detect(Document document, String baseUrl);
}

