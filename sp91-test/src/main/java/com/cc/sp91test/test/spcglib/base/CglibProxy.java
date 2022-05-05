package com.cc.sp91test.test.spcglib.base;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

//代理类
public class CglibProxy implements MethodInterceptor {
    //创建代理对象
    public Object createProxy(Rent rent){
        //创建一个动态类对象
        Enhancer enhancer = new Enhancer();
        //设置父类，即增强的类
        enhancer.setSuperclass(rent.getClass());
        //设置回调函数
        enhancer.setCallback(this);
        //创建代理对象
        return enhancer.create();
    }
    //回调函数
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object o1 = methodProxy.invokeSuper(o, objects);
        return o1;
    }
}

