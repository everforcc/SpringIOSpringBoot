package cn.cc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = {"cn.cc.**.mapper", "cn.cc.**.dao"})
public class Sp80ZnkjApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp80ZnkjApplication.class, args);
    }

}
