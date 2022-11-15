<span  style="font-family: Simsun,serif; font-size: 15px; ">

- 方法异常重试
- [版本地址](https://docs.spring.io/spring-boot/docs/current/reference/html/dependency-versions.html#appendix.dependency-versions)
- org.springframework.retry

### pom

~~~
<dependency>
    <groupId>org.springframework.retry</groupId>
    <artifactId>spring-retry</artifactId>
</dependency>
~~~

### 主类

~~~
@EnableRetry
public class OneForAllApplication {
~~~

### 方法

~~~
@Retryable(value = RuntimeException.class, maxAttempts = 5)
public void retryException(){
    log.info("错误重试");
    throw new RuntimeException("错误重试");
}
~~~

### 注解参数

~~~java
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Retryable {
    String interceptor() default "";

    // 指定异常类型
    Class<? extends Throwable>[] value() default {};

    Class<? extends Throwable>[] include() default {};

    Class<? extends Throwable>[] exclude() default {};

    String label() default "";

    boolean stateful() default false;

    // 最多重试次数
    int maxAttempts() default 3;

    String maxAttemptsExpression() default "";

    // delay 延迟
    Backoff backoff() default @Backoff;

    String exceptionExpression() default "";

    String[] listeners() default {};
}
~~~

</span>