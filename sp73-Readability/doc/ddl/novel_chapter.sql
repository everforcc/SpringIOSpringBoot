-- 小说与章节表结构
-- 使用前请根据环境调整库名/前缀，默认库：oneforall

CREATE TABLE IF NOT EXISTS `ai_readability_novel` (
  `novel_id`     BIGINT       NOT NULL AUTO_INCREMENT COMMENT '小说唯一 ID',
  `title`        VARCHAR(255) NOT NULL DEFAULT '' COMMENT '小说标题',
  `author`       VARCHAR(100)          DEFAULT NULL COMMENT '作者',
  `synopsis`     TEXT                  DEFAULT NULL COMMENT '小说简介/概要',
  `catalog_url`  VARCHAR(512) NOT NULL DEFAULT '' COMMENT '原始目录页 URL',
  `status`       VARCHAR(20)           DEFAULT NULL COMMENT '连载状态（连载中/已完结）',
  `create_time`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`novel_id`),
  KEY `idx_title` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='小说元数据';


CREATE TABLE IF NOT EXISTS `ai_readability_chapter` (
  `chapter_id`     BIGINT       NOT NULL AUTO_INCREMENT COMMENT '章节唯一 ID',
  `novel_id`       BIGINT       NOT NULL COMMENT '外键，关联 aiReadabilityNovel.novel_id',
  `chapter_index`  INT          NOT NULL COMMENT '章节序号',
  `chapter_title`  VARCHAR(255) NOT NULL DEFAULT '' COMMENT '章节标题',
  `content`        LONGTEXT              COMMENT '章节纯文本内容',
  `source_url`     VARCHAR(512) NOT NULL DEFAULT '' COMMENT '原始章节页 URL',
  `create_time`    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`chapter_id`),
  UNIQUE KEY `uk_novel_chapter_index` (`novel_id`, `chapter_index`),
  KEY `idx_novel_id` (`novel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='小说章节内容';

-- 可选：记录抓取任务状态/失败原因的表，可后续扩展

