package cn.cc.sync.binlog;

import cn.cc.sync.binlog.cache.TableMetadataCache;
import cn.cc.sync.binlog.dto.BinlogMessage;
import cn.cc.sync.binlog.dto.TableInfo;
import cn.cc.sync.binlog.mq.BinlogMessageSender;
import cn.cc.sync.binlog.util.SQLBuilderService;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.EventHeader;
import com.github.shyiko.mysql.binlog.event.EventHeaderV4;
import com.github.shyiko.mysql.binlog.event.QueryEventData;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * BinlogEventProcessor 是 Binlog 事件的核心编排者：负责分发不同类型的事件，
 * 依赖 TableMetadataCache 获取列信息，交由 SQLBuilderService 构造 SQL，
 * 最终通过 BinlogMessageSender 推送到内存 Map 与持久层。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BinlogEventProcessor {

    private final SQLBuilderService sqlBuilderService;
    private final BinlogMessageSender messageSender;
    private final TableMetadataCache metadataCache;
    private final BinlogPositionTracker positionTracker;

    public void process(Event event) {
        if (event == null) {
            return;
        }
        EventData data = event.getData();
        if (data instanceof TableMapEventData) {
            TableMapEventData mapData = (TableMapEventData) data;
            metadataCache.update(mapData);
            return;
        }
        // 根据事件类型选择处理策略
        if (data instanceof WriteRowsEventData) {
            handleWrite(event, (WriteRowsEventData) data);
        } else if (data instanceof UpdateRowsEventData) {
            handleUpdate(event, (UpdateRowsEventData) data);
        } else if (data instanceof DeleteRowsEventData) {
            handleDelete(event, (DeleteRowsEventData) data);
        } else if (data instanceof QueryEventData) {
            handleQuery(event, (QueryEventData) data);
        }
    }

    private void handleWrite(Event event, WriteRowsEventData data) {
        TableInfo tableInfo = metadataCache.get(data.getTableId());
        if (tableInfo == null) {
            log.warn("未找到 tableId={} 对应的元数据，忽略 INSERT 事件", data.getTableId());
            return;
        }
        List<String> sqlList = sqlBuilderService.buildInsertSql(tableInfo, data);
        sendMessage(event, tableInfo, "INSERT", sqlList);
    }

    private void handleUpdate(Event event, UpdateRowsEventData data) {
        TableInfo tableInfo = metadataCache.get(data.getTableId());
        if (tableInfo == null) {
            log.warn("未找到 tableId={} 对应的元数据，忽略 UPDATE 事件", data.getTableId());
            return;
        }
        List<String> sqlList = sqlBuilderService.buildUpdateSql(tableInfo, data);
        sendMessage(event, tableInfo, "UPDATE", sqlList);
    }

    private void handleDelete(Event event, DeleteRowsEventData data) {
        TableInfo tableInfo = metadataCache.get(data.getTableId());
        if (tableInfo == null) {
            log.warn("未找到 tableId={} 对应的元数据，忽略 DELETE 事件", data.getTableId());
            return;
        }
        List<String> sqlList = sqlBuilderService.buildDeleteSql(tableInfo, data);
        sendMessage(event, tableInfo, "DELETE", sqlList);
    }

    private void handleQuery(Event event, QueryEventData data) {
        String database = data.getDatabase();
        if (database == null) {
            database = "";
        }
        List<String> sqlList = sqlBuilderService.buildQuerySql(data);
        sendMessage(event, database, "", "QUERY", sqlList);
    }

    private void sendMessage(Event event,
                             TableInfo tableInfo,
                             String sqlType,
                             List<String> sqlList) {
        if (tableInfo == null) {
            return;
        }
        sendMessage(event, tableInfo.getDatabase(), tableInfo.getTable(), sqlType, sqlList);
    }

    private void sendMessage(Event event,
                             String database,
                             String table,
                             String sqlType,
                             List<String> sqlList) {
        if (sqlList == null || sqlList.isEmpty()) {
            return;
        }
        BinlogMessage message = new BinlogMessage();
        message.setDatabase(database);
        message.setTable(table);
        message.setSqlType(sqlType);
        message.setSqlList(sqlList);
        EventHeader header = event.getHeader();
        if (header instanceof EventHeaderV4) {
            EventHeaderV4 v4 = (EventHeaderV4) header;
            message.setTimestamp(v4.getTimestamp());
            message.setBinlogPos(v4.getNextPosition());
        } else {
            message.setBinlogPos(positionTracker.getCurrentPosition());
        }
        message.setBinlogFile(positionTracker.getCurrentFile());
        messageSender.send(message);
    }

}
