package com.cc.sp02thymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class Sp02ThymeleafApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp02ThymeleafApplication.class, args);
    }

}
