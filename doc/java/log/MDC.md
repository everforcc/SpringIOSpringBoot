<font face="Simsun" size=3>

- [参考](https://blog.csdn.net/a183400826/article/details/101519219)
- 先用uuid生成，需要在最外层就赋值
~~~ log.xml
<pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %thread | [%X{X-B3-TraceId}] | %-5level %logger{50} %msg%n</pattern>
~~~

</font>