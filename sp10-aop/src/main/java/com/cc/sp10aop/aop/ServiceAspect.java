package com.cc.sp10aop.aop;

import com.cc.sp10aop.aop.impl.ServiceAspectImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ServiceAspect extends ServiceAspectImpl {

    @Before("execution(* com.cc.sp10aop..*.service..*.save*(..))")
    public void beforeSave(final JoinPoint joinPoint) {
        //save(joinPoint);
        log.info("进入aop");
    }

}
