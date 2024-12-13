package cn.cc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})
@PropertySource(encoding = "UTF-8", value = {"classpath:application.yml"})
public class Sp31MqttUtilApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp31MqttUtilApplication.class, args);
    }

}
