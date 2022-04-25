<font face="Simsun" size=3>

### 一级缓存

- 一级缓存默认开启，一级缓存是和SqlSession绑定的，只存在于SQLSession的生命周期中，且任何的修改操作都会清空一级缓存。


### 二级缓存


- 二级缓存可以理解为存在于SqlSessionFactory的生命周期中，需要配置才能生效，且只有当调用SqlSession的close方法后，SqlSession才会保存查询数据到二级缓存中，在这之后才有了缓存数据。
~~~
修改二级缓存的默认配置可在Mapper接口中或者xml文件中配置，
Mapper接口中可以用cachenamespace标签配置，
xml中可以使用cache标签配置，
且两者同时配置时会报错。
~~~


</font>