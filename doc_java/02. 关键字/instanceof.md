<span  style="font-family: Simsun,serif; font-size: 17px; ">

- 父类.class.isAssignableFrom(子类.class)
- 子类实例 instanceof 父类类型
-

~~~
private static void check(){
        Obj obj = new Obj();
        Object object = new Object();
        // true
        if(obj instanceof Obj){
            System.out.println("object");
        }
        // false
        if(Obj.class.isAssignableFrom(Object.class)){
            System.out.println("1 isAssignableFrom");
        }
        // true
        if(Object.class.isAssignableFrom(Object.class)){
            System.out.println("2 isAssignableFrom");
        }
        // true
        if(Object.class.isAssignableFrom(Obj.class)){
            System.out.println("3 isAssignableFrom");
        }
}
~~~

</span>