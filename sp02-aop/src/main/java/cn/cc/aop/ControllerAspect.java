/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-01 16:41
 * Copyright
 */

package cn.cc.aop;

import cn.cc.aop.impl.ControllerAspectImpl;
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

    @Pointcut("execution(* cn.cc..*.controller..*.*(..))")
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
