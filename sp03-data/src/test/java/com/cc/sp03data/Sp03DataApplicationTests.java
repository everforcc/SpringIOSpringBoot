package com.cc.sp03data;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class Sp03DataApplicationTests {

    @Autowired
    DataSource dataSource;


    @Test
    void contextLoads() {

        /**
         * 查看默认的数据源
         * class com.zaxxer.hikari.HikariDataSource
         *
         */
        System.out.println(dataSource.getClass());
        try {

            DruidDataSource druidDataSource = (DruidDataSource) dataSource;

            System.out.println(druidDataSource.getInitialSize());

            Connection connection = dataSource.getConnection();
            System.out.println(connection);

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
