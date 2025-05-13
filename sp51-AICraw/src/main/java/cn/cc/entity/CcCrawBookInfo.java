package cn.cc.entity;

import java.io.Serializable;

public class CcCrawBookInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String autherId;
    private String name;
    private String pic;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAutherId() {
        return autherId;
    }

    public void setAutherId(String autherId) {
        this.autherId = autherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}    