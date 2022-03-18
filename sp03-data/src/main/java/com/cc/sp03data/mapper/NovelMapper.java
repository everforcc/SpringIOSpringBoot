package com.cc.sp03data.mapper;

import com.cc.sp03data.dto.NovelDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

// 这个注解表示是一个mybatis的mapper类
//@Mapper
//@Repository
public interface NovelMapper {


    List<NovelDto> queryNovelList();

    NovelDto queryByID(int id);

    int addNovel();

    int updateNovel();

    int deleteNovel(int id);

}
