<span  style="font-family: Simsun,serif; font-size: 17px; ">

### 系统架构

- mysql8.0 + netty + swing + token + redis + maven

### 模块划分

- 登录模块
- 好友模块
- 聊天模块

### 对象

- 用户

| 字段 | 说明 |
| --- | --- |
| id | 唯一id |
| account | 账号 |
| name | 用户名 |
| password | 密码 |

- 好友列表

| 字段 | 说明 |
| --- | --- |
| id | 唯一id |
| user_id | 用户id |
| friend_id | 好友id |

- 聊天记录

| 字段 | 说明 |
| --- | --- |
| id | 唯一id |
| user_id | 用户id |
| friend_id | 好友id |
| msg | 聊天信息 |
| create_time | 创建时间 |

### 业务逻辑说明

- 用户可以根据账号密码登陆
- 用户可以根据账号加好友
- 用户可以根据账号和好友账号聊天
- 用户可以根据账号查看聊天记录
- 可以退出登录

### 服务端

- netty ++ swing + token + redis
- 服务端需要能打包

### 客户端

- swing
- 客户端需要能打包
- 需要美化一下界面

### mysql

- 需要生成数据表

</span>

