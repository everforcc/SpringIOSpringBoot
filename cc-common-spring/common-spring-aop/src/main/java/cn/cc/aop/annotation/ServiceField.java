package cn.cc.aop.annotation;

import java.lang.annotation.*;

/**
 * 处理service层公共字段
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceField {

    //int id() default 0;

    String uuid() default "";

    String createTime() default "";

    String updateTime() default "" ;

    int effect() default 0;

    int status() default 0 ;

}
