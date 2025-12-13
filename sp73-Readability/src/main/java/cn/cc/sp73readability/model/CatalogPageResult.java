package cn.cc.sp73readability.model;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 目录页解析结果，包含元数据和章节列表。
 */
public class CatalogPageResult implements Serializable {

    private AiReadabilityNovel aiReadabilityNovel;

    private List<ChapterLink> chapters = new ArrayList<>();

    public AiReadabilityNovel getNovel() {
        return aiReadabilityNovel;
    }

    public void setNovel(AiReadabilityNovel aiReadabilityNovel) {
        this.aiReadabilityNovel = aiReadabilityNovel;
    }

    public List<ChapterLink> getChapters() {
        return chapters;
    }

    public void setChapters(List<ChapterLink> chapters) {
        this.chapters = chapters;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this, SerializerFeature.PrettyFormat);
    }
}

