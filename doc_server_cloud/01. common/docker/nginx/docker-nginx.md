<span  style="font-family: Simsun,serif; font-size: 17px; ">

### nginx

- [参考-docker安装nginx](https://blog.csdn.net/weixin_46244732/article/details/114315708)
- [docker-hub-nginx](https://hub.docker.com/_/nginx)

#### 一. 下载镜像

~~~
docker pull nginx:1.23.2
~~~

#### 二. 生成配置文件

- 1.普通启动

~~~
docker run  --name nginx -m 200m -p 80:80  -d nginx
~~~

- 2.copy生成配置文件
- 3.也复制到隔壁目录一份/可以直接用

~~~
cd /home/test
mkdir nginx
docker container cp nginx:/etc/nginx .
cp -r /home/test/nginx/. /home/data/nginx/nginx80/conf
~~~

#### 三. 带参数启动

##### 1.map文件路径

~~~
-- html
/usr/share/nginx/html
-- config
/etc/nginx
-- log
/var/log/nginx
-- confd/暂时不需要
/etc/nginx/conf.d
~~~

##### 2.参数说明

~~~
参数说明
-name  给你启动的容器起个名字，以后可以使用这个名字启动或者停止容器
-p 　　  映射端口，将docker宿主机的80端口和容器的80端口进行绑定
-v 　　　挂载文件用的，
-m 200m 分配内存空间
-e TZ=Asia/Shanghai  设置时区
第一个-v 表示将你本地的nginx.conf覆盖你要起启动的容器的nginx.conf文件，
第二个-v 表示将日志文件进行挂载，就是把nginx服务器的日志写到你docker宿主机的/home/docker-nginx/log/下面
第三个-v 表示的和第一个-v意思一样的。
-d 表示启动的是哪个镜像
--restart 自动重启
--privileged root权限? todo
~~~

##### 3.带参数启动

~~~
docker run  --name nginx80 -m 200m -p 80:80 \
-v /home/data/nginx/nginx80/html:/usr/share/nginx/html \
-v /home/data/nginx/nginx80/conf:/etc/nginx \
-v /home/data/nginx/nginx80/logs:/var/log/nginx \
-e TZ=Asia/Shanghai \
--restart=always    \
--privileged=true -d nginx
~~~

</span>