package com.cc.sp01init.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import java.util.List;

@Validated// 数据校验
@Component
@ConfigurationProperties(prefix = "dog")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dog {

    //@Value("狗")
    private String name;
    //@Value("1")
    private Integer age;

    // 需要starter
    @Email(message = "自定义异常消息")
    private String email;

    private List<String> list;

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", list=" + list +
                '}';
    }
}
