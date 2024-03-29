<span  style="font-family: Simsun,serif; font-size: 17px; ">

### 几种模式

- [参考](https://zhuanlan.zhihu.com/p/257845445?utm_source=wechat_session)

#### 主从 sentinel.conf

- 优点:
  - master能自动将数据同步到slave，可以进行读写分离，分担master的读压力
  - slave可以接受其他slave的同步请求，分担主节点压力
  - 主节点以非阻塞的方式为slave提供服务，所以在同步期间，客户端仍然可以提交查询或修改请求
  - slave是以非阻塞方式数据同步，同步期间，返回同步之前的数据
- 缺点:     
  - 不具备容错和恢复功能，主机从机的宕机都会导致前段部分读写请求失败
  - 主机宕机，宕机前未同步的数据，切换ip后会导致数据不一致
  - 难以在线扩容，取决于单机的上限
                 

#### 哨兵 基于主从复制

 高可用，读写分离

- 优点:
  - 哨兵模式是基于主从模式的，所有主从的优点，哨兵模式都具有
  - 主从可以自动切换  
- 缺点: 
  - 较难支持在线扩容，在集群容量上限时在线扩容会变得很复杂

- 不一致问题
- 场景描述，对于主从库，读写分离，如果主从库更新同步有时差，就会导致主从库数据的不一致。
1. 忽略这个数据不一致，在数据一致性要求不高的业务下，未必需要时时一致性。
2. 强制读主库，使用一个高可用的主库，数据库读写都在主库，添加一个缓存，提升数据读取的性能。
3. 选择性读主库，添加一个缓存，用来记录必须读主库的数据，将哪个库，哪个表，哪个主键，作为缓存的key,设置缓存失效的时间为主从库同步的时间，如果缓存当中有这个数据，直接读取主库，如果缓存当中没有这个主键，就到对应的从库中读取。

- 问题原因：
- 网络信息不同步，数据发送有延迟

- 根本上解决：
1. 优化主从间的网络环境，通常放置在同一个机房部署，如使用阿里云等云服务器时要注意此现象
2. 监控主从节点延迟（通过offset）判断，如果slave延迟过大，暂时屏蔽程序对该slave的数据访问


#### 集群(直连)

Redis-Cluster采用无中心结构,它的特点如下：

- 优点:     
  - 节点间(Ping-Pong机制),二进制协议，传输速度快
  - 无中心结构，链接任意一个节点即可
  - 节点的fail是通过集群中超过半数的节点检测失败时才生效
  - 支持扩容
  -  
      1、资源隔离型较差，容易出现相互影响的情况。
      2、数据通过异步复制，不保证数据的一致性。

---

### 数据一致性 

主从复制：

- redis的复制功能是支持多个数据库之间的数据同步。一类是主数据库（master）一类是从数据库（slave），
- 主数据库可以进行读写操作，当发生写操作的时候自动将数据同步到从数据库，
- 而从数据库一般是只读的，并接收主数据库同步过来的数据，
- 一个主数据库可以有多个从数据库，而一个从数据库只能有一个主数据库。
- 通过redis的复制功能可以很好的实现数据库的读写分离，提高服务器的负载能力。主数据库主要进行写操作，而从数据库负责读操作。
1. 当一个从数据库启动时，会向主数据库发送sync命令，
2. 主数据库接收到sync命令后会开始在后台保存快照（执行rdb操作），并将保存期间接收到的命令缓存起来
3. 当快照完成后，redis会将快照文件和所有缓存的命令发送给从数据库。
4. 从数据库收到后，会载入快照文件并执行收到的缓存的命令



</span>