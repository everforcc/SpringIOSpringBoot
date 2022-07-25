<span  style="font-family: Simsun,serif; font-size: 17px; ">

- 缓存机制
~~~
@Test
    public void t2() throws Exception {
        Class cache = Integer.class.getDeclaredClasses()[0];
        Field c = cache.getDeclaredField("cache");
        c.setAccessible(true);
        Integer[] array = (Integer[]) c.get(cache);
        System.out.println(array.length);
// array[129] is 1
        array[130] = array[129];
// Set 2 to be 1
        array[131] = array[129];
// Set 3 to be 1
        Integer a = 1;
//        if(a == (Integer)1 && a == (Integer)2 && a == (Integer)3){
//            System.out.println("Success");
//        }
        Integer b = 2;
        if(a == b){ 
            System.out.println("Success");
        }
    }
~~~

---

~~~
public void t2(){
    Integer a = 1000;
    Integer b = 1000;
    System.out.println(a==b);
    Integer c = 100;
    Integer d = 100;
    System.out.println(c==d);
}
~~~

---

~~~
@Test
public void t3(){
    Integer a = new Integer(1000);
    Integer b = a;
    System.out.println(a==b);
    Integer c = new Integer(100);
    Integer d = new Integer(100);
    System.out.println(c==d);
}
~~~

</span>