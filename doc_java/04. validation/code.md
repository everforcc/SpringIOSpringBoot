<span  style="font-family: Simsun,serif; font-size: 17px; ">

- 代码中校验非空使用 @NotEmpty 也不能为空字符串
~~~
1. dto类字段需要的加上注解

- @NotNull 等
- @size 等
- 如果自定义了ISave和IUpdate的话，也要在group里面加入

2. 自定义接口，控制不同情况需要控制的字段
ISave
IUpdate
IMDelete
IDelete

3. 方法上加上包含组的注解  @Validated({ISave.class})

3.1. 需要校验的类要有注解@Validated
3.2. 方法上 @Validated({ISave.class})
3.3. 参数注解@Valid

参数 @Valid Object obj

~~~

### 对象作为属性

- @NotNull

</span>