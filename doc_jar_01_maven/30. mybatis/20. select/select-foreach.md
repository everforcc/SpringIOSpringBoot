<span  style="font-family: Simsun,serif; font-size: 17px; ">

### foreach

~~~
item表示集合中每一个元素进行迭代时的别名，
index指定一个名字，用于表示在迭代过程中，每次迭代到的位置，
open表示该语句以什么开始，
separator表示在每次进行迭代之间以什么符号作为分隔符，
close表示以什么结束，
collection
    1. 如果传入的是单参数且参数类型是一个List的时候，collection属性值为list .
    2. 如果传入的是单参数且参数类型是一个array数组的时候，collection的属性值为array .
    3. 如果传入的参数是多个的时候，我们就需要把它们封装成一个Map了，当然单参数也可以封装成map，实际上如果你在传入参数的时候，在MyBatis里面也是会把它封装成一个Map的，map的key就是参数名，所以这个时候collection属性值就是传入的List或array对象在自己封装的map里面的key
~~~

</span>