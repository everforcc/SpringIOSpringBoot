package cn.cc.sp73readability.service.impl;

import cn.cc.sp73readability.service.CatalogPaginationDetector;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * 默认目录分页探测实现：
 * - 优先识别 rel=next
 * - 再依据“下一页/下一章/下页/next”类关键词匹配分页导航
 * - 自动补全相对链接为绝对 URL
 * 适合大多数目录页的分页场景，可按域名扩展关键词或选择器。
 */
@Component
public class DefaultCatalogPaginationDetector implements CatalogPaginationDetector {

    private static final Set<String> KEYWORDS = new LinkedHashSet<>(Arrays.asList(
            "下一页", "下页", "下一章", "下一章列表", "下一部分", "next", "next page"
    ));

    @Override
    public Optional<String> detect(Document document, String baseUrl) {
        if (document == null) {
            return Optional.empty();
        }
        // 1) rel=next 更优先
        Element relNext = document.selectFirst("link[rel=next][href]");
        if (relNext != null) {
            String href = relNext.absUrl("href");
            if (StringUtils.hasText(href)) {
                return Optional.of(href);
            }
        }

        // 2) 文本匹配分页链接
        Elements anchors = document.select("a[href]");
        for (Element a : anchors) {
            String text = a.text();
            if (!StringUtils.hasText(text)) {
                continue;
            }
            String normalized = text.toLowerCase().replaceAll("\\s+", "");
            if (matchesKeyword(normalized)) {
                String href = a.absUrl("href");
                if (StringUtils.hasText(href) && !href.toLowerCase().startsWith("javascript")) {
                    return Optional.of(href);
                }
            }
        }
        return Optional.empty();
    }

    private boolean matchesKeyword(String normalizedText) {
        for (String kw : KEYWORDS) {
            String normKw = kw.toLowerCase().replaceAll("\\s+", "");
            if (normalizedText.contains(normKw)) {
                return true;
            }
        }
        return false;
    }
}

