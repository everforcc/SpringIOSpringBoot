/**
 * @Description
 * @Author everforcc
 * @Date 2022-08-17 16:08
 * Copyright
 */

package cn.cc.busi.jdbc;

import cn.cc.busi.jdbc.utils.JDBCUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class JDBCTests {

    @Resource
    JDBCUtils jdbcUtils;

    @Test
    public void level_1() {
        String sql = "SELECT e.`id`,e.`name`,e.`age`,e.`email` FROM cc_mybatis_plus_user e " +
                "WHERE e.`name` IN ('Jone','Jack','老子09','小树林俊杰具08')";
        jdbcUtils.executeQuery(sql);
    }

}
