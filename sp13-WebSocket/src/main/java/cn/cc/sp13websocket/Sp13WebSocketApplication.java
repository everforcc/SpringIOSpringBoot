package cn.cc.sp13websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Sp13WebSocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp13WebSocketApplication.class, args);
    }

}
