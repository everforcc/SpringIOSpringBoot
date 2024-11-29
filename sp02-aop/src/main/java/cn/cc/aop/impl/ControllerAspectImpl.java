/**
 * @Description
 * @Author everforcc
 * @Date 2022-07-01 16:41
 * Copyright
 */

package cn.cc.aop.impl;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;

public interface ControllerAspectImpl {

    Logger log = LoggerFactory.getLogger(ControllerAspectImpl.class);

    default void log(JoinPoint joinPoint){
        String str = Arrays.toString(joinPoint.getArgs());
        //String res = Objects.isNull(result)? "": JSON.toJSONString(result);
        log.info("前 req: 【{}】", str);
        //log.info("res: 【{}】", res);
    }

    default void log(Object result){
        //String str = Arrays.toString(joinPoint.getArgs());
        String res = Objects.isNull(result)? "": JSON.toJSONString(result);
        //log.info("str: 【{}】", str);
        log.info("后 res: 【{}】", res);
    }

}
