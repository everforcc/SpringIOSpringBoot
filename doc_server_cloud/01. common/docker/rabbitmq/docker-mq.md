<span  style="font-family: Simsun,serif; font-size: 17px; ">

### 官网

- [rabbitmq](https://hub.docker.com/_/rabbitmq)
- [参考](https://www.cnblogs.com/yufeng218/p/9452621.html)
- 支持版本，management带有web管理页面

~~~
3.10.0-rc.4, 3.10-rc
3.10.0-rc.4-management, 3.10-rc-management
3.10.0-rc.4-alpine, 3.10-rc-alpine
3.10.0-rc.4-management-alpine, 3.10-rc-management-alpine
3.9.15, 3.9, 3, latest
3.9.15-management, 3.9-management, 3-management, management
3.9.15-alpine, 3.9-alpine, 3-alpine, alpine
3.9.15-management-alpine, 3.9-management-alpine, 3-management-alpine, management-alpine
3.8.29, 3.8
3.8.29-management, 3.8-management
3.8.29-alpine, 3.8-alpine
3.8.29-management-alpine, 3.8-management-alpine
~~~

### pull

~~~
docker pull rabbitmq:3.10-rc-management
~~~

### 启动

- 还没映射目录

~~~
docker run -d --name rabbitmq310 -p 5672:5672 -p 15672:15672 -v `pwd`/data:/var/lib/rabbitmq --hostname myRabbit -e RABBITMQ_DEFAULT_VHOST=my_vhost  -e RABBITMQ_DEFAULT_USER=c.c. -e RABBITMQ_DEFAULT_PASS=c.c.c.c. rabbitmq:3.10-rc-management
~~~

- err

~~~
docker run -d --name rabbitmq310 -p 5672:5672 -p 15672:15672   \
-v /home/rabbitmq/etc/rabbitmq:/etc/rabbitmq -v /home/rabbitmq/lib/rabbitmq:/var/lib/rabbitmq -v /home/rabbitmq/log/rabbitmq/:/var/log/rabbitmq --hostname myRabbit -e RABBITMQ_DEFAULT_VHOST=my_vhost  -e RABBITMQ_DEFAULT_USER=cc -e RABBITMQ_DEFAULT_PASS=c.c.c.c. rabbitmq:3.10-rc-management
~~~

### 访问

- [本地](127.0.0.1:15672)

~~~
一定要输入对用户名密码，否则就会报错
您与***不是私密链接
c.c.
c.c.c.c.
~~~

### 后续配置

- 参考 doc_middleware

</span>