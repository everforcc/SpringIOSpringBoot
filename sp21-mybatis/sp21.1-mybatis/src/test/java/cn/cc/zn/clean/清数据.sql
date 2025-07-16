select count(*) from zn_test_del;

select * from zn_test_del ztd order by ztd.create_time asc ;

delete from zn_test_del;

-- 生成测试数据
CALL insert_test_data();