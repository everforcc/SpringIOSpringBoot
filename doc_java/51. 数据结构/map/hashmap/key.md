<span  style="font-family: Simsun,serif; font-size: 17px; ">

- 我们知道Map以键值对的形式来存储数据。有一点值得说明的是，如果要使用我们自己的类作为键，我们必须同时重写hashCode() 和 equals()两个方法。HashMap使用equals方法来判断当前的键是否与表中的键相同。equals()方法需要满足以下5个条件
  - 自反性 x.equals(x) 一定返回true
  - 对称性 x.equals(y)返回true，则y.equals(x) 也返回true
  - 传递性 x.equals(y)返回true,y.equals(z)返回true,则x.equals(y)返回true
  - 一致性 如果对象中的信息没有改变，x.equals(y)要么一直返回true，要么一直返回false
  - 对任何不是null的x，想x.equals(null)一定返回false

</span>