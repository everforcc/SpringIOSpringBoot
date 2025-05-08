package cn.cc.entity;

import java.io.Serializable;

public class CcCrawBookContent implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long chapterId;
    private String content;
    private Integer order;
    private String common;
    private Long prevChapterId;
    private Long nextChapterId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public Long getPrevChapterId() {
        return prevChapterId;
    }

    public void setPrevChapterId(Long prevChapterId) {
        this.prevChapterId = prevChapterId;
    }

    public Long getNextChapterId() {
        return nextChapterId;
    }

    public void setNextChapterId(Long nextChapterId) {
        this.nextChapterId = nextChapterId;
    }
}    