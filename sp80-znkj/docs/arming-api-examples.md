# 布防/撤防 API 测试示例

## 1. 初始化测试数据（SQL）
见 `src/main/resources/db/arming_ddl.sql`，已包含：
- 组：`arming_group(id=1, name=默认组)`
- 设备：`device(id=10001, group_id=1)`
- 周位图：初始化为全 0（全撤防）

## 2. 写入周位图（42 字节）
- 路径：`POST /arming/upsertGroupSchedule/1`
- 请求体（JSON）：
  - 字段 `weekBits` 传 BASE64。示例将“周一 00:00-08:00 布防、12:00-14:00 布防、18:00-24:00 布防，其余撤防”写入，仅演示周一，其余天保持 0。

示例（curl）：
```bash
# 生成 42 字节：这里示例用全 0；请按业务构造 336 位的 BASE64
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{"weekBits":"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA="}' \
  http://localhost:8888/arming/upsertGroupSchedule/1
```

> 注意：`AAAAAAAA...` 是 42 字节全 0 的 BASE64（仅示例）。生产中需构造对应 336 位位图。

## 3. 写入设备归属
- 路径：`POST /arming/upsertDeviceGroup?deviceId=10001&groupId=1`

示例（curl）：
```bash
curl -X POST "http://localhost:8888/arming/upsertDeviceGroup?deviceId=10001&groupId=1"
```

## 4. 判定是否上报
- 路径：`GET /arming/shouldReport/10001`

示例（curl）：
```bash
curl -X GET "http://localhost:8888/arming/shouldReport/10001"
```
响应示例：
```json
{
  "deviceId": 10001,
  "shouldReport": true
}
```

## 5. 今日撤防（根据当前时间自动计算区间）
- 路径：`POST /arming/defuse/1`

示例（curl）：
```bash
curl -X POST "http://localhost:8888/arming/defuse/1"
```
响应示例：
```json
{
  "groupId": 1,
  "status": "OK",
  "effective": true
}
```

说明：
- 不能在同一布防时间段内连续撤防：同一连续布防区间内重复点击将返回 `effective=false`；
- 若在撤防区间点击，则按规则会撤防“今天的下一个连续布防区间”。

## 6. 构造 weekBits 的方法（参考）
- 336 位位图的顺序为：周一(0..47) | 周二(0..47) | ... | 周日(0..47)。
- 将 336 位按 BitSet 序列化为 42 字节后进行 BASE64：

Java 伪代码：
```java
BitSet bits = new BitSet(336);
// 以周一 0-8 点(16槽)布防为例：
for (int i = 0; i < 16; i++) { bits.set(i); }
byte[] bytes = bits.toByteArray(); // 长度需达 42 字节，否则补零对齐
if (bytes.length < 42) {
    bytes = Arrays.copyOf(bytes, 42);
}
String base64 = Base64.getEncoder().encodeToString(bytes);
```

> 若生成的数组小于 42 字节，请在尾部补零至 42 字节，或在 Redis 存储端固定写入 42 字节长度。

## 7. 查询周位图
- 路径：`GET /arming/schedule/{groupId}?format=base64|01`
- 示例：
```bash
curl -X GET "http://localhost:8888/arming/schedule/1?format=base64"
curl -X GET "http://localhost:8888/arming/schedule/1?format=01"
```
