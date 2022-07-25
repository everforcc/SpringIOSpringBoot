<span  style="font-family: Simsun,serif; font-size: 17px; ">

[TOC]

### 1. of

~~~
T 类型，可以创建空对象
~~~

### 2. ofNullable

~~~
为指定的值创建一个Optional，如果指定的值为null，则返回一个空的Optional
~~~

### 3. 创建空 Optional

### 4. isPresent
### 5. get

### 6. ifPresent

如果Optional实例有值则为其调用consumer，否则不做处理

...


### 如何使用

1. 存在即返回, 无则提供默认值
~~~
return user.orElse(null);  //而不是 return user.isPresent() ? user.get() : null;
return user.orElse(UNKNOWN_USER); 
~~~
2. 存在即返回, 无则由函数来产生
~~~
return user.orElseGet(() -> fetchAUserFromDatabase()); //而不要 return user.isPresent() ? user: fetchAUserFromDatabase();
~~~
3. 存在才对它做点什么
~~~
user.ifPresent(System.out::println);

//而不要下边那样
if (user.isPresent()) {
  System.out.println(user.get());
}
~~~
4. map 函数隆重登场
~~~
return user.map(u -> u.getOrders()).orElse(Collections.emptyList())

//上面避免了我们类似 Java 8 之前的做法
if(user.isPresent()) {
  return user.get().getOrders();
} else {
  return Collections.emptyList();
}
~~~
- map 是可能无限级联的, 比如再深一层, 获得用户名的大写形式
~~~
return user.map(u -> u.getUsername())
           .map(name -> name.toUpperCase())
           .orElse(null);
~~~

使用 Optional 时尽量不直接调用 Optional.get() 方法, Optional.isPresent() 更应该被视为一个私有方法, 应依赖于其他像 Optional.orElse(), Optional.orElseGet(), Optional.map() 等这样的方法.

</span>