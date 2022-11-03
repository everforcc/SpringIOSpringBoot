<span  style="font-family: Simsun,serif; font-size: 17px; ">

- [官网](https://hub.docker.com/_/zookeeper)
~~~
3.5.9, 3.5
3.6.3, 3.6
3.7.0, 3.7
3.8.0, 3.8, latest
~~~

### 1. 下载

~~~
docker pull zookeeper:3.5
~~~

### 2. 启动

~~~
docker run --name zookeeper3.5  -p 2181:2181 \
 --restart=always \
 -d zookeeper:3.5
~~~

### 3. docker compose

~~~
version: '3.1'

services:
  zoo1:
    image: zookeeper
    restart: always
    hostname: zoo1
    ports:
      - 2181:2181
    environment:
      ZOO_MY_ID: 1
      ZOO_SERVERS: server.1=zoo1:2888:3888;2181 server.2=zoo2:2888:3888;2181 server.3=zoo3:2888:3888;2181

  zoo2:
    image: zookeeper
    restart: always
    hostname: zoo2
    ports:
      - 2182:2181
    environment:
      ZOO_MY_ID: 2
      ZOO_SERVERS: server.1=zoo1:2888:3888;2181 server.2=zoo2:2888:3888;2181 server.3=zoo3:2888:3888;2181

  zoo3:
    image: zookeeper
    restart: always
    hostname: zoo3
    ports:
      - 2183:2181
    environment:
      ZOO_MY_ID: 3
      ZOO_SERVERS: server.1=zoo1:2888:3888;2181 server.2=zoo2:2888:3888;2181 server.3=zoo3:2888:3888;2181
~~~

</span>