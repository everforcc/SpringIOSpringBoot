package cn.cc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Sp35WebSocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp35WebSocketApplication.class, args);
    }

}
