## Binlog 中 BEGIN / QUERY / 回滚 与 `binlog_message` 表的关系说明

### 1. `binlog_message` 表关键字段含义

- **`sql_type`**
  - 表示当前 Binlog 事件被归类的“类型标签”。
  - 行级 DML：`INSERT` / `UPDATE` / `DELETE`。
  - 原始 SQL 事件（`QueryEvent`）：统一标记为 `QUERY`。

- **`sql_list_json`**
  - 存储当前事件对应的 **SQL 文本列表**，是一个 JSON 数组字符串。
  - 反序列化后是 `List<String>`，例如：
    - `["BEGIN"]`：当前事件只有一条 SQL，为 `BEGIN`；
    - `["ALTER TABLE ..."]`：当前事件是一条 DDL；
    - `["UPDATE ...", "INSERT ..."]`：理论上也可以是多条。

示例：

```sql
INSERT INTO oneforall.binlog_message
  (id, database_name, table_name, sql_type, sql_list_json, binlog_file, binlog_pos, event_time, created_at)
VALUES
  (50, 'fuint_food', '', 'QUERY', '["BEGIN"]',
   'DESKTOP-3CK8IIS-bin.000531', 3014, 1765871048000, '2025-12-16 07:44:09');
```

解释：
- `sql_type = 'QUERY'`：说明这是一个 **QueryEvent**（原始 SQL 事件）。
- `sql_list_json = '["BEGIN"]'`：说明这条事件中只有一条 SQL 文本 —— `BEGIN`，即事务开始。

> 结论：**`"QUERY"` 是事件类别，`'["BEGIN"]'` 是这条事件内真实 SQL 内容的列表。**

---

### 2. 为什么能看到 `BEGIN`，却看不到“结束标志”？

在 MySQL 中，一个典型的显式事务可能是：

```sql
BEGIN;
UPDATE ...;
INSERT ...;
COMMIT;  -- 或 ROLLBACK;
```

在 binlog（ROW 模式）视角下，大致会有：

1. **`BEGIN`**：
   - 以 **QueryEvent** 形式出现，SQL = `"BEGIN"`；
   - 在当前实现中，会被转换为：
     - `sql_type = 'QUERY'`；
     - `sql_list_json = '["BEGIN"]'`。

2. **中间的 INSERT/UPDATE/DELETE**：
   - 以 **Row Event** 形式出现：`WriteRowsEventData` / `UpdateRowsEventData` / `DeleteRowsEventData`；
   - 由 `SQLBuilderService` 转成对应的 SQL 文本，保存为：
     - `sql_type = 'INSERT'/'UPDATE'/'DELETE'`；
     - `sql_list_json = '["INSERT INTO ..."]'` 等。

3. **事务结束（COMMIT/ROLLBACK）**：
   - 在 ROW binlog 模式下，事务提交通常对应 **XID 事件**，不一定以 `COMMIT` 的 SQL 文本形式出现；
   - 当前实现里：
     - 并没有把 XID/COMMIT/ROLLBACK 映射为 `["COMMIT"]` 或 `["ROLLBACK"]` 这样的 SQL 文本并落库；
     - 因此在 `binlog_message` 表中，只能直观看到 `["BEGIN"]`，而看不到对称的 `["COMMIT"]` 记录。

> 事务真正的“结束”是由 MySQL 自己在 binlog 中通过 XID 等内部事件表达的，当前业务代码只关心行级 DML 和部分 QueryEvent，不对提交/回滚再生成额外 SQL 记录。

---

### 3. 异常回滚时，DML 会不会被保存？

**结论：不会。**

原因：

- 在 MySQL 的 binlog 机制中（ROW 模式）：
  - **只有最终成功提交的事务，才会把对应的行事件写入 binlog**；
  - 如果一个事务最终执行了 `ROLLBACK`，该事务中所有 DML 对应的行事件都不会写入 binlog。

- 我们的整体链路是：

  ```
  MySQL Binlog → BinaryLogClient → BinlogEventProcessor → SQLBuilderService → BinlogMessageSender → binlog_message 表
  ```

  因此：
  - 只有写入 binlog 的事件，`BinaryLogClient` 才能收到；
  - 没有进 binlog 的事务（回滚掉的 DML），我们根本拿不到，自然也不会保存到 `binlog_message` 表。

---

### 4. 自动提交（autocommit）情况下的行为

- 当 `autocommit = 1`（MySQL 默认）：
  - 每一条 DML 语句都相当于一个“单条语句事务”；
  - 语句执行 **成功**：对应的 Row 事件会被立即写入 binlog，进而被我们解析并持久化；
  - 语句执行 **失败**（例如违反约束、SQL 语法错误）：不会写入 binlog，我们也不会保存任何记录。

> 可以理解为：`binlog_message` 中的 DML 记录，都是“已经在源库成功执行并提交”的操作。

---

### 5. 如果想显式记录事务结束（COMMIT/ROLLBACK）怎么办？

当前实现只明确记录了：

- `["BEGIN"]` 这样的事务开始事件（作为 QueryEvent）；
- 以及后续成功提交的行级 DML 对应的 SQL。

如果业务上需要在 `binlog_message` 中 **成对看到 BEGIN / COMMIT（或 ROLLBACK）**，可以扩展以下能力：

1. 在 `BinlogEventProcessor` 中：
   - 监听 mysql-binlog-connector 暴露的 XID 或 COMMIT/ROLLBACK 相关事件；
   - 额外构造一条 `BinlogMessage`：
     - `sql_type = 'QUERY'`；
     - `sql_list_json = '["COMMIT"]'` 或 `["ROLLBACK"]`；
   - 这样在 `binlog_message` 表中，BEGIN 与 COMMIT/ROLLBACK 就可以形成更清晰的事务边界记录。

2. 或者只在内存/业务逻辑中根据事件顺序管理事务边界，而不必在表中持久化 COMMIT/ROLLBACK 的“伪 SQL”。

---

### 6. 总结

1. `sql_type = 'QUERY'` 表示这是原始 SQL 类型的 Binlog 事件，`sql_list_json` 是其中的 SQL 文本数组，例如 `["BEGIN"]`。
2. `["BEGIN"]` 代表“事务开始”的 QueryEvent；事务结束通常由 XID/COMMIT 表达，当前实现未将其保存为 SQL 文本记录。
3. **回滚掉的事务不会出现在 binlog 中**，因此所有被回滚的 DML 也不会出现在 `binlog_message` 表。
4. `binlog_message` 中的 DML（INSERT/UPDATE/DELETE）都可以认为是 **源库已经成功提交的操作**。
5. 如需更完整的事务轨迹，可在 `BinlogEventProcessor` 里扩展对 XID/COMMIT/ROLLBACK 的捕获与记录逻辑。


