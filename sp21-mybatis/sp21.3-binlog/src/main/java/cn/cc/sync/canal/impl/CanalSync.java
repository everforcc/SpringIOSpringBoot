package cn.cc.sync.canal.impl;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class CanalSync {

    private final static int BATCH_SIZE = 10;

    Map<String, List<String>> primaryKeyCache = new ConcurrentHashMap<>();

    @Async
    public void flow() throws Exception {
        // 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("127.0.0.1", 11111), "example", "", "");
        try {
            //打开连接
            connector.connect();
            //订阅数据库表,全部表
            connector.subscribe(".*\\..*");
            //回滚到未进行ack的地方，下次fetch的时候，可以从最后一个没有ack的地方开始拿
            connector.rollback();
            // todo 这里可以修改为定时任务或者监听之类的，先这样
            while (true) {
                // 获取指定数量的数据
                Message message = connector.getWithoutAck(BATCH_SIZE);
                //获取批量ID
                long batchId = message.getId();
                //获取批量的数量
                int size = message.getEntries().size();
                //如果没有数据
                if (batchId == -1 || size == 0) {
                    try {
                        //线程休眠2秒
                        Thread.sleep(2000);
                        log.info("没有数据,线程休眠2秒...");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    log.info("开始处理数据...");
                    //如果有数据,处理数据
                    printEntry(message.getEntries());
                }
                //进行 batch id 的确认。确认之后，小于等于此 batchId 的 Message 都会被确认。
                connector.ack(batchId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connector.disconnect();
        }
    }

    /**
     * 打印canal server解析binlog获得的实体类信息
     */
    private static void printEntry(List<CanalEntry.Entry> entrys) {
        for (CanalEntry.Entry entry : entrys) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                //开启/关闭事务的实体类型，跳过
                continue;
            }
            //RowChange对象，包含了一行数据变化的所有特征
            //比如isDdl 是否是ddl变更操作 sql 具体的ddl sql beforeColumns afterColumns 变更前后的数据字段等等
            CanalEntry.RowChange rowChage;
            try {
                rowChage = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(), e);
            }
            //获取操作类型：insert/update/delete类型
            CanalEntry.EventType eventType = rowChage.getEventType();
            //打印Header信息
            log.info("================》; binlog[{}}:{}] , name[{},{}] , eventType : {}",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType);
            //判断是否是DDL语句
            if (rowChage.getIsDdl()) {
                log.info("================》;isDdl: true,sql: {}", rowChage.getSql());
            }
            //获取RowChange对象里的每一行数据，打印出来
            for (CanalEntry.RowData rowData : rowChage.getRowDatasList()) {
                //如果是删除语句
                if (eventType == CanalEntry.EventType.DELETE) {
                    printColumn(rowData.getBeforeColumnsList());
                    List<String> sqlList = buildDeleteSQL(entry.getHeader().getSchemaName(), entry.getHeader().getTableName(), rowChage);
                    for (String sql : sqlList) {
                        log.info("================》; delete sql: {}", sql);
                    }
                    //如果是新增语句
                } else if (eventType == CanalEntry.EventType.INSERT) {
                    printColumn(rowData.getAfterColumnsList());
                    List<String> sqlList = buildInsertSQL(entry.getHeader().getSchemaName(), entry.getHeader().getTableName(), rowChage);
                    for (String sql : sqlList) {
                        log.info("================》; insert sql: {}", sql);
                    }
                    //如果是更新的语句
                } else if (eventType == CanalEntry.EventType.UPDATE) {
                    //变更前的数据
                    log.info("------->; before");
                    printColumn(rowData.getBeforeColumnsList());
                    //变更后的数据
                    log.info("------->; after");
                    printColumn(rowData.getAfterColumnsList());
                    List<String> updateSQLList = buildUpdateSQL(entry.getHeader().getSchemaName(), entry.getHeader().getTableName(), rowChage);
                    for (String sql : updateSQLList) {
                        log.info("================》; update sql: {}", sql);
                    }
                }
            }
        }
    }

    private static void printColumn(List<CanalEntry.Column> columns) {
        for (CanalEntry.Column column : columns) {
            log.info("变更字段 {} : {} update= {}, 是否是主键: {}", column.getName(), column.getValue(), column.getUpdated(), column.getIsKey());
//            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }

    /**
     * 构建 INSERT SQL
     */
    private static List<String> buildInsertSQL(String database, String table, CanalEntry.RowChange rowChange) {
        List<String> sqlList = new ArrayList<>();

        for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
            List<String> columns = new ArrayList<>();
            List<String> values = new ArrayList<>();

            // 获取所有列的值（INSERT 只有 afterColumns）
            for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
                columns.add(escapeColumn(column.getName()));
                values.add(formatValue(column.getValue(), column.getMysqlType()));
            }

            String sql = String.format(
                    "INSERT INTO `%s`.`%s` (%s) VALUES (%s);",
                    database,
                    table,
                    String.join(", ", columns),
                    String.join(", ", values)
            );

            sqlList.add(sql);
        }

        return sqlList;
    }

    /**
     * 构建 UPDATE SQL
     */
    private static List<String> buildUpdateSQL(String database, String table, CanalEntry.RowChange rowChange) {
        List<String> sqlList = new ArrayList<>();

        for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
            List<String> setClauses = new ArrayList<>();
            List<String> whereClauses = new ArrayList<>();

            // 构建 SET 子句（使用 afterColumns）
            for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
                if (column.getUpdated()) {
                    setClauses.add(String.format(
                            "%s = %s",
                            escapeColumn(column.getName()),
                            formatValue(column.getValue(), column.getMysqlType())
                    ));
                }
            }

//            // 构建 WHERE 子句（使用 beforeColumns，优先使用主键）
//            List<String> primaryKeys = getPrimaryKeys(database, table);
            boolean hasPrimaryKey = false;

            for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
                if (column.getIsKey()) {
                    whereClauses.add(String.format(
                            "%s = %s",
                            escapeColumn(column.getName()),
                            formatValue(column.getValue(), column.getMysqlType())
                    ));
                    hasPrimaryKey = true;
                }
            }

            // 如果没有主键，使用所有列作为 WHERE 条件
            if (!hasPrimaryKey) {
                for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
                    whereClauses.add(String.format(
                            "%s = %s",
                            escapeColumn(column.getName()),
                            formatValue(column.getValue(), column.getMysqlType())
                    ));
                }
            }

            String sql = String.format(
                    "UPDATE `%s`.`%s` SET %s WHERE %s;",
                    database,
                    table,
                    String.join(", ", setClauses),
                    String.join(" AND ", whereClauses)
            );

            sqlList.add(sql);
        }

        return sqlList;
    }

    /**
     * 构建 DELETE SQL
     */
    private static List<String> buildDeleteSQL(String database, String table, CanalEntry.RowChange rowChange) {
        List<String> sqlList = new ArrayList<>();

        for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
            List<String> whereClauses = new ArrayList<>();

//            // DELETE 使用 beforeColumns
//            List<String> primaryKeys = getPrimaryKeys(database, table);
            boolean hasPrimaryKey = false;

            for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
                if (column.getIsKey()) {
                    whereClauses.add(String.format(
                            "%s = %s",
                            escapeColumn(column.getName()),
                            formatValue(column.getValue(), column.getMysqlType())
                    ));
                    hasPrimaryKey = true;
                }
            }

            // 如果没有主键，使用所有列作为 WHERE 条件
            if (!hasPrimaryKey) {
                for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
                    whereClauses.add(String.format(
                            "%s = %s",
                            escapeColumn(column.getName()),
                            formatValue(column.getValue(), column.getMysqlType())
                    ));
                }
            }

            String sql = String.format(
                    "DELETE FROM `%s`.`%s` WHERE %s;",
                    database,
                    table,
                    String.join(" AND ", whereClauses)
            );

            sqlList.add(sql);
        }

        return sqlList;
    }

    /**
     * 转义列名
     */
    private static String escapeColumn(String columnName) {
        return "`" + columnName + "`";
    }

    /**
     * 格式化值（处理 NULL、字符串转义等）
     */
    private static String formatValue(String value, String mysqlType) {
        if (value == null || "NULL".equalsIgnoreCase(value)) {
            return "NULL";
        }

        // 字符串类型需要转义和加引号
        if (isStringType(mysqlType)) {
            return "'" + escapeString(value) + "'";
        }

        // 数值类型直接返回
        if (isNumericType(mysqlType)) {
            return value;
        }

        // 日期时间类型
        if (isDateTimeType(mysqlType)) {
            if (StringUtils.isEmpty(value)) {
                return "null";
            }
            return "'" + value + "'";
        }

        // 二进制类型（BLOB、BINARY等）
        if (isBinaryType(mysqlType)) {
            // 将字节数组转换为十六进制字符串
            return "0x" + bytesToHex(value.getBytes());
        }

        // 默认按字符串处理
        return "'" + escapeString(value) + "'";
    }

    // 类型判断辅助方法
    private static boolean isStringType(String mysqlType) {
        return mysqlType.contains("char") || mysqlType.contains("text") ||
                mysqlType.contains("enum") || mysqlType.contains("set");
    }

    private static boolean isNumericType(String mysqlType) {
        return mysqlType.contains("int") || mysqlType.contains("decimal") ||
                mysqlType.contains("float") || mysqlType.contains("double") ||
                mysqlType.contains("numeric");
    }

    private static boolean isDateTimeType(String mysqlType) {
        return mysqlType.contains("date") || mysqlType.contains("time") ||
                mysqlType.contains("year") || mysqlType.contains("datetime");
    }

    private static boolean isBinaryType(String mysqlType) {
        return mysqlType.contains("blob") || mysqlType.contains("binary") ||
                mysqlType.contains("varbinary");
    }

    /**
     * 转义字符串（防止 SQL 注入）
     */
    private static String escapeString(String value) {
        return value.replace("\\", "\\\\")
                .replace("'", "\\'")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    /**
     * 将字节数组转换为十六进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString().toUpperCase();
    }

    /**
     * 获取表的主键列表（需要缓存或查询元数据）
     */
//    private List<String> getPrimaryKeys(String database, String table) {
//        String cacheKey = database + "." + table;
//        // 优先从缓存读取，避免频繁查询 information_schema
//        List<String> primaryKeys = primaryKeyCache.get(cacheKey);
//        if (primaryKeys != null) {
//            return primaryKeys;
//        }
//        // 缓存未命中时，实时查询 information_schema 并写入缓存
//        primaryKeys = fetchPrimaryKeysFromMetadata(database, table);
//        primaryKeyCache.put(cacheKey, primaryKeys);
//        return primaryKeys;
//    }
//
//    /**
//     * 如果没有缓存，则直接查询 information_schema.COLUMNS 获取主键信息
//     */
//    private List<String> fetchPrimaryKeysFromMetadata(String database, String table) {
//        String sql = "SELECT COLUMN_NAME FROM information_schema.COLUMNS "
//                + "WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ? AND COLUMN_KEY = 'PRI'";
//        List<String> hosts = Arrays.asList("192.168.0.30", "192.168.0.5");
////        String sql = "insert into gen_table(table_id,table_name) " +
////                "values(?, ?);";
//        Map<String, JDBCUtils.ExecutionResult> result =
//                JDBCUtils.executeOnHosts(hosts, "bmclocal", 3306, "root", "znkj123456", sql,
//                        (host, ps) -> {
//                            try {
//                                ps.setLong(1, 5L);
//                                ps.setString(2, "JDBCUtilsTest");
//                            } catch (SQLException e) {
//                                e.printStackTrace();
//                            }
//                        });
//        result.forEach((host, result1) -> {
//            System.out.println("执行结果：" + result1);
//        });
//
//
//
//        return jdbcTemplate.query(sql, new Object[]{database, table},
//                (rs, rowNum) -> rs.getString("COLUMN_NAME"));
//    }

}
