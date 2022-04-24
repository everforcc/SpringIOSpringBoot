<font face="Simsun" size=3>

~~~
ConcurrentHashMap
~~~

1. HashMap的内部数据结构
   数组 + 链表/红黑树

2. HashMap允许空键空值么
   HashMap最多只允许一个键为Null(多条会覆盖)，但允许多个值为Null

3. 影响HashMap性能的重要参数
   初始容量：创建哈希表(数组)时桶的数量，默认为 16
   负载因子：哈希表在其容量自动增加之前可以达到多满的一种尺度，默认为 0.75

4. HashMap的工作原理
   HashMap是基于hashing的原理，我们使用put(key, value)存储对象到HashMap中，使用get(key)从HashMap中获取对象

5. 

6.HashMap 的底层数组长度为何总是2的n次方
- HashMap根据用户传入的初始化容量，利用无符号右移和按位或运算等方式计算出第一个大于该数的2的幂。
- `使数据分布均匀，减少碰撞
当length为2的n次方时，h&(length - 1) 就相当于对length取模，而且在速度、效率上比直接取模要快得多

9. 为什么是16
- 2的倍数,太小了就有可能频繁发生扩容，影响效率。太大了又浪费空间，不划算。

</font>