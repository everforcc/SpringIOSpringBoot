package cn.cc.model;

import lombok.Data;

@Data
public class Message {
    private Long id;        // 消息唯一标识
    private String type;    // 消息类型：login、chat、addFriend、history等
    private String from;    // 发送方账号
    private String to;      // 接收方账号
    private String content; // 消息内容
    private Long time;      // 时间戳
    private Integer status; // 消息状态：0-发送中，1-发送成功，2-发送失败
}