-- 如果右边的为null就西安市委空

SELECT e.*,a.* FROM cc_website e LEFT JOIN cc_websitedata a ON e.`id` = a.`parentid`
WHERE e.`id` = 1;