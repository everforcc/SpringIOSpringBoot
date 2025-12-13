package cn.cc.sp73readability.mybatis.mapper;

import cn.cc.sp73readability.model.AiReadabilityNovel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AiReadabilityNovelMapper {

    AiReadabilityNovel selectByCatalogUrl(@Param("catalogUrl") String catalogUrl);

    int insertNovel(AiReadabilityNovel aiReadabilityNovel);

    int updateNovel(AiReadabilityNovel aiReadabilityNovel);
}

