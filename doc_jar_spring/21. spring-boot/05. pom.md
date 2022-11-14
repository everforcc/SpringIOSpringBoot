<span  style="font-family: Simsun,serif; font-size: 17px; ">

- 版本都在parent里面管理
- 启动器
~~~
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
~~~
- springboot将所用的功能场景变成启动器
- 使用什么功能找到对应启动器，导入就好

### @ConfigurationProperties
- 自动配置
- 导入jra包会有提示
- 一般用来导入配置文件，例如redis，mysql等

### 多环境配置，以及配置文件

### dependencies

1. scope
~~~
<!--
依赖范围。在项目发布过程中，帮助决定哪些构件被包括进来。欲知详情请参考依赖机制。 
- compile ：默认范围，用于编译 
- provided：类似于编译，但支持你期待jdk或者容器提供，类似于classpath 
- runtime: 在执行时需要使用 
- test: 用于test任务时使用 
- system: 需要外在提供相应的元素。通过systemPath来取得 
- systemPath: 仅用于范围为system。提供相应的路径 
- optional: 当项目自身被依赖时，标注依赖是否传递。用于连续依赖时使用 
-->
~~~
2. optional
~~~
true: <!--可选依赖，如果你在项目B中把C依赖声明为可选，你就需要在依赖于B的项目（例如项目A）中显式的引用对C的依赖。可选依赖阻断依赖的传递性。 -->


~~~






</span>