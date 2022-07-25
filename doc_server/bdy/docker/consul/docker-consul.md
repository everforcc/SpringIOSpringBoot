<span  style="font-family: Simsun,serif; font-size: 17px; ">

[TOC]

### 官网

- [官网](https://hub.docker.com/_/consul)
~~~
1.12.0, 1.12, latest
1.11.5, 1.11
1.10.10, 1.10
~~~

### 2. 下载

~~~
windows下官网的下载很慢
docker pull consul:1.10
~~~

### 3. 启动

~~~
docker run --name consul.1.10  -p 8500:8500 \
 -d consul:1.10
~~~


</span>