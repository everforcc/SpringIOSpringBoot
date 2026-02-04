package cn.cc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@MapperScan(value = {"cn.cc.**.dao"})
@SpringBootApplication
public class Sp213BinlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp213BinlogApplication.class, args);
    }

}
