<span  style="font-family: Simsun,serif; font-size: 17px; ">

### 一. 端口

- 记录下所用的端口,文件映射目录

| instantce     | port      | map     |
|--------------|---------------|------|
| consul | 8500 | / |
| es  | / | / |
| ftp  | 20 | 20 |
| hazelcast  | / | / |
| mysql-version  | / | / |
| rabbitmq  | 15672 | 15672 |
| redis  | / | / |
| zookeeper  | 2181 | / |

### 二. file

| instantce     | config      | data     | log |
|--------------|---------------|------|---|
| consul | / | / |
| es  | / | / |
| ftp  | 20/21/21100-21110 | 20/21/21100-21110 |
| hazelcast  | / | / |
| mysql-version  | / | / |
| rabbitmq  | / | / |
| redis  | / | / |
| zookeeper  | / | / |

#### ftp

- 数据
- /home/data/ftp/data:/home/vsftpd
- 日志
- /home/data/ftp/log:/var/log/vsftpd

#### mysql

- 8.0
- /home/data/mysql/mysql8.0/var/lib/mysql:/var/lib/mysql

#### nginx

#### redis

- /home/data/redis-singleton:/data
- todo 单例的调整这个文件夹
- /home/data/redis/redis-singleton:/data

</span>