<span  style="font-family: Simsun,serif; font-size: 17px; ">

### pro 

~~~
sys.driver = com.mysql.jdbc.Driver
# 数据库链接地址
sys.url = jdbc:mysql://localhost:3306/database?useUnicode=true&characterEncoding=gbk
# 数据库用户名
sys.username = root
# 数据库密码
sys.password = pas
~~~
- jdbc写法
~~~
jdbc:oracle:thin:@//<host>:<port>/<service_name>
jdbc:oracle:thin:@//192.1.1.45:1521/oa
~~~

### xml

~~~xml
<?xml version="1.0" encoding="utf-8"?>

<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
  <property name="driverClassName" value="${driver}"/>  
  <property name="url" value="${url}"/>  
  <property name="username" value="${username}"/>  
  <property name="password" value="${password}"/>  
  <!--initialSize: 初始化连接-->  
  <property name="initialSize" value="15"/>  
  <!--maxIdle: 最大空闲连接-->  
  <property name="maxIdle" value="10"/>  
  <!--minIdle: 最小空闲连接-->  
  <property name="minIdle" value="5"/>  
  <!--maxActive: 最大连接数量-->  
  <property name="maxActive" value="100"/>  
  <!--removeAbandoned: 是否自动回收超时连接-->  
  <property name="removeAbandoned" value="true"/>  
  <!--removeAbandonedTimeout: 超时时间(以秒数为单位)-->  
  <property name="removeAbandonedTimeout" value="180"/>  
  <!--maxWait: 超时等待时间以毫秒为单位 6000毫秒/1000等于60秒-->  
  <property name="maxWait" value="30000"/>  
  <property name="poolPreparedStatements" value="false"/>  
  <property name="defaultAutoCommit" value="true"/>  
  <property name="testWhileIdle" value="true"/>  
  <property name="testOnBorrow" value="false"/>  
  <property name="testOnReturn" value="false"/>  
  <property name="validationQuery">
    <value>SELECT 1</value>
  </property>  
  <property name="timeBetweenEvictionRunsMillis" value="30000"/>  
  <property name="minEvictableIdleTimeMillis" value="1800000"/>  
  <property name="numTestsPerEvictionRun" value="10"/>
</bean>
~~~
---
~~~
<property  name="testWhileIdle"  value="true"  /> 建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效

<property name="timeBetweenEvictionRunsMillis" value="30000" />每30秒运行一次空闲连接回收器
<property name="minEvictableIdleTimeMillis"  value="1800000"/>空闲连接30分钟后被收回
<property name="numTestsPerEvictionRun"  value="10"/> 在每次空闲连接回收器线程运行时，检查的连接数量


validationQuery   用来检测连接是否有效的sql，要求是一个查询语句。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会其作用。
testOnBorrow true 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
testOnReturn false 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
testWhileIdle false 建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。

poolPreparedStatements false 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。

默认配置的DBCP不会对数据库的连接做测试，有时数据的连接已经断开了，但连接池不知道，还以为是可用的，当应用程序从连接池中取到这种连接的时候就会报错。我们可以使用DBCP的数据库连接定时检查机制，按照以上配置，DBCP连接池可以定时检测连接，如果失败，可以自动重新连接。

参考：https://www.cnblogs.com/wuyun-blog/p/5679073.html
~~~


</span>