package com.cc.sp10aop.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Slf4j
@Aspect
@Component
public class DemoAspect {

    @Pointcut(value = "execution(public String com.cc.sp10aop.business.flow.service.impl.FlowService.testFlow*(..))")
    public void pointcut() {}

    @Before("pointcut()")
    public void before(JoinPoint point) {
        log.info("order1. 2. before");
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        log.info("order1. 1. around begin");
        try {
            Object proceed = point.proceed();
            log.info("order1. 6. around after");
            return proceed;
        } catch (Throwable e) {
            log.info("order1. e2. around after exception");
            throw e;
        }
    }

    @After("pointcut()")
    public void after() throws Throwable {
        log.info("order1. 5. after");
    }

    @AfterReturning("pointcut()")
    public void afterReturning() throws Throwable {
        log.info("order1. 4. afterReturning");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        log.info("order1. e1. afterThrowing");
    }

}
