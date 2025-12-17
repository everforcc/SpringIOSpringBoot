package cn.cc.sync.binlog.dao;

import cn.cc.sync.binlog.dto.BinlogMessageEntity;

import java.util.List;

public interface BinlogMessageMapper {

    int insert(BinlogMessageEntity entity);

    List<BinlogMessageEntity> selectByTimeRange(String database, String table,
                                                long startTime, long endTime);

}
