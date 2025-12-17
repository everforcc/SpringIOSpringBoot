package cn.cc.sync.binlog.service;

import cn.cc.sync.binlog.dao.BinlogMessageMapper;
import cn.cc.sync.binlog.dto.BinlogMessage;
import cn.cc.sync.binlog.dto.BinlogMessageEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据入库
 */
@Service
public class MessageStorageService {

    @Resource
    private BinlogMessageMapper mapper;
    @Resource
    private ObjectMapper objectMapper;

    public void store(BinlogMessage message) {
        BinlogMessageEntity entity = new BinlogMessageEntity();
        entity.setDatabase(message.getDatabase());
        entity.setTable(message.getTable());
        entity.setSqlType(message.getSqlType());
        try {
            entity.setSqlListJson(objectMapper.writeValueAsString(message.getSqlList()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Serialize SQL list failed", e);
        }
        entity.setBinlogFile(message.getBinlogFile());
        entity.setBinlogPos(message.getBinlogPos());
        entity.setTimestamp(message.getTimestamp());
        mapper.insert(entity);
    }

    /**
     * 恢复数据
     * @param database
     * @param table
     * @param startTime
     * @param endTime
     * @return
     */
    public List<BinlogMessage> loadMessages(String database, String table,
                                            long startTime, long endTime) {
        List<BinlogMessageEntity> entities =
                mapper.selectByTimeRange(database, table, startTime, endTime);

        return entities.stream().map(entity -> {
            BinlogMessage message = new BinlogMessage();
            message.setDatabase(entity.getDatabase());
            message.setTable(entity.getTable());
            message.setSqlType(entity.getSqlType());
            try {
                message.setSqlList(objectMapper.readValue(
                        entity.getSqlListJson(), new TypeReference<List<String>>() {}));
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Deserialize SQL list failed", e);
            }
            message.setBinlogFile(entity.getBinlogFile());
            message.setBinlogPos(entity.getBinlogPos());
            message.setTimestamp(entity.getTimestamp());
            return message;
        }).collect(Collectors.toList());
    }
}