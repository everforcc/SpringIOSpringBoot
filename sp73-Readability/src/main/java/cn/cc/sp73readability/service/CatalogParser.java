package cn.cc.sp73readability.service;

import cn.cc.sp73readability.model.CatalogPageResult;

/**
 * 目录页解析接口：提取小说元数据与章节链接列表。
 * 可基于 Jsoup + Readability 启发式实现。
 */
public interface CatalogParser {

    /**
     * 解析目录页 HTML。
     *
     * @param html    页面源码
     * @param baseUrl 用于补全相对链接的基准 URL
     * @return 解析出的元数据与章节列表
     */
    CatalogPageResult parse(String html, String baseUrl);
}

