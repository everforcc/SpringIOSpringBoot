<span  style="font-family: Simsun,serif; font-size: 17px; ">

### 上月，上周，示例

~~~sql
-- 上个月
SELECT LAST_DAY(NOW() - INTERVAL 1 MONTH) AS '上个月最后一天'
FROM DUAL;
-- 去年几几年
SELECT YEAR (DATE_SUB(NOW(), INTERVAL 1 YEAR)) AS '去年几几年'
FROM DUAL;
-- 当前时间推迟一年
SELECT DATE_SUB(NOW(), INTERVAL 1 YEAR)
FROM DUAL;

-- 时间格式化
SELECT DATE_FORMAT('2005-01-01 13:14:20', '%Y-%m-%d %H:%i:%s')
FROM DUAL;
-- 上个月最后一天
SELECT DATE_FORMAT(LAST_DAY(NOW() - INTERVAL 1 MONTH), '%Y-%m-%d %H:%i:%s') AS '上个月最后一天'
FROM DUAL;
-- 去年上个月最后一天
SELECT DATE_FORMAT(LAST_DAY(DATE_SUB(NOW(), INTERVAL 1 YEAR) - INTERVAL 1 MONTH), '%Y-%m-%d %H:%i:%s') AS '去年上个月最后一天'
FROM DUAL;
~~~

</span>