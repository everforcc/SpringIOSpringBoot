/**
 * @Description
 * @Author everforcc
 * @Date 2022-08-17 16:08
 * Copyright
 */

package cn.cc.zn.clean;

import cn.cc.busi.autosql.dao.CommonDelDao;
import cn.cc.busi.jdbc.utils.JDBCUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class CleanData {

    @Resource
    CommonDelDao commonDelDao;

    @Test
    public void level_1() {
        // 现在有1000条数据
        // 当设置为1112的时候，会自动删除1112-1000的数据
        String tableName = "zn_test_del";
        int maxCount = 1000;
        double maxRate = 0.9;
        double minRate = 0.8;
        commonDelDao.autoCleanData(tableName, maxCount, maxRate, minRate);
    }

}
