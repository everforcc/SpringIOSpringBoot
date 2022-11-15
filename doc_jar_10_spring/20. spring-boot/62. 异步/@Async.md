<span  style="font-family: Simsun,serif; font-size: 17px; ">

- [Spring @Async之三：Spring @Async使用方法总结](https://www.cnblogs.com/duanxz/p/6782933.html)
- [配置文件参考](https://blog.csdn.net/qq_44750696/article/details/123960134)

### 1. 开启

- 启动类

~~~
@EnableAsync
~~~

- 方法

~~~
必须是spring的类 @Component
方法上加注解 @Async
~~~

- 不能使用static方法

### 2. 无返回值

- oneforall 示例代码

### 3. 有返回值

- oneforall 示例代码

### 4. 异常

- oneforall 示例代码

1. 自定义实现AsyncTaskExecutor的任务执行器，在这里定义处理具体异常的逻辑和方式。
2. 配置由自定义的TaskExecutor替代内置的任务执行器

### 5. 事务

- 在@Async标注的方法，同时也适用了@Transactional进行了标注；
- 在其调用数据库操作之时，将无法产生事务管理的控制，原因就在于其是基于异步处理的操作。

---

- 那该如何给这些操作添加事务管理呢？可以将需要事务管理操作的方法放置到异步方法内部，在内部被调用的方法上添加@Transactional.
- 例如：
    - 方法A，使用了@Async/@Transactional来标注，但是无法产生事务控制的目的
    - 方法B，使用了@Async来标注， B中调用了C、D，C/D分别使用@Transactional做了标注，则可实现事务控制的目的。

### 6. 自定义线程池 配置

#### 6.1 一个线程池

#### 6.2 多个线程池

</span>