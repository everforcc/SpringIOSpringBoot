package com.cc.sp03data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.cc.sp03data.**.mapper")
public class Sp03DataApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp03DataApplication.class, args);
    }

}
