
-- 创建存储过程 insert_test_data：
-- 使用循环插入 1000 条数据
-- ID 从 1 到 1000 连续递增
-- content 字段格式为 zn_test_del_1 到 zn_test_del_1000
-- create_time 从 2025-06-27 00:00:00 开始，每条数据增加 1 秒
-- 添加存储过程调用语句：
-- 最后一行添加 CALL insert_test_data() 执行存储过程生成数据
-- 这个存储过程会生成符合要求的测试数据，每次调用都会重新插入 1000 条新记录。

-- 创建存储过程，向 zn_test_del 表插入1000条测试数据
DROP PROCEDURE IF EXISTS insert_test_data;

DELIMITER $$
CREATE PROCEDURE insert_test_data()
BEGIN
  DECLARE i INT DEFAULT 1;
  DECLARE start_time DATETIME DEFAULT '2025-06-27 00:00:00';
  
  -- 循环插入1000条数据
  WHILE i <= 1000 DO
    INSERT INTO zn_test_del(id, content, create_time)
    VALUES (
      i, 
      CONCAT('zn_test_del_', i),
      DATE_ADD(start_time, INTERVAL (i-1) SECOND)
    );
    SET i = i + 1;
  END WHILE;
END$$
DELIMITER ;

-- 调用存储过程生成测试数据
CALL insert_test_data();