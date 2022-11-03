<span  style="font-family: Simsun,serif; font-size: 17px; ">

- [docker-hazelcast](https://hub.docker.com/r/hazelcast/management-center)

### 1. pull

~~~
docker pull hazelcast/hazelcast:5.2.0-SNAPSHOT
~~~

### 2. run

- 后台启动,带端口

~~~
docker run -d --name hazelcast520  -p 5701:5701 hazelcast/hazelcast:5.2.0-SNAPSHOT
~~~

- 管理界面

~~~
#拉取hazelcast管理镜像
docker pull hazelcast/management-center:5.1.3
docker run -d -p 9240:8080 hazelcast/management-center:5.1.3

#拉取hazelcast镜像
docker pull hazelcast/hazelcast:5.1.3
~~~

- 单节点

~~~
#单机单节点
docker run -d -e JAVA_OPTS="-Dhazelcast.local.publicAddress=192.168.153.131:5701 -Dhazelcast.rest.enabled=true -Xms128M -Xmx256M" -p 5701:5701 hazelcast/hazelcast:5.1.3
~~~

- 集群

~~~
docker run -d -e JAVA_OPTS="-Dhazelcast.local.publicAddress=0.0.0.0:9231 -Dhazelcast.rest.enabled=true -Xms128M -Xmx256M" -e MANCENTER_URL="http://0.0.0.0:9240/hazelcast-mancenter" -p 9231:5701 hazelcast/hazelcast:5.2.0-SNAPSHOT

docker run -d -e JAVA_OPTS="-Dhazelcast.local.publicAddress=0.0.0.0:9232 -Dhazelcast.rest.enabled=true -Xms128M -Xmx256M" -e MANCENTER_URL="http://0.0.0.0:9240/hazelcast-mancenter" -p 9232:5701 hazelcast/hazelcast:5.2.0-SNAPSHOT

docker run -d -e JAVA_OPTS="-Dhazelcast.local.publicAddress=0.0.0.0:9233 -Dhazelcast.rest.enabled=true -Xms128M -Xmx256M" -e MANCENTER_URL="http://0.0.0.0:9240/hazelcast-mancenter" -p 9233:5701 hazelcast/hazelcast:5.2.0-SNAPSHOT
~~~

</span>