package cn.cc.service.impl;

import cn.cc.entity.CcCrawBookInfo;
import cn.cc.entity.CcCrawBookCapter;
import cn.cc.entity.CcCrawBookContent;
import cn.cc.mapper.BookMapper;
import cn.cc.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public CcCrawBookInfo getBookInfo(Long id) {
        return bookMapper.getBookInfo(id);
    }

    @Override
    public List<CcCrawBookCapter> getChapterList(Long bookId) {
        return bookMapper.getChapterList(bookId);
    }

    @Override
    public CcCrawBookContent getChapterContent(Long chapterId) {
        return bookMapper.getChapterContent(chapterId);
    }

    @Override
    public Long getBookIdByChapterId(Long chapterId) {
        return bookMapper.getBookIdByChapterId(chapterId);
    }
}    