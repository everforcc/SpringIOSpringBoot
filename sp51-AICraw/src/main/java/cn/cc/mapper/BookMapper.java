package cn.cc.mapper;

import cn.cc.entity.CcCrawBookCapter;
import cn.cc.entity.CcCrawBookContent;
import cn.cc.entity.CcCrawBookInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookMapper {

    @Select("select * from cc_craw_book_info where id = #{id} ")
    CcCrawBookInfo getBookInfo(Long id);

    @Select("select * from cc_craw_book_capter where book_id = #{bookId} ")
    List<CcCrawBookCapter> getChapterList(Long bookId);

    @Select("select * from cc_craw_book_content where chapter_id = #{chapter_id} ")
    CcCrawBookContent getChapterContent(Long chapterId);

    @Select("select book_id from cc_craw_book_capter where id = #{chapterId} ")
    Long getBookIdByChapterId(@Param("chapterId") Long chapterId);
}    