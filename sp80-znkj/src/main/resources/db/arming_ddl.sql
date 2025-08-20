-- 设备表：仅示例字段，实际可扩展类型/型号等维度
CREATE TABLE IF NOT EXISTS device (
  id        BIGINT      NOT NULL PRIMARY KEY,
  group_id  BIGINT      NOT NULL,
  type_id   BIGINT      NULL,
  model_id  BIGINT      NULL,
  name      VARCHAR(64) NULL,
  updated_at TIMESTAMP  NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_device_group_id (group_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 布防组
CREATE TABLE IF NOT EXISTS arming_group (
  id        BIGINT      NOT NULL PRIMARY KEY,
  name      VARCHAR(64) NOT NULL,
  status    TINYINT     NOT NULL DEFAULT 1,
  updated_at TIMESTAMP  NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 布防组周位图（7*48=336bit=42字节）：1=布防，0=撤防
CREATE TABLE IF NOT EXISTS arming_group_schedule (
  group_id  BIGINT       NOT NULL PRIMARY KEY,
  week_bits VARBINARY(42) NOT NULL,
  updated_at TIMESTAMP   NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 示例：初始化一条组计划（全撤防=全0）
-- INSERT INTO arming_group_schedule(group_id, week_bits) VALUES(1, UNHEX(REPEAT('00', 42)));

-- 测试数据
INSERT INTO arming_group(id, name, status) VALUES(1, '默认组', 1)
  ON DUPLICATE KEY UPDATE name = VALUES(name), status = VALUES(status);

-- 构造一条“0-8布防，8-12撤防，12-14布防，14-18撤防，18-24布防”的当天位图（以周一为例），其余全撤防
-- 这里直接给出完整 42 字节，简化演示：先置空，再按业务接口写入
INSERT INTO arming_group_schedule(group_id, week_bits) VALUES(1, UNHEX(REPEAT('00', 42)))
  ON DUPLICATE KEY UPDATE week_bits = VALUES(week_bits);

-- 一个设备，归属组1
INSERT INTO device(id, group_id, name) VALUES(10001, 1, '测试设备10001')
  ON DUPLICATE KEY UPDATE group_id = VALUES(group_id), name = VALUES(name);

-- 可选：为了在 Redis 宕机后恢复今日撤防信息，增加“每日撤防历史表”（按日持久化），服务写掩码时同时写此表
CREATE TABLE IF NOT EXISTS arming_group_today_mask (
  id        BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
  group_id  BIGINT      NOT NULL,
  biz_date  DATE        NOT NULL,
  mask_bits VARBINARY(6) NOT NULL,
  created_at TIMESTAMP  NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY uk_gid_date (group_id, biz_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


