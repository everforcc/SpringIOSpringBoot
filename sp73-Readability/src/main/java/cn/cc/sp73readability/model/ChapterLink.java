package cn.cc.sp73readability.model;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.Serializable;

/**
 * 章节链接信息，用于目录页解析结果。
 */
public class ChapterLink implements Serializable {

    private Integer chapterIndex;

    private String title;

    private String url;

    public ChapterLink() {
    }

    public ChapterLink(Integer chapterIndex, String title, String url) {
        this.chapterIndex = chapterIndex;
        this.title = title;
        this.url = url;
    }

    public Integer getChapterIndex() {
        return chapterIndex;
    }

    public void setChapterIndex(Integer chapterIndex) {
        this.chapterIndex = chapterIndex;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    @Override
    public String toString() {
        return JSONObject.toJSONString(this, SerializerFeature.PrettyFormat);
    }
}

