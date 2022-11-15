<span  style="font-family: Simsun,serif; font-size: 17px; ">

#### IOC

- Inversion of Control，缩写为IoC

将设计好的对象交给容器控制，而不是直接在对象内部控制
~~~
A a = new AImpl();
ioc
加上注解
在xml配置，通过反射在编译期获取

~~~

- 节省资源，多个service层可以使用同一个对象，没必要创建多个
- 如果实现类变化，修改方便

~~~java
public class UserServiceImpl implements UserService{
    ...
}

// 将该实现类声明为 Bean
@Component
public class OtherUserServiceImpl implements UserService{
    ...
}
~~~


</span>