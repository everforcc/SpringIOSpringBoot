package com.cc.sp91test.test.spcglib.aop;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class UserDaoProxy1 implements MethodInterceptor {
    public Object createProxy(Object o){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(o.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        MyAspect myAspect = new MyAspect();
        myAspect.beforeLog();
        Object o1 = methodProxy.invokeSuper(o, objects);
        myAspect.afterLog();
        return o1;
    }
}
