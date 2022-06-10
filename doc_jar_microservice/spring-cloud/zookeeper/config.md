<font face="Simsun" size=3>

- windows需要在官网下载，
- 3.5之后的需要带-bin 的才能直接运行
~~~
### dataDir=/tmp/zookeeper
### 修改为
dataDir=../data
### 端口
clientPort=2181
~~~
- jetty占用端口
~~~
# 默认8080
1. 删除jetty
2. 修改端口,两种
     启动脚本 -Dzookeeper.admin.serverPort=
     zoo.cfg admin.serverPort=
3. 停用
     启动脚本 -Dzookeeper.admin.enableServer=false
~~~

</font>