package cn.cc.service;

import cn.cc.entity.CcCrawBookInfo;
import cn.cc.entity.CcCrawBookCapter;
import cn.cc.entity.CcCrawBookContent;

import java.util.List;

public interface BookService {
    CcCrawBookInfo getBookInfo(Long id);
    List<CcCrawBookCapter> getChapterList(Long bookId);
    CcCrawBookContent getChapterContent(Long chapterId);
    Long getBookIdByChapterId(Long chapterId);
}    