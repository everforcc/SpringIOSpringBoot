<span  style="font-family: Simsun,serif; font-size: 17px; ">

[TOC]

- [SpringCloud之开启Eureka密码认证](https://blog.csdn.net/jc_hook/article/details/125526221)

### 开启密码认证

#### 1.pom

~~~
<!--引入security-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
~~~

#### 2.配置文件

~~~
spring:
  security:
    user:
      name: admin
      password: 123456
~~~

#### 3. 配置类

##### Spring Security 5.6.5及更旧版本或Spring Boot 2.6.8

~~~
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭csrf
        http.csrf().disable();
        // 支持httpBasic
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }
}
~~~

##### Spring Security 5.7.1及更新版本或者Spring Boot 2.7.0及更新版本

~~~
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //关闭csrf
        http.csrf().disable();
        //支持httpBasic
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
        return http.build();
    }
}
~~~

### 注册服务

- 修改配置文件，将服务注册到开启了密码认证的eureka中心。

~~~
secutiry:
	user:
		name: admin
      	password: 123456

eureka:
  client:
    service-url:
      defaultZone: http://${secutiry.user.name}:${secutiry.user.password}@localhost:8086/eureka
~~~

</span>