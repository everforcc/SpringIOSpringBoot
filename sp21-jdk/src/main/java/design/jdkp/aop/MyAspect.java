package design.jdkp.aop;

//切面
public class MyAspect {
    //切入的方法，即通知
    public void beforeLog(){
        System.out.println("前置日志");
    }
    //切入的方法，即通知
    public void afterLog(){
        System.out.println("后置日子");
    }
}
