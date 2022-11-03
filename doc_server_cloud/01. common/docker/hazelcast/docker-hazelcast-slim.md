<span  style="font-family: Simsun,serif; font-size: 17px; ">

### management

#### 1. pull

~~~
-- 
docker pull hazelcast/hazelcast:5.1.4-slim
docker pull hazelcast/hazelcast:5.2.0-SNAPSHOT-slim
~~~

#### 2. run

- 简单启动下测试

~~~
docker run -d --name hazelcast520  -p 5701:5701 hazelcast/hazelcast:5.2.0-SNAPSHOT
docker run -d --name hazelcast514-slim -p 5701:5701 -e HZ_CLUSTERNAME=dev hazelcast/hazelcast:5.1.4-slim
docker run -d --name hazelcast520-slim -p 5701:5701 hazelcast/hazelcast:5.2.0-SNAPSHOT-slim
~~~
- 参数
~~~
-e HZ_CLUSTERNAME=dev
~~~

</span>