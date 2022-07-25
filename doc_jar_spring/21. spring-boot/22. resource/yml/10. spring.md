<span  style="font-family: Simsun,serif; font-size: 17px; ">

### java 字段值为null,不返回该字段

- spirng下的参数
~~~yml
spring:
  jackson:
    default-property-inclusion: non_null # 返回对象字段为null则不显示
~~~
- 或者
~~~
// 类注解
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
~~~

</span>