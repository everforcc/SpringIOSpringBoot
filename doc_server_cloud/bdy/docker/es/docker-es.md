<span  style="font-family: Simsun,serif; font-size: 17px; ">


[TOC]

- [官网](https://hub.docker.com/_/elasticsearch)

### 1. 拉镜像

~~~
docker pull elasticsearch:7.17.3

8.1.3
7.17.3
6.8.23
~~~

### 2. 本机目录

~~~
sudo mkdir -p /opt/elasticsearch/config
sudo mkdir -p /opt/elasticsearch/data
sudo mkdir -p /opt/elasticsearch/plugins
~~~

### 3. 配置文件

~~~
sudo echo "http.host: 0.0.0.0" >> /opt/elasticsearch/config/elasticsearch.yml
~~~


### 4. 创建容器

~~~
docker run --name elasticsearch7 -p 9200:9200  -p 9300:9300 \
 --restart=always \
 -e "discovery.type=single-node" \
 -e ES_JAVA_OPTS="-Xms84m -Xmx512m" \
 -v /opt/elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml \
 -v /opt/elasticsearch/data:/usr/share/elasticsearch/data \
 -v /opt/elasticsearch/plugins:/usr/share/elasticsearch/plugins \
 -d elasticsearch:7.17.3
~~~

### 5. 启动测试

~~~
docker start name
如果无法访问，保证其他镜像能重启的情况下，重启docker

curl http://127.0.0.1:9200/
curl http://180.76.156.43:9200/
~~~

</span>