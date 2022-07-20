-- 如果右边的为null就西安市委空

SELECT e.*,a.* FROM cc_website e LEFT JOIN cc_websitedata a ON e.`id` = a.`parentid`
WHERE e.`id` = 1;

-- left join 三个表
SELECT e.`id` 'eid',a.`id` 'aid',c.`id` 'cid' FROM cc_website e
LEFT JOIN cc_websitedata a ON e.`id` = a.`parentid`
LEFT JOIN cc_websitetag c ON a.`id` = c.`webrootid`
WHERE e.`id` = '1' ;