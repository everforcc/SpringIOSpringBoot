package cn.cc.sp50feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class Sp50FeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp50FeignApplication.class, args);
    }

}
