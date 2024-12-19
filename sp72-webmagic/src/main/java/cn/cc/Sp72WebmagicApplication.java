package cn.cc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("cn.cc")
@SpringBootApplication
public class Sp72WebmagicApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp72WebmagicApplication.class, args);
    }

}
