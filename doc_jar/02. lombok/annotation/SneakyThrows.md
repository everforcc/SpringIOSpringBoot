<span  style="font-family: Simsun,serif; font-size: 17px; ">

[TOC]

## @SneakyThrows

java中的异常常见的有两类

1. 普通Exception类,也就是我们常说的受检异常或者Checked Exception。
2. RuntimeException类，既运行时异常。

- 前者强制要求调用者处理，开发者要处理一些场景中必然存在的情况,比如网络异常，文件找不到异常
- 但是不可能每种都处理，一般都是一路抛上去
~~~
try{
}catch(Exception e){
    throw new RuntimeException(e);
}
~~~

---

- 实际生成代码
~~~
try {
    
} catch (Throwable var3) {
    throw var3;
}
~~~

---

- 源码
~~~
    public static RuntimeException sneakyThrow(Throwable t) {
        if (t == null) {
            throw new NullPointerException("t");
        } else {
            return (RuntimeException)sneakyThrow0(t);
        }
    }

    private static <T extends Throwable> T sneakyThrow0(Throwable t) throws T {
        throw t;
    }
~~~

</span>