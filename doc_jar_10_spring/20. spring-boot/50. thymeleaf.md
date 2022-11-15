<span  style="font-family: Simsun,serif; font-size: 17px; ">

### 页面包代码支持 

- 代码支持位置
~~~
private String staticPathPattern = "/**";
org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter.addResourceHandlers

### 优先级也是按照这个排序
private static final String[] CLASSPATH_RESOURCE_LOCATIONS = new String[]{"classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/"};
org.springframework.boot.autoconfigure.web.WebProperties.Resources
自定义
spring.mvc.static-path-pattern=/zdy
~~~
- webjars
~~~
http://localhost:8080/webjars/jquery/3.6.0/jquery.js
~~~

### templates

- 只能通过controller访问
- 放入templates下面即可
~~~
org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties
~~~
- 约束
~~~
<!--添加头部,即可使用thymeleaf表达式-->
<html lang="en" xmlns:th="http://www.thymeleaf.org">
~~~


### history

- 2019年已经删除默认支持icon了

</span>