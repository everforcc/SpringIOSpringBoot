package cn.cc.sp73readability.mybatis.mapper;

import cn.cc.sp73readability.model.AiReadabilityChapter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AiReadabilityChapterMapper {

    int batchInsertOrUpdate(@Param("aiReadabilityChapters") List<AiReadabilityChapter> aiReadabilityChapters);
}

