<span  style="font-family: Simsun,serif; font-size: 17px; ">

## yml参数配置



### 1. url 参数

key | value
---|---
useUnicode | true
characterEncoding | UTF-8
serverTimezone | 时区
useSSL=false | 为了符合不使用SSL的现有应用程序

- 时区可选清单
- GMT
- UTC


### 2. driver

- 8以及以上版本有两个driver
- 8版本的com.mysql.cj.jdbc.Driver
- 

### 3. druid

- 读取yml的属性类
~~~
com.alibaba.druid.pool.DruidAbstractDataSource

~~~

### 4. mabatis

~~~
mybatis:
  # 指定全局配置文件位置  便于管理和扫描
  config-location: classpath:mybatis/mybatis-config.xml
  # 指定sql映射文件位置
  mapper-locations: classpath*:mybatis/mapper/**/*.xml
  # mapper-locations: classpath*:mybatis/mapper/**/*.xml
~~~

### 5.日志

~~~
mybatis:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl # 输出日志，参数可以选择不同的实现
~~~

### 6. 异常信息

~~~
Fri May 27 08:44:55 CST 2022 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
~~~

</span>