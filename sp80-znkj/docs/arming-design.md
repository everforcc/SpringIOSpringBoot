# 布防/撤防系统设计说明（sp80-znkj）

## 背景与目标
- 在海量设备事件上报场景下，实现“是否需要上报”的低延迟判定与按天撤防能力。
- 设备与布防组关系：一个设备仅归属一个布防组，一个布防组可包含多设备（含类型/型号维度）。
- 周期：按周循环；每天 24 小时，每 30 分钟一个槽位（共 48 个），每周 7 天共 336 槽。

## 设计思路（高层）
- 用“周位图 + 今日撤防掩码”实现按位快速判定：
  - 周位图（42 字节 = 7 × 48 位）：1 表示布防，0 表示撤防。
  - 今日撤防掩码（6 字节 = 48 位）：1 表示“今日额外撤防”。
  - 最终是否布防：`armed = weekBits[weekOffset] && !todayMask[daySlot]`。
- 数据读写路径优化：
  - 热路径仅依赖本地缓存命中时的内存计算；未命中退回 Redis 并回填本地缓存。
  - 配置或归属变更采用“写通”策略：先写 MySQL，再更新 Redis，并刷新本地缓存。
- 撤防仅影响“今天”：点击撤防生成当日掩码并设置 TTL 至午夜，午夜自然过期，第二天恢复周计划。

## 数据模型（MySQL）
见 `src/main/resources/db/arming_ddl.sql`：
- `device(id PK, group_id, type_id, model_id, name, updated_at)`
- `arming_group(id PK, name, status, updated_at)`
- `arming_group_schedule(group_id PK, week_bits VARBINARY(42), updated_at)`

说明：
- `week_bits` 以周一到周日为序，每天 48 槽，字节序与 `java.util.BitSet` 的 `toByteArray()`/`valueOf()` 保持一致。

## Redis 键设计
- 设备归属映射（Hash）：`dev:gid`，field=`deviceId`，value=`groupId`
- 组周位图（String，42 字节）：`arm:sch:g:{groupId}`
- 今日撤防掩码（String，6 字节，TTL 到当天 24:00）：`arm:mask:today:g:{groupId}`

## 判定算法与时间槽
- 当天槽位：`daySlot = hour * 2 + (minute >= 30 ? 1 : 0)` ∈ [0,47]
- 周内日索引：`weekDayIndex = (Mon=1..Sun=7) % 7` ∈ [0,6]
- 周偏移：`weekOffset = weekDayIndex * 48 + daySlot` ∈ [0,335]
- 判定：`armed = weekBits[weekOffset] && !todayMask[daySlot]`

## 撤防规则（按天）
- 当前在布防段：撤防“包含当前槽位的连续布防区间” `[L, R]`；
- 当前在撤防段：撤防“今天的下一个连续布防区间” `[L, R]`；
- 掩码合并策略：多次撤防做并集（OR），TTL 统一设为“距午夜剩余时长”。

### 变更：不能在同一布防时间段内连续撤防
- 规则：一次撤防只影响一个“连续布防区间”；如果再次点击撤防仍处于同一连续布防区间，则不生效；
- 实现：计算出的新掩码 `mask` 与已有 `existedMask` 做差集（`toAdd = mask AND NOT existedMask`），若差集为空则认为无效；否则合并（OR）并持久化。

### Redis 宕机后的恢复方案
- 风险：若撤防后 Redis 宕机导致掩码和“已使用”标记丢失，重启后今日掩码消失；
- 方案：新增表 `arming_group_today_mask(group_id, biz_date, mask_bits)` 将当日掩码持久化；
- 恢复：加载今日掩码时，若 Redis 不存在，则从 DB 读取并回写 Redis（设置 TTL 至午夜）。

对应示例（0-8 布防、8-12 撤防、12-14 布防、14-18 撤防、18-24 布防）：
- 0-8 点击撤防 → 当日掩码置 0-8；
- 8-12 点击撤防 → 当日掩码置 12-14；
- 12-14 点击撤防 → 当日掩码置 12-14；
- 14-18 点击撤防 → 当日掩码置 18-24；
- 18-24 点击撤防 → 当日掩码置 18-24。

## 接口约定（示例）
- 判定是否上报：`GET /arming/shouldReport/{deviceId}` → `{ deviceId, shouldReport }`
- 今日撤防：`POST /arming/defuse/{groupId}`
- 写通设备归属：`POST /arming/upsertDeviceGroup?deviceId=..&groupId=..`
- 写通组周位图：`POST /arming/upsertGroupSchedule/{groupId}`，Body：`{ "weekBits": BASE64 }`

## 运行与初始化
- 数据源与 Redis 在 `application.yml` 配置；
- 执行 `src/main/resources/db/arming_ddl.sql` 建表并插入简单测试数据；
- 通过接口写入周位图与设备归属后进行判定测试。

## 测试建议
- 判定正确性：覆盖 00:00、23:30、段首/段尾、跨段切换等边界；
- 性能：在高 QPS 下观察本地命中率、Redis RTT 与 GC 指标；
- 容灾：Redis 不可用时的降级策略（本地缓存命中 vs 未命中）；
- 一致性：巡检任务定期比对 MySQL 与 Redis 数据；

## 扩展方向
- 日期特例（节假日/临时策略）：引入“日期特例表”，优先级高于周位图与掩码；
- 组内差异化：为类型/型号增加覆盖位图，分层合并；
- 预计算当前槽：每 30 分钟刷新 `groupActive[gid]`，将判定退化为一次数组访问。

## 代码风格与注释约定
- 注释写全：类注释描述职责与上下文，方法注释描述输入/输出与关键约束；
- 不省略花括号；优先早返回避免深嵌套；
- 避免 TODO 注释，尽量实现或形成明确工作项。

## 为什么 Redis 配置要这样写（@Primary 的原因）
- 现象：Spring Boot 会自动装配一个 `stringRedisTemplate`（类型为 `StringRedisTemplate`，继承自 `RedisTemplate<String, String>`）。我们同时自定义了一个 `RedisTemplate<String, String>`（方法名 `redisStringTemplate`），当服务中按类型注入 `RedisTemplate<String, String>` 时，Spring 会发现两个候选 Bean，导致歧义并报错（你看到的错误：found 2 beans）。
- 处理：为自定义的 `redisStringTemplate` 添加 `@Primary`，当注入按类型匹配到多个候选时，Spring 优先选择被标记为 `@Primary` 的那个。
- 为什么选择这样做：
  - 项目中同时需要一个二进制模板 `RedisTemplate<String, byte[]>`（用于周位图/掩码）与一个字符串模板；我们希望统一管理序列化器（key/hashKey/hashValue 都是 String 序列化），并避免在每个使用点都写 `@Qualifier` 增加心智负担。
  - Spring Boot 自带的 `StringRedisTemplate` 虽然也满足字符串场景，但与我们自定义模板在泛型与序列化策略约定上有细微差异；将自定义模板设为 `@Primary` 能保证服务层注入时行为一致。
- 等价替代方案：
  - 在使用处显式指定 `@Qualifier("redisStringTemplate")` 或直接注入 `StringRedisTemplate` 类型；
  - 不使用 `@Primary`，但所有用到字符串模板的构造器参数都改为 `@Qualifier`（维护成本较高，不推荐）。
- 注意：`RedisTemplate<String, byte[]>` 不会与 Spring Boot 的默认 Bean 冲突，因为系统不会自动提供该泛型的模板；它专门用于二进制位图存取。
