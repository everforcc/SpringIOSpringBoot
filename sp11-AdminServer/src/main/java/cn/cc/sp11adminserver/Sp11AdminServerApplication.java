package cn.cc.sp11adminserver;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class Sp11AdminServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp11AdminServerApplication.class, args);
    }

}
