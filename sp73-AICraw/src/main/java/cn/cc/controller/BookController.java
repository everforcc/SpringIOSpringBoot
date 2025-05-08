package cn.cc.controller;

import cn.cc.entity.CcCrawBookInfo;
import cn.cc.entity.CcCrawBookCapter;
import cn.cc.entity.CcCrawBookContent;
import cn.cc.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books/{id}")
    public CcCrawBookInfo getBookInfo(@PathVariable Long id) {
        return bookService.getBookInfo(id);
    }

    @GetMapping("/books/{id}/chapters")
    public List<CcCrawBookCapter> getChapterList(@PathVariable Long id) {
        return bookService.getChapterList(id);
    }

    @GetMapping("/chapters/{id}")
    public CcCrawBookContent getChapterContent(@PathVariable Long id) {
        return bookService.getChapterContent(id);
    }

    @GetMapping("/chapters/{id}/bookId")
    public Long getBookIdByChapterId(@PathVariable Long id) {
        return bookService.getBookIdByChapterId(id);
    }
}    