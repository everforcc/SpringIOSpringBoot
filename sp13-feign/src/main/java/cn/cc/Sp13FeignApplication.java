package cn.cc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class Sp13FeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp13FeignApplication.class, args);
    }

}
