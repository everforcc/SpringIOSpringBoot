package cn.cc.sync.binlog.util;

import cn.cc.sync.binlog.dto.TableInfo;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.QueryEventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
 * SQLBuilderService 根据 Binlog 行事件构造可重放的 SQL。
 * 这里不依赖 ORM，仅通过 TableInfo 提供的列定义来生成 INSERT/UPDATE/DELETE 文本。
 */
@Service
public class SQLBuilderService {

    public List<String> buildInsertSql(TableInfo tableInfo, WriteRowsEventData data) {
        List<String> sqlList = new ArrayList<>();
        for (Serializable[] row : data.getRows()) {
            List<String> columns = new ArrayList<>();
            List<String> values = new ArrayList<>();
            appendColumnsAndValues(tableInfo, data.getIncludedColumns(), row, columns, values);
            if (columns.isEmpty()) {
                continue;
            }
            sqlList.add(String.format(
                    "INSERT INTO `%s`.`%s` (%s) VALUES (%s);",
                    tableInfo.getDatabase(),
                    tableInfo.getTable(),
                    String.join(", ", columns),
                    String.join(", ", values)
            ));
        }
        return sqlList;
    }

    public List<String> buildUpdateSql(TableInfo tableInfo, UpdateRowsEventData data) {
        List<String> sqlList = new ArrayList<>();
        for (Map.Entry<Serializable[], Serializable[]> entry : data.getRows()) {
            List<String> setClauses = new ArrayList<>();
            List<String> whereClauses = new ArrayList<>();
            appendColumnsAndValues(
                    tableInfo,
                    data.getIncludedColumns(),
                    entry.getValue(),
                    setClauses,
                    null
            );
            appendWhereClauses(
                    tableInfo,
                    data.getIncludedColumnsBeforeUpdate(),
                    entry.getKey(),
                    whereClauses
            );
            if (setClauses.isEmpty() || whereClauses.isEmpty()) {
                continue;
            }
            sqlList.add(String.format(
                    "UPDATE `%s`.`%s` SET %s WHERE %s;",
                    tableInfo.getDatabase(),
                    tableInfo.getTable(),
                    String.join(", ", setClauses),
                    String.join(" AND ", whereClauses)
            ));
        }
        return sqlList;
    }

    public List<String> buildDeleteSql(TableInfo tableInfo, DeleteRowsEventData data) {
        List<String> sqlList = new ArrayList<>();
        for (Serializable[] row : data.getRows()) {
            List<String> whereClauses = new ArrayList<>();
            appendWhereClauses(tableInfo, data.getIncludedColumns(), row, whereClauses);
            if (whereClauses.isEmpty()) {
                continue;
            }
            sqlList.add(String.format(
                    "DELETE FROM `%s`.`%s` WHERE %s;",
                    tableInfo.getDatabase(),
                    tableInfo.getTable(),
                    String.join(" AND ", whereClauses)
            ));
        }
        return sqlList;
    }

    public List<String> buildQuerySql(QueryEventData data) {
        List<String> sqlList = new ArrayList<>();
        if (data.getSql() != null && !data.getSql().trim().isEmpty()) {
            sqlList.add(data.getSql().trim());
        }
        return sqlList;
    }

    private void appendColumnsAndValues(TableInfo tableInfo,
                                        BitSet includedColumns,
                                        Serializable[] row,
                                        List<String> columnClauses,
                                        List<String> valueClauses) {
        int valueIndex = 0;
        for (int columnIndex = 0; columnIndex < tableInfo.getColumns().size(); columnIndex++) {
            if (includedColumns != null && !includedColumns.get(columnIndex)) {
                continue;
            }
            TableInfo.ColumnInfo columnInfo = tableInfo.getColumn(columnIndex);
            Serializable value = valueIndex < row.length ? row[valueIndex] : null;
            if (columnClauses != null) {
                String formattedValue = formatValue(value, columnInfo.getDataType());
                if (valueClauses == null) {
                    columnClauses.add(String.format("`%s` = %s", columnInfo.getName(), formattedValue));
                } else {
                    columnClauses.add("`" + columnInfo.getName() + "`");
                    valueClauses.add(formattedValue);
                }
            }
            valueIndex++;
        }
    }

    private void appendWhereClauses(TableInfo tableInfo,
                                    BitSet includedColumns,
                                    Serializable[] row,
                                    List<String> whereClauses) {
        List<String> primaryKeyClauses = new ArrayList<>();
        int valueIndex = 0;
        for (int columnIndex = 0; columnIndex < tableInfo.getColumns().size(); columnIndex++) {
            if (includedColumns != null && !includedColumns.get(columnIndex)) {
                continue;
            }
            TableInfo.ColumnInfo columnInfo = tableInfo.getColumn(columnIndex);
            Serializable value = valueIndex < row.length ? row[valueIndex] : null;
            String expression = String.format("`%s` = %s",
                    columnInfo.getName(), formatValue(value, columnInfo.getDataType()));
            if (columnInfo.isPrimaryKey()) {
                primaryKeyClauses.add(expression);
            } else {
                whereClauses.add(expression);
            }
            valueIndex++;
        }
        if (!primaryKeyClauses.isEmpty()) {
            whereClauses.clear();
            whereClauses.addAll(primaryKeyClauses);
        }
    }

    private String formatValue(Object value, String mysqlType) {
        if (value == null) {
            return "NULL";
        }
        if (value instanceof byte[]) {
            byte[] bytes = (byte[]) value;
            return "0x" + bytesToHex(bytes);
        }
        if (value instanceof Boolean) {
            Boolean boolVal = (Boolean) value;
            return Boolean.TRUE.equals(boolVal) ? "1" : "0";
        }
        String lowerType = mysqlType == null ? "" : mysqlType.toLowerCase(Locale.ROOT);
        if (isNumericType(lowerType) && value instanceof Number) {
            return value.toString();
        }

        // 统一按本地时间/日期/时间类型格式输出，避免 Instant 默认转为 UTC ISO 字符串
        if (value instanceof java.util.Date) {
            java.util.Date date = (java.util.Date) value;
            String pattern = "yyyy-MM-dd HH:mm:ss";
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(pattern);
            return "'" + sdf.format(date) + "'";
        }
        if (value instanceof java.time.LocalDateTime) {
            java.time.LocalDateTime dt = (java.time.LocalDateTime) value;
            return "'" + dt.toString().replace('T', ' ') + "'";
        }
        if (value instanceof java.time.LocalDate) {
            java.time.LocalDate d = (java.time.LocalDate) value;
            return "'" + d.toString() + "'";
        }
        if (value instanceof java.time.LocalTime) {
            java.time.LocalTime t = (java.time.LocalTime) value;
            return "'" + t.toString() + "'";
        }
        return "'" + escapeString(value.toString()) + "'";
    }

    private boolean isNumericType(String mysqlType) {
        return mysqlType.contains("int") || mysqlType.contains("decimal")
                || mysqlType.contains("double") || mysqlType.contains("float")
                || mysqlType.contains("numeric") || mysqlType.contains("bit");
    }

    private String escapeString(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("'", "\\'")
                .replace("\r", "\\r")
                .replace("\n", "\\n")
                .replace("\t", "\\t");
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            sb.append(String.format("%02X", aByte));
        }
        return sb.toString();
    }
}

