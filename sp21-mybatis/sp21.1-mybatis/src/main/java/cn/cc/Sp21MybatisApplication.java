package cn.cc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = {"cn.cc.**.mapper","cn.cc.**.dao"}) // ,"cn.cc.mysql.busi.autosql.**.dao"
public class Sp21MybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp21MybatisApplication.class, args);
    }

}
