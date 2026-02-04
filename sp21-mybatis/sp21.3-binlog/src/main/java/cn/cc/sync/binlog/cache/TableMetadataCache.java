package cn.cc.sync.binlog.cache;

import cn.cc.sync.binlog.dto.TableInfo;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TableMetadataCache 负责把 tableId 映射到列元数据，避免在热路径上重复查询 information_schema。
 * mysql-binlog-connector 事件仅携带 tableId，因此这里需要按需懒加载并缓存。
 */
@Component
public class TableMetadataCache {

    private final JdbcTemplate jdbcTemplate;
    private final Map<Long, TableInfo> tableIdCache = new ConcurrentHashMap<>();
    private final Map<String, TableInfo> tableNameCache = new ConcurrentHashMap<>();

    public TableMetadataCache(@Qualifier("binlogMetadataJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void update(TableMapEventData mapData) {
        String cacheKey = cacheKey(mapData.getDatabase(), mapData.getTable());
        TableInfo tableInfo = tableNameCache.computeIfAbsent(cacheKey, key -> loadTableInfo(
                mapData.getDatabase(), mapData.getTable()));
        tableIdCache.put(mapData.getTableId(), tableInfo);
    }

    public TableInfo get(long tableId) {
        return tableIdCache.get(tableId);
    }

    private TableInfo loadTableInfo(String database, String table) {
        String sql = "SELECT COLUMN_NAME, COLUMN_TYPE, COLUMN_KEY, IS_NULLABLE "
                + "FROM information_schema.COLUMNS "
                + "WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ? "
                + "ORDER BY ORDINAL_POSITION";
        List<TableInfo.ColumnInfo> columns = jdbcTemplate.query(
                sql,
                ps -> {
                    ps.setString(1, database);
                    ps.setString(2, table);
                },
                (ResultSet rs, int rowNum) -> new TableInfo.ColumnInfo(
                        rs.getString("COLUMN_NAME"),
                        rs.getString("COLUMN_TYPE"),
                        "PRI".equalsIgnoreCase(rs.getString("COLUMN_KEY")),
                        "YES".equalsIgnoreCase(rs.getString("IS_NULLABLE"))
                )
        );
        if (columns == null || columns.isEmpty()) {
            throw new IllegalStateException("无法加载表结构：" + database + "." + table);
        }
        return new TableInfo(database, table, new ArrayList<>(columns));
    }

    private String cacheKey(String database, String table) {
        return database + "." + table;
    }
}

