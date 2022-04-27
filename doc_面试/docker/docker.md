<font face="Simsun" size=3>

- Docker 容器在应用程序层创建抽象并将应用程序及其所有依赖项打包在一起

### 简介
~~~
虚拟化:

虚拟化帮助我们在单个物理服务器上运行和托管多个操作系统

容器化:

容器化为我们提供了一个独立的环境来运行我们的应用程序。

~~~

### 基础操作

~~~
docker  start
        stop
        kill
## 访问
docker exec -it <container_id> bash

## 列出清单
docker ps

~~~

### compose启动顺序

- 在继续下一个容器之前不会等待容器准备就绪。为了控制我们的执行顺序，我们可以使用“取决于”条件
~~~
depends_on:

~~~

### 生命周期

~~~
创建容器
运行容器
暂停容器（可选）
取消暂停容器（可选）
启动容器
停止容器
重启容器
杀死容器
销毁容器
~~~


### dockerfile

- Dockerfile 是一个文本文件，其中包含我们需要运行以构建 Docker 映像的所有命令。
  Docker 使用 Dockerfile 中的指令自动构建镜像。
  我们可以docker build用来创建按顺序执行多个命令行指令的自动构建。

### 如何从 Docker 镜像创建 Docker 容器

- docker run -it -d <image_name>

### Docker Compose 可以使用 JSON 代替 YAML 吗

-  docker-compose -f docker-compose.json up


</font>