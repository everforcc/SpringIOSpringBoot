package cn.cc.aop.aspectj.impl;

import cn.cc.aop.annotation.ServiceField;
import cn.cc.aop.dto.CommonFieldDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.UUID;


@Slf4j
public class ServiceFieldAspectImpl {

    /**
     * 1. 用来处理所有的service层,给公共的字段赋值
     * 2. 更新的时候处理更新字段， eg: 更新时间,用户id，用户名，
     */

    protected void save(JoinPoint joinPoint) {
        MDC.put("X-B3-TraceId", UUID.randomUUID().toString().replace("-", ""));
        /**
         * 1. 获取类上的注解
         */
        ServiceField serviceField = joinPoint.getTarget().getClass().getAnnotation(ServiceField.class);
        if (Objects.nonNull(serviceField)) {
            /**
             * 提取系统中公共的字段赋值
             */
            /**
             * 是哪个方法触发的aop
             */
            log.info("joinPoint: {}", joinPoint.toString());
            log.info("X-B3-TraceId: {}", MDC.get("X-B3-TraceId"));
            log.info("serviceAspect.status(): {}", serviceField.status());
            log.info("controller path: {}", getPath());

            final Object obj = joinPoint.getArgs()[0]; // 当前新增对象
            final Object userId = joinPoint.getArgs().length > 1 ? joinPoint.getArgs()[1] : 0; // 当前操作用户id
            // 如果有多余的可以指定忽略某些字段
            BeanUtils.copyProperties(CommonFieldDto.save(userId), obj);
        } else {
            //
            log.info("没有获取到注解 @ServiceAspect");
        }
    }

    /**
     * 获得 controller 请求URL
     *
     * @return String
     */
    private String getPath() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "请求 URL 获取失败";
        }
    }

}
