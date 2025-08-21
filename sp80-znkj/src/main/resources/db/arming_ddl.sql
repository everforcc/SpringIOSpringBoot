-- =====================================================
-- 布防撤防系统数据库表结构
-- 说明：本文件包含布防撤防系统的所有数据库表定义
-- 作者：系统设计团队
-- 创建时间：2024年
-- =====================================================

-- =====================================================
-- 设备表：存储设备基本信息及其所属的布防组
-- 说明：设备是布防系统的最小单位，每个设备只能归属一个布防组
-- =====================================================
drop table IF EXISTS arming_device;
CREATE TABLE IF NOT EXISTS arming_device (
  id        BIGINT      NOT NULL PRIMARY KEY COMMENT '设备ID，主键',
  group_id  BIGINT      NOT NULL COMMENT '布防组ID，关联arming_group表',
  type_id   BIGINT      NULL COMMENT '设备类型ID，用于设备分类',
  model_id  BIGINT      NULL COMMENT '设备型号ID，用于设备规格',
  name      VARCHAR(64) NULL COMMENT '设备名称，便于识别和管理',
  updated_at TIMESTAMP  NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  INDEX idx_arming_device_group_id (group_id) COMMENT '布防组索引，提高查询性能'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备信息表';

-- =====================================================
-- 布防组表：存储布防组的基本信息
-- 说明：布防组是设备的分组单位，每个布防组有自己的周计划和撤防状态
-- =====================================================
drop table IF EXISTS arming_group;
CREATE TABLE IF NOT EXISTS arming_group (
  id        BIGINT      NOT NULL PRIMARY KEY COMMENT '布防组ID，主键',
  name      VARCHAR(64) NOT NULL COMMENT '布防组名称，应该具有业务含义',
  status    TINYINT     NOT NULL DEFAULT 1 COMMENT '布防组状态：1=启用，0=禁用',
  updated_at TIMESTAMP  NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  INDEX idx_arming_group_status (status) COMMENT '状态索引，便于查询启用的布防组'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='布防组信息表';

-- =====================================================
-- 布防组周位图表：存储每个布防组的一周布防计划
-- 说明：周位图是布防系统的核心数据，336位表示一周的布防计划
-- =====================================================
drop table IF EXISTS arming_group_schedule;
CREATE TABLE IF NOT EXISTS arming_group_schedule (
  group_id  BIGINT       NOT NULL PRIMARY KEY COMMENT '布防组ID，主键，关联arming_group表',
  week_bits VARBINARY(42) NOT NULL COMMENT '周位图数据，42字节=336位，表示一周的布防计划',
  updated_at TIMESTAMP   NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='布防组周位图表';

-- =====================================================
-- 布防组今日撤防掩码表：存储每日的撤防状态
-- 说明：用于Redis宕机后的数据恢复，记录每日的撤防掩码
-- =====================================================
drop table IF EXISTS arming_group_today_mask;
CREATE TABLE IF NOT EXISTS arming_group_today_mask (
  id        BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID，自增',
  group_id  BIGINT      NOT NULL COMMENT '布防组ID，关联arming_group表',
  biz_date  DATE        NOT NULL COMMENT '业务日期，格式：YYYY-MM-DD',
  mask_bits VARBINARY(6) NOT NULL COMMENT '撤防掩码数据，6字节=48位，表示当天的撤防状态',
  created_at TIMESTAMP  NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UNIQUE KEY uk_gid_date (group_id, biz_date) COMMENT '布防组和日期的唯一约束，确保每天只有一条记录',
  INDEX idx_arming_group_today_mask_group_id (group_id) COMMENT '布防组索引',
  INDEX idx_arming_group_today_mask_biz_date (biz_date) COMMENT '业务日期索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='布防组今日撤防掩码表';

-- =====================================================
-- 测试数据初始化
-- 说明：以下数据用于系统测试和演示
-- =====================================================

-- 初始化一个默认布防组
INSERT INTO arming_group(id, name, status) VALUES(1, '默认组', 1)
  ON DUPLICATE KEY UPDATE name = VALUES(name), status = VALUES(status);

-- 初始化默认布防组的周位图（全撤防状态）
-- 注意：这里使用全0的42字节，实际使用时需要通过API接口设置具体的布防计划
INSERT INTO arming_group_schedule(group_id, week_bits) VALUES(1, UNHEX(REPEAT('00', 42)))
  ON DUPLICATE KEY UPDATE week_bits = VALUES(week_bits);

-- 初始化一个测试设备，归属默认布防组
INSERT INTO arming_device(id, group_id, name)VALUES (10001, 1, '测试设备10001')
ON DUPLICATE KEY UPDATE group_id = VALUES(group_id), name = VALUES(name);

-- =====================================================
-- 数据说明
-- =====================================================
/*
周位图数据格式说明：
- 42字节 = 336位
- 7天 × 48槽位 = 336位
- 每个槽位代表30分钟
- 1表示布防，0表示撤防

位图结构：
- 第0-47位：周一（00:00-23:59）
- 第48-95位：周二（00:00-23:59）
- 第96-143位：周三（00:00-23:59）
- 第144-191位：周四（00:00-23:59）
- 第192-239位：周五（00:00-23:59）
- 第240-287位：周六（00:00-23:59）
- 第288-335位：周日（00:00-23:59）

撤防掩码数据格式说明：
- 6字节 = 48位
- 1天 × 48槽位 = 48位
- 每个槽位代表30分钟
- 1表示需要撤防，0表示按周计划执行

业务规则：
1. 设备只能归属一个布防组
2. 布防组可以启用或禁用
3. 每个布防组只能有一个周计划
4. 每日撤防记录用于Redis宕机恢复
5. 撤防掩码在午夜自动过期，恢复周计划
*/


