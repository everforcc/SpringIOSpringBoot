package cn.cc.sync.binlog.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * MyBatis 持久化
 * 保存binlog数据
 */
@Data
public class BinlogMessageEntity {
    private Long id;
    private String database;
    private String table;
    private String sqlType;
    private String sqlListJson;
    private String binlogFile;
    private Long binlogPos;
    private Long timestamp;
    private LocalDateTime createdAt;
}