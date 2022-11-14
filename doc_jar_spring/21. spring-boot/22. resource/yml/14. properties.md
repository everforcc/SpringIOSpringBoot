<span  style="font-family: Simsun,serif; font-size: 17px; ">

- properties和yml的配置
- [文档](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features)
- [config文档](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config.typesafe-configuration-properties)
- [提示缺少pom](https://docs.spring.io/spring-boot/docs/2.3.1.RELEASE/reference/html/appendix-configuration-metadata.html#configuration-metadata-annotation-processor)

~~~
key: value
list: [v1,v2,v3]
list:
 - v1
 - v2
 - v3
map: {k1: v1,k2: v2}
~~~

- yml 可以给实体类赋值
- [松散绑定-对应大写之类的-Relaxed Binding](https://docs.spring.io/spring-boot/docs/2.6.4/reference/htmlsingle/#features.external-config.typesafe-configuration-properties.relaxed-binding)

### 环境变量注入值

- [spring reference](https://docs.spring.io/spring-boot/docs/2.6.4/reference/htmlsingle/#features.external-config.application-json)

~~~
### 启动时加入环境变量，替换yml内的数据
java -jar sp01-init-0.0.1-SNAPSHOT.jar --spring.application.json="{\"dog\":{\"name\":\"test\"}}"
~~~

### 多环境切换

1. active

~~~
spring:
  profiles:
    active: bdy # 用来激活不同的配置
~~~

- 高版本过期了不建议用

~~~
---

server:
  port: 8081
spring:
  profiles: dev # 已经过期了
~~~

- 切换环境的方式

~~~
jar   --环境变量 的时候环境变量
docker 的时候环境变量
pom修改 
yml修改 
maven profile选择
~~~

</span>