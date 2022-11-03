<span  style="font-family: Simsun,serif; font-size: 17px; ">

### 一. mysql

- [文档](https://hub.docker.com/_/mysql)

#### 恢复脚本

- 如果要恢复脚本
- 命名为 privileges.sql
- 放到目录下,启动后执行过去

#### - 8.0

~~~
# 1. build
docker build -t mysql8.0 -f Dockerfile-mysql8.0 .

# 2.  正确 没有命名 --name mysql-8.0
docker run -p 3308:3306 -d --name mysql8.0 --restart=always -e MYSQL_ROOT_PASSWORD=c.c.5664  -v /home/data/mysql/mysql8.0/var/lib/mysql:/var/lib/mysql mysql8.0 

# 3. 进入容器
docker exec -it b5e5c6b9888c bash

# 4. 登录
mysql  -u root -pc.c.5664
#c.c.5664/ 本地不需要密码

# 5. 执行恢复数据脚本
source /mysql/privileges.sql;
source /mysql/backup.sql;
~~~

#### - 5.7

~~~
# 1. build
docker build -t mysql5.7 -f Dckerfile-mysql5.7 .

# 2. 挂载到本地数据 --name mysql-5.7
docker run -p 3307:3306 -d --name mysql8.0 --restart=always -e MYSQL_ROOT_PASSWORD=c.c.5664 -v /home/data/mysql/mysql5.7/var/lib/mysql:/var/lib/mysql -v /home/data/mysql/mysql5.7/etc/mysql:/etc/mysql mysql5.7

# 3. 进入容器
docker exec -it name bash

# 4. 登录
mysql  -u root -p
c.c.5664

# 5. 执行恢复数据脚本
source /mysql/privileges.sql
source /mysql/backup.sql
~~~

</span>