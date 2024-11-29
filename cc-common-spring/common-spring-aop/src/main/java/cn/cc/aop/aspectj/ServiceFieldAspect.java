package cn.cc.aop.aspectj;

import cn.cc.aop.aspectj.impl.ServiceFieldAspectImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ServiceFieldAspect extends ServiceFieldAspectImpl {

    @Before("execution(* cn.cc..*.service..*.save*(..))")
    public void beforeSave(final JoinPoint joinPoint) {
        save(joinPoint);
        log.info("进入aop");
    }

}
