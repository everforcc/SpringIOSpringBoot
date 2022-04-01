package com.cc.sp10aop.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Slf4j
@Aspect
@Component
public class DemoOrder2Aspect {

    @Pointcut(value = "execution(public String com.cc.sp10aop.business.flow.service.FlowService.testFlow(..))")
    public void pointcut() {}

    @Before("pointcut()")
    public void before(JoinPoint point) {
        log.info("order2. 2. before");
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        log.info("order2. 1. around begin");
        try {
            Object proceed = point.proceed();
            log.info("order2. 6. around after");
            return proceed;
        } catch (Throwable e) {
            log.info("order2. e2. around after exception");
            throw e;
        }
    }

    @After("pointcut()")
    public void after() throws Throwable {
        log.info("order2. 5. after");
    }

    @AfterReturning("pointcut()")
    public void afterReturning() throws Throwable {
        log.info("order2. 4. afterReturning");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        log.info("order2. e1. afterThrowing");
    }

}
