package cn.cc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

/**
 * @see <a href="https://segmentfault.com/a/1190000017811919">原文地址</a>
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})
@PropertySource(encoding = "UTF-8", value = {"classpath:config/mqtt.properties"})
public class Sp32MqttSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp32MqttSpringApplication.class, args);
    }

}
