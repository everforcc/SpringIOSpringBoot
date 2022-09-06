-- 将多行数据合并为一行
-- mysql
SELECT GROUP_CONCAT(e.`username`)
FROM cc_custom_user e;
-- oracle
SELECT wm_concat(e.`username`)
FROM cc_custom_user e;
