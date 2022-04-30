<font face="Simsun" size=3>

- 父子依赖
- 标签 
~~~
relativePath
1. 指定查找该父项目pom.xml的(相对)路径。默认顺序：relativePath > 本地仓库 > 远程仓库
2. 没有relativePath标签等同…/pom.xml, 即默认从当前pom文件的上一级目录找
3. 表示不从relativePath找, 直接从本地仓库找,找不到再从远程仓库找
~~~
- 冲突解决
~~~
在新引入的jar包里面除去冲突
<exclusions>
    <exclusion>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
    </exclusion>
</exclusions>
~~~

### dependency 

- dependencyManagement
~~~
dependencyManagement 父pom
让子项目中引用一个依赖而不用显示的列出版本号，会沿着父子层向上走，直到拥有
dependencyManagement 的项目，然后用定义好的版本号

需要修改的话，只需要在父项目修改就可以
只是生命依赖，并不引入，只是提供一个规范
~~~
- dependencies
~~~
~~~
- dependency
~~~
~~~

### 跳过单元测试

- 
~~~
toggle skip test model
~~~

### 内存溢出

</font>