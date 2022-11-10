package com.cc.sp03data.dao;

import com.cc.sp03data.dto.NovelDto;
import org.apache.ibatis.annotations.Insert;

public interface IJSONFieldDao {

    @Insert("INSERT INTO cc_novel (`uuid`) VALUES ( #{newuuid} )")
    int insertNovel(NovelDto novelDto);

}
