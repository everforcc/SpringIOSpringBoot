## 常见 Binlog 事件类型说明（结合当前项目）

> 说明：事件名称基于 MySQL 官方 Binlog 事件类型，右侧说明结合 `mysql-binlog-connector-java` 和本项目（`sp80-znkj` 中的 `cn.cc.sync.binlog` 包）来描述。

### 1. 与本项目强相关的事件

| 事件类型（官方） | mysql-binlog-connector 对应 Data 类 | 当前项目是否处理 | 在项目中的作用 / 说明 |
|------------------|--------------------------------------|------------------|------------------------|
| `TABLE_MAP_EVENT` | `TableMapEventData` | ✅ 是 | `BinlogEventProcessor` 收到后调用 `TableMetadataCache.update(mapData)`，根据 `database.table` 懒加载列信息并缓存，将内部 `tableId` 映射到 `TableInfo`。后续所有行级 DML（INSERT/UPDATE/DELETE）都依赖这一步来获取表结构。 |
| `WRITE_ROWS_EVENT[_V1/_V2]` | `WriteRowsEventData` | ✅ 是 | 表示 **INSERT 行事件**。`BinlogEventProcessor.handleWrite` 使用 `SQLBuilderService.buildInsertSql` 把行数据拼成 `INSERT` SQL，落库时 `sql_type = "INSERT"`，`sql_list_json` 为 SQL 列表。 |
| `UPDATE_ROWS_EVENT[_V1/_V2]` | `UpdateRowsEventData` | ✅ 是 | 表示 **UPDATE 行事件**。`BinlogEventProcessor.handleUpdate` 使用 `SQLBuilderService.buildUpdateSql` 生成 `UPDATE` SQL，落库时 `sql_type = "UPDATE"`。 |
| `DELETE_ROWS_EVENT[_V1/_V2]` | `DeleteRowsEventData` | ✅ 是 | 表示 **DELETE 行事件**。`BinlogEventProcessor.handleDelete` 使用 `SQLBuilderService.buildDeleteSql` 生成 `DELETE` SQL，落库时 `sql_type = "DELETE"`。 |
| `QUERY_EVENT` | `QueryEventData` | ✅ 是 | 表示 **原始 SQL 事件**（包含 SQL 文本）。`BinlogEventProcessor.handleQuery` 调用 `SQLBuilderService.buildQuerySql`，最终落库为 `sql_type = "QUERY"`，`sql_list_json` 是 SQL 文本数组。例如：`["BEGIN"]` 表示事务开始；DDL 如 `["ALTER TABLE ..."]` 也会走这里。 |
| `ROTATE_EVENT` | `RotateEventData` | ⚠️ 间接相关 | 表示 binlog 文件滚动（切换文件）。`BinlogPositionTracker` 会跟踪位点/文件名，`BinlogMessage` 里保存 `binlog_file` 和 `binlog_pos`，用于恢复/回放。当前代码主要是通过 `positionTracker.track(event)` 间接感知。 |
| `XID_EVENT` | `XidEventData` | ⚠️ 仅位点相关 | 表示 ROW 模式下的 **事务提交**。当前项目主要用于位点跟踪（通过 `BinlogPositionTracker`），**没有将其转换为 `["COMMIT"]` 这样的 SQL 记录到 `binlog_message`**。 |

---

### 2. 事务相关行为小结

| 场景 | 是否写入 Binlog | 本项目是否能看到 | `binlog_message` 中会出现什么 |
|------|------------------|------------------|--------------------------------|
| 显式 `BEGIN` | 一般以 `QUERY_EVENT("BEGIN")` 写入 | ✅ 能看到 | 作为 `sql_type = "QUERY"`，`sql_list_json = '["BEGIN"]'` 的记录。 |
| 中间的 INSERT/UPDATE/DELETE 且事务 **最终 COMMIT** | ✅ 会写入（ROW 模式为行事件） | ✅ 能看到 | 转换为 `INSERT` / `UPDATE` / `DELETE` 类型记录，SQL 在 `sql_list_json` 中。 |
| 事务 **ROLLBACK**（回滚） | ❌ 行级 DML 不写入 Binlog | ❌ 看不到 | 对应 DML 不会出现在 `binlog_message`，最多只可能出现一个 `["ROLLBACK"]` 的 QueryEvent（当前未特别处理）。 |
| 自动提交 `autocommit=1` 的单条 DML 且执行成功 | ✅ 写入 | ✅ 能看到 | 每条 DML 变成一条对应的 INSERT/UPDATE/DELETE 记录。 |
| 自动提交 `autocommit=1` 的单条 DML 执行失败 | ❌ 不写入 | ❌ 看不到 | 不会出现在 `binlog_message`。 |

---

### 3. 其他常见 Binlog 事件类型（当前项目基本未直接使用）

| 事件类型（官方） | 说明 | 当前项目处理方式 |
|------------------|------|------------------|
| `FORMAT_DESCRIPTION_EVENT` | 描述 binlog 文件格式和版本的头信息，通常出现在每个 binlog 文件开头。 | 由 mysql-binlog-connector 内部处理；项目中不做显式业务逻辑，仅作为底层支持。 |
| `STOP_EVENT` | 表示 MySQL 服务器关闭 binlog。 | 由 mysql-binlog-connector 处理；项目通常只从连接断开/异常中感知。 |
| `INTVAR_EVENT` | 用于记录自增 ID 值（`LAST_INSERT_ID`）等，以便在 STATEMENT 模式下保证复制一致性。 | 当前项目未对其做显式处理，因为主要采用 ROW 行事件重建 SQL。 |
| `RAND_EVENT` | 记录随机数种子，用于在 statement 复制中保证 `RAND()` 一致性。 | 未显式处理。 |
| `USER_VAR_EVENT` | 记录用户自定义变量变化。 | 未显式处理。 |
| `GTID_EVENT` | GTID 模式下，表示一个事务 GTID 的开始。 | 当前代码未显式使用 GTID 信息，仅基于文件名 + position 做位点追踪。 |

---

### 4. 与当前项目的映射关系总结

1. **行级 DML 事件（WRITE/UPDATE/DELETE_ROWS）**  
   - 由 `BinlogEventProcessor` 分发到 `SQLBuilderService`，生成可重放的 `INSERT/UPDATE/DELETE` SQL；  
   - `BinlogMessageSender` 将其写入内存 Map 与 `binlog_message` 表。

2. **原始 SQL 事件（QUERY_EVENT）**  
   - 统一按 `sql_type = "QUERY"` 落库，`sql_list_json` 保存 Binlog 中携带的 SQL 文本列表；  
   - 包括事务开始 `BEGIN`、DDL 等。

3. **事务提交（XID_EVENT）与回滚（ROLLBACK）**  
   - XID 事件主要被用于位点追踪，当前项目并未将其转换为 SQL 文本插入 `binlog_message`；  
   - 只要事务最终回滚，其 DML 行事件不会写入 Binlog，因此也不会出现在 `binlog_message` 中。

4. **表结构映射（TABLE_MAP_EVENT）**  
   - 是行级事件的前置：所有行事件都只带 `tableId`；  
   - 项目通过 `TableMetadataCache` + `information_schema.COLUMNS` 解析表结构，用于后续 SQL 拼装。

> 可以简单记住：
> - **真正会被我们转换成 SQL 并保存的，主要是：`TABLE_MAP` + 行级 DML + 部分 `QUERY_EVENT`（如 BEGIN / DDL）**；  
> - 事务提交/回滚更多是由 MySQL/connector 保证一致性，当前实现只在位点层面感知，不会生成额外 SQL 记录。 |


