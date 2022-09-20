-- 时间区间查询
SELECT e.`createtime` FROM cc_novel e
WHERE e.`createtime` >= DATE('2022-09-13') AND e.`createtime` < DATE('2022-09-14');