package cn.cc.sp73readability;

import cn.cc.sp73readability.model.CatalogPageResult;
import cn.cc.sp73readability.model.ChapterLink;
import cn.cc.sp73readability.model.ReadabilityResult;
import cn.cc.sp73readability.service.impl.CatalogParserImpl;
import cn.cc.sp73readability.service.impl.ReadabilityServiceImpl;
import org.junit.jupiter.api.Test;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 验证目录解析与正文抽取的单元测试。
 * 使用本地样例 HTML，避免真实站点访问。
 */
class CatalogParserReadabilityTests {

    private final CatalogParserImpl catalogParser = new CatalogParserImpl();
    private final ReadabilityServiceImpl readabilityService = new ReadabilityServiceImpl();

    @Test
    void parseCatalogAndReadabilityFromRemoteUrl() throws Exception {
        String baseUrl = "https://www.youduzw.com/book/11001/1262963_4.html";
        // 直接抓取远程 HTML（需网络可达）
        String userAgent2 = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36 Edg/143.0.0.0";
        Document doc = Jsoup.connect(baseUrl)
                .userAgent(userAgent2)
                .timeout(10_000)
                .get();
        String html = doc.outerHtml();

        CatalogPageResult catalog = catalogParser.parse(html, baseUrl);
        assertNotNull(catalog);
        assertNotNull(catalog.getNovel());
        assertEquals(baseUrl, catalog.getNovel().getCatalogUrl());
        assertNotNull(catalog.getNovel().getTitle());

        List<ChapterLink> chapters = catalog.getChapters();
        assertTrue(chapters.size() >= 3, "章节数量应解析到至少3条");
        assertEquals(1, chapters.get(0).getChapterIndex());

        ReadabilityResult readability = readabilityService.parse(html, baseUrl);
        assertNotNull(readability);
        System.out.println("readability.getContent(): \r\n" + readability.getContent());
        System.out.println("readability.getTitle(): \r\n" + readability.getTitle());
//        assertTrue(readability.getContent().contains("示例正文"));
        assertNotNull(readability.getTitle());
    }
}

