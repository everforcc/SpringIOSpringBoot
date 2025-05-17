package cn.cc.entity;

import lombok.Data;
import java.util.Date;

@Data
public class ChatRecord {
    private Long id;
    private Long userId;
    private Long friendId;
    private String msg;
    private Date createTime;
} 