<span  style="font-family: Simsun,serif; font-size: 17px; ">

1. 关于抽象类

~~~
1、抽象类和抽象方法都必须用abstract修饰符来修饰，抽象方法不能有方法体
2、抽象类有构造器，但不能直接被实例化，要创建对象涉及向上转型，主要是用于被其子类调用
3、抽象类中可以没有抽象方法，但是有抽象方法的类必定是抽象类
4、抽象类中可以包含静态方法
5、抽象类不能用final修饰
6、抽象类不能用private修饰
7、抽象类也是类，一样是用来继承的，接口与类才是实现关系
8、外部抽象类不能用Static修饰，但内部的抽象类可以使用static声明
9、 抽象类可以继承抽象类，抽象派生类可以覆盖基类的抽象方法，也可以不覆盖。如果不覆盖，则其具体非抽象派生类必须覆盖它们
10、 抽象类可以实现接口，可以把接口中方法映射到抽象类中作为抽象方法而不必实现，而在抽象类的子类中实现接口中方法
~~~

2. 普通类

~~~
构造方法
普通方法
static方法
常量
变量
等
~~~
- 抽象类是指在普通类的结构里面选择性的增加抽象方法并以abstract关键字将该类修饰

3. 不能存在的关键字

~~~
final      因为需要被继承
private
static
~~~
~~~java
// 内部抽象类
//定义一个抽象类A
abstract class A{
   //定义一个内部抽象类B
    static abstract class B{  //static定义的内部类属于外部类
        public abstract void saoMethod();
    }
}

class C extends A.B{

    public void saoMethod(){
        System.out.println("======saoMethod方法执行了======");
    }
}
public class StaticDemo {

    public static void main(String[] args) {
        A.B ab = new C();//向上转型
        ab.saoMethod();
    }

}

//运行结果：  ======saoMethod方法执行了======
~~~

4. 接口

~~~
1.抽象类可以有构造方法，接口中不能有构造方法。

2.抽象类中可以有普通成员变量，接口中没有普通成员变量

3.抽象类中可以包含非抽象的普通方法，接口中的可以有非抽象方法，比如deaflut方法

4.抽象类中的抽象方法的访问类型可以是public，protected和（默认类型,虽然 eclipse下不报错，但应该也不行），但接口中的抽象方法只能是public类型的，并且默认即为public abstract类型。

5.抽象类中可以包含静态方法，接口中不能包含静态方法

6.抽象类和接口中都可以包含静态成员变量，抽象类中的静态成员变量的访问类型可以任意，但接口中定义的变量只能是public static final类型，并且默认即为public static final类型。

7.一个类可以实现多个接口，但只能继承一个抽象类。
~~~

5. 情况

- 抽象类，固有属性
- 接口，便于扩展

</span>