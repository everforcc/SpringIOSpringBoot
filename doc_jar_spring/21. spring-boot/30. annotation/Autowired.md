<span  style="font-family: Simsun,serif; font-size: 17px; ">

[TOC]

- spring定义

## 1. 使用范围

~~~
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    boolean required() default true;
}
~~~

### 1.1 成员变量

~~~
~~~

### 1.2 构造器

~~~
实际还是成员变量
~~~

### 1.3 方法

~~~
spring会在启动的过程中，自动调用一次， static？ 初始化数据吧
~~~

### 1.4 参数

~~~
@Service
public class UserService {
    private IUser user;
    public UserService(@Autowired IUser user) {
        this.user = user;
    }
}
~~~

### 1.5 注解

### 1.6 实例化多个

~~~
假如一个接口有多个实现类，用list来实例会全部拿过来
~~~

### 1.7 使用位置

~~~
spring加载完以后
~~~

</span>