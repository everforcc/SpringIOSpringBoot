package design.jdkp.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class UserDaoProxy implements InvocationHandler {
    private Object object;
    private MyAspect myAspect;

    public void setObject(Object object) {
        this.object = object;
    }

    public void setMyAspect(MyAspect myAspect) {
        this.myAspect = myAspect;
    }

    public Object createProxy(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),object.getClass().getInterfaces(),this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        myAspect.beforeLog();
        Object obj = method.invoke(object, args);
        myAspect.afterLog();
        return obj;
    }
}
