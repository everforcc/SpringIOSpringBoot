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


</font>