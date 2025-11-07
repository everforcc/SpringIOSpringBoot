### @DS 切库“不生效”排查记录（已定位为 MyBatis 缓存导致）

- 现象：Service 方法上 `@DS` 日志显示已切换到 `yq138`/`yq136`，但第二次查询未打印 SQL，看起来像“仍然查第一次的库”。
- 根因：`ZnPayOrderRecordMapper.xml` 启用了二级缓存 `<cache .../>`，MyBatis 二级缓存 key 仅由 `MappedStatementId + 参数` 组成，不感知动态数据源。第一次在 `yq138` 查询后，第二次切到 `yq136` 命中缓存，导致不发 SQL，也无法触发 `yq136` 上缺列报错（或数据差异）。
- 证据：日志出现 `Cache Hit Ratio [cn.cc.huifu.refund.mapper.ZnPayOrderRecordMapper]`，且第二次无 `Preparing:`；关闭缓存或设置 `useCache=false` 后，两次均打印 `Preparing:` 且在 `yq136` 抛出 `Unknown column 'e.auth_type'`。

修复/规避方案：
- 在本命名空间禁用缓存：删除 `<cache/>`，或在查询上设置 `useCache="false"`，或为该命名空间配置不使用二级缓存。
- 仅测试阶段可全局关闭：`mybatis.configuration.cache-enabled: false`。
- 理论可行但不推荐：自定义 CacheKey 将数据源 key（如 `DynamicDataSourceContextHolder.peek()`）纳入缓存 key，以实现“按库隔离缓存”。实现成本高、侵入性强。

验证要点：
- 打开 `com.baomidou.dynamic.datasource` DEBUG，确认每次方法进入的 `lookupKey`；
- 在 Service 打印 `DynamicDataSourceContextHolder.peek()`；
- 打印 SQL（StdOutImpl）并观察两次均有 `Preparing:`；
- 必要时在 SQL 临时 `SELECT DATABASE()` 验库。

其它易混淆点（本次未触发，但需注意）：
- `@DS` 必须标在实现类的 public 方法且需“走代理”，同类内部自调用无效；
- 事务边界：外层已开启事务后再切库不生效，应保证切库与事务开启在同一方法上；
- 注解不要只标在接口方法上。

本次结论：
- 切库功能正常；问题由 MyBatis 二级缓存跨数据源命中造成。已将 Mapper SQL 合并为单方法，并对该查询禁用缓存，验证通过，`yq136` 按预期报 `Unknown column 'e.auth_type'`。