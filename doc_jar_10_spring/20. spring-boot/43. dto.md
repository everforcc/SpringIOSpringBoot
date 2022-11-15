<span  style="font-family: Simsun,serif; font-size: 17px; ">

- [x] serialVersionUID 版本

~~~
实现java.io.Serializable这个接口是为序列化,serialVersionUID 用来表明实现序列化类的不同版本间的兼容性。如果你修改了此类, 要修改此值。否则以前用老版本的类序列化的类恢复时会出错。
实现后如果你用的是工具的话会出来警告,他会提示你,可以自动生成private static final long serialVersionUID = 1L;
为了在反序列化时，确保类版本的兼容性，最好在每个要序列化的类中加入private static final long serialVersionUID这个属性，具体数值自己定义.
~~~

</span>