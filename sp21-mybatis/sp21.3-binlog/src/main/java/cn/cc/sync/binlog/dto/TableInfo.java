package cn.cc.sync.binlog.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TableInfo 提供一个不可变的表结构视图，包含列顺序与主键信息，方便 SQLBuilder 复用。
 */
public class TableInfo {

    private final String database;
    private final String table;
    private final List<ColumnInfo> columns;
    private final List<ColumnInfo> primaryKeys;

    public TableInfo(String database, String table, List<ColumnInfo> columns) {
        this.database = database;
        this.table = table;
        this.columns = Collections.unmodifiableList(columns);
        this.primaryKeys = columns.stream()
                .filter(ColumnInfo::isPrimaryKey)
                .collect(Collectors.toList());
    }

    public String getDatabase() {
        return database;
    }

    public String getTable() {
        return table;
    }

    public List<ColumnInfo> getColumns() {
        return columns;
    }

    public List<ColumnInfo> getPrimaryKeys() {
        return primaryKeys;
    }

    public ColumnInfo getColumn(int index) {
        return columns.get(index);
    }

    public static class ColumnInfo {
        private final String name;
        private final String dataType;
        private final boolean primaryKey;
        private final boolean nullable;

        public ColumnInfo(String name, String dataType, boolean primaryKey, boolean nullable) {
            this.name = name;
            this.dataType = dataType;
            this.primaryKey = primaryKey;
            this.nullable = nullable;
        }

        public String getName() {
            return name;
        }

        public String getDataType() {
            return dataType;
        }

        public boolean isPrimaryKey() {
            return primaryKey;
        }

        public boolean isNullable() {
            return nullable;
        }
    }
}

