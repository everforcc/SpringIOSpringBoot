package cn.cc;

import cn.cc.config.MinioConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

// Correct the classpath of your application so that it contains a single, compatible version of okhttp3.RequestBody
@Import(value = MinioConfig.class)
@SpringBootApplication
public class Sp43FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp43FileApplication.class, args);
    }

}
