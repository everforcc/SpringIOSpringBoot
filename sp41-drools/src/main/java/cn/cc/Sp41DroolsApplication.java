package cn.cc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * sp41drools
 */
@EnableCaching
@MapperScan("cn.cc.**.mapper")
@SpringBootApplication
public class Sp41DroolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp41DroolsApplication.class, args);
    }

}
