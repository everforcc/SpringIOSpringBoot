package cn.cc.sp04mybatisplus;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@MapperScan(basePackages = {"cn.cc.sp04mybatisplus.mapper","cn.cc.sp04mybatisplus"},annotationClass = Mapper.class)
@MapperScan("cn.cc.sp04mybatisplus")
@SpringBootApplication
public class Sp04MybatisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp04MybatisPlusApplication.class, args);
    }

}
