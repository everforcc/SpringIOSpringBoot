package cn.cc.busi.jsonfield.dao;

import cn.cc.dto.NovelDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IJSONFieldDao {

    @Insert("INSERT INTO cc_novel (`uuid`) VALUES ( #{newuuid} )")
    int insertNovel(NovelDto novelDto);

    @Select("select * from cc_novel where name = #{name}")
    List<NovelDto> list(NovelDto novelDto);

}
