package cn.cc.sp73readability.service.impl;

import cn.cc.sp73readability.model.AiReadabilityNovel;
import cn.cc.sp73readability.model.CatalogPageResult;
import cn.cc.sp73readability.model.ChapterLink;
import cn.cc.sp73readability.service.CatalogParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 基于 Jsoup 的目录页解析实现，采用启发式：
 * - 从 <title>、<meta name=author> 等提取元数据
 * - 查找包含最多章节链接的容器作为章节列表
 */
@Service
public class CatalogParserImpl implements CatalogParser {

    private static final int MIN_LINK_TEXT = 2;
    private static final int MAX_LINK_TEXT = 60;

    @Override
    public CatalogPageResult parse(String html, String baseUrl) {
        Document doc = Jsoup.parse(html, baseUrl);
        doc.outputSettings().prettyPrint(false);

        CatalogPageResult result = new CatalogPageResult();
        AiReadabilityNovel aiReadabilityNovel = new AiReadabilityNovel();

        aiReadabilityNovel.setTitle(extractTitle(doc));
        aiReadabilityNovel.setAuthor(extractAuthor(doc));
        aiReadabilityNovel.setSynopsis(extractSynopsis(doc));
        aiReadabilityNovel.setCatalogUrl(baseUrl);
        result.setNovel(aiReadabilityNovel);

        result.setChapters(extractChapters(doc));
        return result;
    }

    private String extractTitle(Document doc) {
        String title = doc.title();
        if (StringUtils.hasText(title)) {
            title = title.replace("最新章节", "").replace("全文阅读", "").trim();
        }
        if (!StringUtils.hasText(title)) {
            Element h1 = doc.selectFirst("h1");
            if (h1 != null) {
                title = h1.text();
            }
        }
        return title;
    }

    private String extractAuthor(Document doc) {
        String author = doc.select("meta[name=author]").attr("content");
        if (!StringUtils.hasText(author)) {
            // 在页面文本中寻找“作者”关键词
            Element authorElem = doc.select("*:matchesOwn(作者[:：].+)").first();
            if (authorElem != null) {
                author = authorElem.text().replace("作者：", "").replace("作者:", "").trim();
            }
        }
        return author;
    }

    private String extractSynopsis(Document doc) {
        // 优先 meta description
        String desc = doc.select("meta[name=description]").attr("content");
        if (StringUtils.hasText(desc)) {
            return desc;
        }
        // 次选包含“简介/介绍/内容简介”的段落
        Element intro = doc.select("*:matchesOwn((简介)|(介绍)|(内容简介))").parents().stream()
                .findFirst().orElse(null);
        if (intro != null) {
            return intro.text();
        }
        return null;
    }

    private List<ChapterLink> extractChapters(Document doc) {
        // 策略1: 优先查找包含章节模式（"第X章"）的链接容器
        Element bestContainer = findContainerByChapterPattern(doc);
        boolean isChapterPatternContainer = (bestContainer != null);
        
        // 策略2: 如果策略1未找到，优先查找包含"最新章节"或"正文"关键词的区域
        if (bestContainer == null) {
            bestContainer = findContainerByKeywords(doc);
        }
        
        // 策略3: 如果前两个策略都未找到，则找出链接数量最多的父容器（排除推荐阅读）
        if (bestContainer == null) {
            bestContainer = findContainerByLinkCount(doc);
        }

        List<ChapterLink> chapterLinks = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        if (bestContainer != null) {
            Elements chapterAnchors = bestContainer.select("a[href]");
            int idx = 1;
            for (Element a : chapterAnchors) {
                String text = a.text();
                String href = a.absUrl("href");
                if (!StringUtils.hasText(text) || !StringUtils.hasText(href)) {
                    continue;
                }
                if (text.length() < MIN_LINK_TEXT || text.length() > MAX_LINK_TEXT) {
                    continue;
                }
                
                // 如果容器是通过章节模式找到的，只提取包含章节模式的链接
                if (isChapterPatternContainer && !isChapterPattern(text)) {
                    continue;
                }
                
                // 排除推荐阅读区域的链接（除非包含章节模式，说明可能是误判）
                if (isInRecommendedReading(a) && !isChapterPattern(text)) {
                    continue;
                }
                
                if (!seen.contains(href)) {
                    seen.add(href);
                    chapterLinks.add(new ChapterLink(idx++, text.trim(), href));
                }
            }
        }

        // 若未找到合适容器，兜底：按出现顺序截取前 200 个合格链接（优先章节模式，排除推荐阅读）
        if (chapterLinks.isEmpty()) {
            Elements links = doc.select("a[href]");
            int idx = 1;
            for (Element a : links) {
                String text = a.text();
                String href = a.absUrl("href");
                if (!StringUtils.hasText(text) || !StringUtils.hasText(href)) {
                    continue;
                }
                if (text.length() < MIN_LINK_TEXT || text.length() > MAX_LINK_TEXT) {
                    continue;
                }
                // 排除推荐阅读区域的链接（除非包含章节模式）
                if (isInRecommendedReading(a) && !isChapterPattern(text)) {
                    continue;
                }
                if (!seen.add(href)) {
                    continue;
                }
                chapterLinks.add(new ChapterLink(idx++, text.trim(), href));
                if (chapterLinks.size() >= 200) {
                    break;
                }
            }
        }

        // 按 index 排序保证顺序
        chapterLinks.sort(Comparator.comparingInt(ChapterLink::getChapterIndex));
        return chapterLinks;
    }

    /**
     * 通过章节模式（"第X章"）查找章节列表容器
     */
    private Element findContainerByChapterPattern(Document doc) {
        Elements links = doc.select("a[href]");
        Element bestContainer = null;
        int maxChapterLinkCount = 0;

        for (Element link : links) {
            String text = link.text();
            if (!StringUtils.hasText(text) || text.length() < MIN_LINK_TEXT || text.length() > MAX_LINK_TEXT) {
                continue;
            }
            
            // 如果链接文本包含章节模式，向上查找父容器
            if (isChapterPattern(text)) {
                Element parent = link.parent();
                int maxDepth = 10;
                while (parent != null && maxDepth-- > 0) {
                    // 排除推荐阅读容器
                    if (isRecommendedReadingContainer(parent)) {
                        parent = parent.parent();
                        continue;
                    }
                    
                    // 统计该容器中包含章节模式的链接数量
                    Elements containerLinks = parent.select("a[href]");
                    int chapterLinkCount = 0;
                    for (Element containerLink : containerLinks) {
                        String linkText = containerLink.text();
                        if (StringUtils.hasText(linkText) && isChapterPattern(linkText)) {
                            chapterLinkCount++;
                        }
                    }
                    
                    // 如果找到包含足够章节链接的容器，返回它
                    if (chapterLinkCount >= 5 && chapterLinkCount > maxChapterLinkCount) {
                        maxChapterLinkCount = chapterLinkCount;
                        bestContainer = parent;
                    }
                    
                    parent = parent.parent();
                }
            }
        }

        return bestContainer;
    }

    /**
     * 判断文本是否包含章节模式（如"第1章"、"第122章"等）
     */
    private boolean isChapterPattern(String text) {
        if (!StringUtils.hasText(text)) {
            return false;
        }
        // 匹配"第X章"模式，X可以是数字或中文数字
        return text.matches(".*第[\\d零一二三四五六七八九十百千万]+章.*");
    }

    /**
     * 通过关键词查找章节列表容器（优先查找包含"最新章节"或"正文"的区域）
     */
    private Element findContainerByKeywords(Document doc) {
        // 查找包含"最新章节"或"正文"关键词的元素
        Elements keywordElements = doc.select("*:matchesOwn((最新章节)|(正文))");
        
        for (Element keywordElem : keywordElements) {
            // 向上查找父容器，寻找包含章节链接的容器
            Element container = keywordElem.parent();
            int maxDepth = 10; // 限制向上查找的深度
            while (container != null && maxDepth-- > 0) {
                // 检查该容器是否包含推荐阅读关键词，如果包含则跳过
                if (isRecommendedReadingContainer(container)) {
                    container = container.parent();
                    continue;
                }
                
                Elements links = container.select("a[href]");
                int validLinkCount = 0;
                for (Element link : links) {
                    String text = link.text();
                    if (StringUtils.hasText(text) && text.length() >= MIN_LINK_TEXT && text.length() <= MAX_LINK_TEXT) {
                        validLinkCount++;
                    }
                }
                
                // 如果找到包含足够链接的容器，返回它
                if (validLinkCount >= 5) { // 至少5个有效链接才认为是章节列表
                    return container;
                }
                
                container = container.parent();
            }
        }
        
        return null;
    }

    /**
     * 通过链接数量查找章节列表容器（排除推荐阅读区域）
     */
    private Element findContainerByLinkCount(Document doc) {
        Elements links = doc.select("a[href]");
        Element bestContainer = null;
        int maxCount = 0;

        for (Element link : links) {
            // 排除推荐阅读区域的链接
            if (isInRecommendedReading(link)) {
                continue;
            }
            
            String text = link.text();
            if (!StringUtils.hasText(text) || text.length() < MIN_LINK_TEXT || text.length() > MAX_LINK_TEXT) {
                continue;
            }
            Element parent = link.parent();
            
            // 排除推荐阅读容器
            if (isRecommendedReadingContainer(parent)) {
                continue;
            }
            
            int count = parent.select("> a[href]").size();
            if (count > maxCount) {
                maxCount = count;
                bestContainer = parent;
            }
        }

        return bestContainer;
    }

    /**
     * 判断元素是否在推荐阅读区域内
     */
    private boolean isInRecommendedReading(Element element) {
        Element parent = element.parent();
        int maxDepth = 10;
        while (parent != null && maxDepth-- > 0) {
            if (isRecommendedReadingContainer(parent)) {
                return true;
            }
            parent = parent.parent();
        }
        return false;
    }

    /**
     * 判断容器是否是推荐阅读容器
     */
    private boolean isRecommendedReadingContainer(Element container) {
        String text = container.text();
        String html = container.html();
        // 检查是否包含推荐阅读相关的关键词
        return (text != null && (text.contains("推荐阅读") || text.contains("推荐地址"))) ||
               (html != null && (html.contains("推荐阅读") || html.contains("推荐地址")));
    }
}

