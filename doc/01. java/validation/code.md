<font face="Simsun" size=3>

~~~
1. dto类字段需要的加上注解

2. 自定义接口，控制不同情况需要控制的字段
ISave
IUpdate
IMDelete
IDelete

3. 方法上加上包含组的注解  @Validated({ISave.class})
~~~
需要校验的类要有注解@Validated
方法上 @Validated({ISave.class})
~~~

4. 对应对象加上  @Valid 
~~~
参数 @Valid Object obj
~~~

~~~


</font>