<span  style="font-family: Simsun,serif; font-size: 17px; ">

- 数据源相关
~~~yml
spring:
  datasource:
    url: jdbc:mysql://180.76.156.43:3308/oneforall?characterEncoding=UTF-8&&serverTimezone=GMT&useSSL=false # 编码和时区 &userSSL=false
    username: cceverfor
    password: 5664c.c.
    driver-class-name: com.mysql.cj.jdbc.Driver
#    hikari:
#      schema: classpath:db/schema.sql
#      data: classpath:db/data.sql
    initialization-mode: always
    schema:
      - classpath:db/schema.sql
      - classpath:db/data.sql

mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
~~~

</span>