package cn.cc.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChapterUtils {

    // 第两百九十九章纳兰嫣然的出手
    // 正则匹配章节格式：如 “第一百二十八章”
    // 修改后的正则表达式，支持更复杂的中文数字（如“第一万三千八百六十四章”）
    private static final Pattern CHAPTER_PATTERN = Pattern.compile("^第([零一二三四五六七八九十百千万两〇]+)章|^第\\d+章");

    /**
     * 提取字符串中的章节编号（中文数字转阿拉伯数字）
     * @param title 原始字符串
     * @return 章节编号，如果没有匹配到返回 -1
     */
    public static int getChapterNumber(String title) {
        if (title == null || title.isEmpty()) {
            return -1;
        }

        Matcher matcher = CHAPTER_PATTERN.matcher(title);
        if (matcher.find()) {
            String chapterTitle = matcher.group(0); // 获取"第N章"部分
            String numberPart = chapterTitle.substring(1, chapterTitle.length() - 1); // 去掉"第"和"章"
            try {
                return ChineseNumberUtils.convert(numberPart);
            } catch (Exception e) {
                return -1; // 如果转换失败也返回 -1
            }
        }

        return -1; // 没有匹配到章节格式
    }

    // 第一万三千八百六十四章 输出 13864
    public static void main(String[] args) {
        System.out.println(getChapterNumber("第一万三千八百六十四章"));
        System.out.println(getChapterNumber("第两百九十九章纳兰嫣然的出手"));       // 输出：299
        System.out.println(getChapterNumber("第一百二十八章 正节标题")); // 输出：128
        System.out.println(getChapterNumber("第128章 正节标题"));         // 输出：128
        System.out.println(getChapterNumber("序章 开篇"));                // 输出：-1
        System.out.println(getChapterNumber("第一章 第一个章节"));       // 输出：1
    }
}

