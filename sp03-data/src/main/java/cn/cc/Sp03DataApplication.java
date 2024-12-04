package cn.cc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = {"cn.cc.**.mapper","cn.cc.**.dao"}) // ,"cn.cc.mysql.busi.autosql.**.dao"
public class Sp03DataApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp03DataApplication.class, args);
    }

}
