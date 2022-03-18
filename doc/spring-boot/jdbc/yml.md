<font face="Simsun" size=3>

## yml参数配置



### 1. url 参数

key | value
---|---
useUnicode | true
characterEncoding | UTF-8
serverTimezone | 时区

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

</font>