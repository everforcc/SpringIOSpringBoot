/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-01 16:41
 * Copyright
 */

package com.cc.sp10aop.aop;

import com.cc.sp10aop.aop.impl.ControllerAspectImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class ControllerAspect implements ControllerAspectImpl {

    @Pointcut("execution(* com.cc.sp10aop..*.controller..*.*(..))")
    public void point(){

    }

    @Before(value = "point()")
    public void before(JoinPoint joinPoint){
        log(joinPoint);
    }

    @AfterReturning(value = "point()", returning = "result")
    public void after(JoinPoint joinPoint, Object result){
        log(result);
    }

}
