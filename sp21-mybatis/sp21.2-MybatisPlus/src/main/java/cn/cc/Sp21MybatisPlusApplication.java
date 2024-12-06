package cn.cc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@MapperScan(basePackages = {"cn.cc.sp04mybatisplus.mapper","cn.cc.sp04mybatisplus"},annotationClass = Mapper.class)
@MapperScan("cn.cc")
@SpringBootApplication
public class Sp21MybatisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp21MybatisPlusApplication.class, args);
    }

}
