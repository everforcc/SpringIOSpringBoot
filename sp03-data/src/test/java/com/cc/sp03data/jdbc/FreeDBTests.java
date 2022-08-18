/**
 * @Description
 * @Author everforcc
 * @Date 2022-08-09 18:20
 * Copyright
 */

package com.cc.sp03data.jdbc;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FreeDBTests {

    public static Connection init() {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            // ?sslMode=VERIFY_IDENTITY
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://6ocz8lzsq4hd.us-east-4.psdb.cloud/oneforall",
                    "a0yhrwlul53j",
                    "pscale_pw_-Yb-3tlCQj1TxxksyjnWlIB3g-ZfgKgLGjQgQ7tiGm8");
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void execute() {
        //DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        List<Connection> connectionList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            LocalTime start = LocalTime.now();
            System.out.println("sta: " + start);
            Connection connection = init();
            connectionList.add(connection);
            LocalTime end = LocalTime.now();
            System.out.println("end: " + end);
        }

        System.out.println("当前有" + connectionList.size() + "个链接");

        try {
            for (int j = 0; j < 10; j++) {
                for (int i = 0; i < 4; i++) {
                    System.out.println("------" + i);
                    String sql = "select * from cc_free_db where id = " + i;

                    int index = i % 3;
                    System.out.println("选用第" + index + "个链接");
                    Connection connection = connectionList.get(index);
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql);

                    while (resultSet.next()) {
                        LocalTime localTime = LocalTime.now();
                        System.out.println(localTime);
                        System.out.println("id: " + resultSet.getInt("id"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        execute();
        System.out.println();
        //System.out.println(0 % 3);
        //System.out.println("---");
    }

}
