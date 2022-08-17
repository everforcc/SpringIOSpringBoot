/**
 * @Description
 * @Author everforcc
 * @Date 2022-08-17 15:10
 * Copyright
 */

package com.cc.sp03data.jdbc;

import com.cc.sp03data.dto.MybatisUser;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

@Component
@Slf4j
public class JDBCUtils {

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String passWord;

    @Value("${spring.datasource.url}")
    private String url;

    /**
     *
     */
    @SneakyThrows
    public void executeQuery(String sql) {
        log.info("userName: {}", userName);
        log.info("passWord: {}", passWord);
        @Cleanup
        Connection connection = DriverManager.getConnection(url, userName, passWord);
        log.info("connection: {}", connection);
        @Cleanup
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // ? 占位符
        // preparedStatement.setString(1, "name");
        ResultSet resultSet = preparedStatement.executeQuery();

        IResultSetHandler iResultSetHandler = new BearHandler<>(MybatisUser.class);
        boolean flag = true;
        // 如果是list
        while (flag) {
            MybatisUser mybatisUser = (MybatisUser) iResultSetHandler.handler(resultSet);
            if (Objects.isNull(mybatisUser)) {
                flag = false;
            } else {
                log.info("mybatisUser: \r\n{}", mybatisUser);
            }
        }
        //log.info("name: {}", resultSet.getString("name"));
    }

}
