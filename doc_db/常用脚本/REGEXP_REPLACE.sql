-- 匹配出数字,然后按照数字排序
SELECT e.`name`, REGEXP_REPLACE(e.`name`, '[^0-9]', '') AS ttt
FROM cc_mybatis_plus_user e
ORDER BY CONVERT(REGEXP_REPLACE(e.`name`, '[^0-9]', ''), SIGNED)