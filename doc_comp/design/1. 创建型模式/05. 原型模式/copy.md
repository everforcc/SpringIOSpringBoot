<span  style="font-family: Simsun,serif; font-size: 17px; ">

- 浅拷贝
- 只copy本身，而不copy对象包含的其他对象
- 修改copy对象的引用，原对象的引用也会变

~~~java
//implements Cloneable

@Override
protected Object clone() throws CloneNotSupportedException {
    return super.clone();
}
~~~

---

- 深拷贝
- copy对象，也copy对象包含的引用指向的所有对象
- x包含yz，copy -> x1,y1,z1

~~~java
//implements Cloneable

@Override
protected Object clone() throws CloneNotSupportedException {
    MyFile2 file = (MyFile2) super.clone();
    file.info = (Info) file.info.clone();
    return file;
}
~~~
- 如果内部引用多，非常麻烦，使用序列化的方式
~~~

~~~
- 序列化需要实现Serializable接口，什么都不用做，写了就好



</span>