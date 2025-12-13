package cn.cc.sp73readability;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 预留定时任务能力，后续可用于定时抓取/队列巡检
public class Sp73ReadabilityApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp73ReadabilityApplication.class, args);
    }

}
