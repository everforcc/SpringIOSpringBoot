package cn.cc.entity;

import java.io.Serializable;

public class CcCrawBookCapter implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long bookId;
    private String name;
    private Integer order;
    private String common;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}    