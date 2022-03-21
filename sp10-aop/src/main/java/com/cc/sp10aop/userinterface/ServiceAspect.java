package com.cc.sp10aop.userinterface;

import java.lang.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceAspect {

    //int id() default 0;

    String uuid() default "";

    String createTime() default "";

    String updateTime() default "" ;

    int effect() default 0;

    int status() default 0 ;




}
