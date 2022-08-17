/**
 * @Description
 * @Author everforcc
 * @Date 2022-08-17 16:08
 * Copyright
 */

package com.cc.sp03data.jdbc;

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
                "WHERE e.`name` IN ('Jone','Jack')";
        jdbcUtils.executeQuery(sql);
    }

}
