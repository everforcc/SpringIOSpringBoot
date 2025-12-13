package cn.cc.sp73readability.service.impl;

import cn.cc.sp73readability.model.ReadabilityResult;
import cn.cc.sp73readability.service.ReadabilityService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 简化版 Readability：移除脚本样式，选取文本密度最高的节点作为正文。
 */
@Service
public class ReadabilityServiceImpl implements ReadabilityService {

    @Override
    public ReadabilityResult parse(String html, String baseUrl) {
        Document doc = Jsoup.parse(html, baseUrl);
        clean(doc);

        String title = extractTitle(doc);
        String content = extractMainContent(doc, title);

        return new ReadabilityResult(title, content);
    }

    private void clean(Document doc) {
        doc.select("script,style,noscript,iframe,footer,header,nav,form,button").remove();
    }

    private String extractTitle(Document doc) {
        Element h1 = doc.selectFirst("h1");
        if (h1 != null && StringUtils.hasText(h1.text())) {
            return h1.text().trim();
        }
        return doc.title();
    }

    private String extractMainContent(Document doc, String title) {
        // 先尝试常见正文容器
        Elements candidates = doc.select("article, main, #content, .content, #chaptercontent, .chapter-content, .article");
        Element best = pickBest(candidates);
        if (best == null) {
            // 兜底：在 body 下选出 <p> 文本总长度最大的直接子节点
            Element body = doc.body();
            best = body.children().stream()
                    .max(Comparator.comparingInt(this::sumParagraphLength))
                    .orElse(body);
        }
        if (best == null) {
            return "";
        }
        // 将段落去重、过滤后拼成纯文本，避免重复块
        Set<String> normalizedSet = new LinkedHashSet<>();
        Set<String> paragraphs = new LinkedHashSet<>();
        for (Element p : best.select("p, div")) {
            String text = p.text();
            if (!StringUtils.hasText(text)) {
                continue;
            }
            String trimmed = text.trim();
            if (shouldSkip(trimmed, title)) {
                continue;
            }
            String normalized = normalize(trimmed);
            if (!StringUtils.hasText(normalized) || normalized.length() < 10) {
                continue;
            }
            if (!normalizedSet.add(normalized)) {
                continue; // 按归一化文本去重，避免轻微差异导致重复
            }
            paragraphs.add(trimmed);
            if (paragraphs.size() >= 400) {
                break; // 防止异常页面过长
            }
        }
        if (paragraphs.isEmpty()) {
            return best.text().trim();
        }
        // 去除“长段落包含短段落”型重复，保留更短的核心正文
        paragraphs = dropContainedDuplicates(paragraphs);
        return String.join("\n", paragraphs).trim();
    }

    private boolean shouldSkip(String text, String title) {
        String lower = text.toLowerCase();
        if (lower.startsWith("当前位置") || lower.contains("上一章") || lower.contains("下一章")) {
            return true;
        }
        if (lower.contains("目录") && lower.contains("首页")) {
            return true;
        }
        if (lower.contains("手机阅读") || lower.contains("版权") || lower.contains("有度中文网")) {
            return true;
        }
        if (StringUtils.hasText(title) && text.trim().equals(title.trim())) {
            return true;
        }
        // 过滤掉明显过短的片段
        return text.length() < 15;
    }

    private String normalize(String text) {
        // 去掉空白和常见标点以便归一化去重
        String collapsed = WHITESPACE.matcher(text).replaceAll("");
        return PUNCT.matcher(collapsed).replaceAll("");
    }

    private Set<String> dropContainedDuplicates(Set<String> paragraphs) {
        String[] arr = paragraphs.toArray(new String[0]);
        boolean[] drop = new boolean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            if (drop[i]) continue;
            for (int j = i + 1; j < arr.length; j++) {
                if (drop[j]) continue;
                String a = arr[i];
                String b = arr[j];
                if (a.length() >= 40 && b.length() >= 40) {
                    if (a.contains(b) && a.length() >= b.length() + 20) {
                        drop[i] = true; // a 是冗余的长段，保留更精简的 b
                    } else if (b.contains(a) && b.length() >= a.length() + 20) {
                        drop[j] = true;
                    }
                }
            }
        }
        Set<String> result = new LinkedHashSet<>();
        for (int i = 0; i < arr.length; i++) {
            if (!drop[i]) {
                result.add(arr[i]);
            }
        }
        return result;
    }

    private static final Pattern WHITESPACE = Pattern.compile("\\s+");
    private static final Pattern PUNCT = Pattern.compile("[，。、“”\"'：:；;·—\\-\\u3000\\s]");

    private int sumParagraphLength(Element element) {
        return element.select("p").stream()
                .map(Element::text)
                .filter(StringUtils::hasText)
                .mapToInt(String::length)
                .sum();
    }

    private Element pickBest(Elements elements) {
        return elements.stream()
                .max(Comparator.comparingInt(e -> e.text().length()))
                .orElse(null);
    }
}

