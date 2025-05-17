-- 用户表
CREATE TABLE `user` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '唯一id',
  `account` VARCHAR(32) NOT NULL UNIQUE COMMENT '账号',
  `name` VARCHAR(32) NOT NULL COMMENT '用户名',
  `password` VARCHAR(64) NOT NULL COMMENT '密码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 好友表
CREATE TABLE `friend` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '唯一id',
  `user_id` BIGINT NOT NULL COMMENT '用户id',
  `friend_id` BIGINT NOT NULL COMMENT '好友id',
  KEY `idx_user_id` (`user_id`),
  KEY `idx_friend_id` (`friend_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 聊天记录表
CREATE TABLE `chat_record` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '唯一id',
  `user_id` BIGINT NOT NULL COMMENT '用户id',
  `friend_id` BIGINT NOT NULL COMMENT '好友id',
  `msg` TEXT NOT NULL COMMENT '聊天信息',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  KEY `idx_user_id` (`user_id`),
  KEY `idx_friend_id` (`friend_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4; 