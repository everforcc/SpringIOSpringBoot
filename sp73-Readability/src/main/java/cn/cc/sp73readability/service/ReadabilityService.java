package cn.cc.sp73readability.service;

import cn.cc.sp73readability.model.ReadabilityResult;

/**
 * 章节页正文提取接口，封装 Readability 算法的调用。
 */
public interface ReadabilityService {

    /**
     * 从章节页 HTML 中提取标题与正文。
     *
     * @param html 页面源码
     * @param baseUrl 用于补全图片/链接等相对路径
     * @return 提取结果
     */
    ReadabilityResult parse(String html, String baseUrl);
}

