use mysql;
select host, user from user;
-- 因为mysql版本是5.7，因此新建用户为如下命令：
create user cceverfor identified by '5664c.c.';
-- 将docker_mysql数据库的权限授权给创建的cceverfor用户，密码为5664c.c.
grant all on oneforall.* to cceverfor@'%' identified by '5664c.c.' with grant option;
-- 这一条命令一定要有：
flush privileges;