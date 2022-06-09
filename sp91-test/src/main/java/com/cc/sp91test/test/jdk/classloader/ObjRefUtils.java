package com.cc.sp91test.test.jdk.classloader;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 对象反射获取信息
 *
 * @author everforcc 2021-09-16
 */
public class ObjRefUtils {

    public static void main(String[] args) {
        try {
            //
            //Class<?> clazz = getInstance();
            // 构造
            //getConstructor(clazz);
            // 属性
            //Obj obj = new Obj();
            //obj.setPublicStr("123");
            //getField(obj.getClass(),obj);
            // 方法
            // getMehtod(clazz);
            //
            //
            //getInnerClass();
            //getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Class<?> getInstance() throws ClassNotFoundException {
        //  1. 类路径,class的静态方法 cc.java0.reflection.api.Obj
        Class<?> clazz = Class.forName("ConstantCharSet");
        //  2. 直接获取对象的class
        //  Class<?> clazzint = int.class;
        //  Class<?>  clazzinteger = Integer.TYPE;
        //  3. 调用某个对象的 getClass()
        //  String str = new String();
        //  Class<?> clazzstr = str.getClass();
        //  4. 包不一定存在
        /*String packageName = clazz.getPackage().getName();
        if(StringUtils.isNotBlank(packageName)) {
            System.out.println(packageName);
        }
        System.out.println(packageName.substring(packageName.lastIndexOf(".")+1));*/
        return clazz;
    }

    public static void getConstructor(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //获取String类带一个String参数的构造器
        Constructor constructor = clazz.getConstructor(String.class);
        //根据构造器创建实例
        Object obj = constructor.newInstance("23333");
        System.out.println(obj);
    }

    /**
     * 通过反射为字段赋值
     *
     * @param clazz
     * @param object
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static void getField(Class<?> clazz, Object object) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        // 1. 获取指定的字段
//        Field fieldPublicStr = clazz.getField("fieldName");
//        fieldPublicStr.set(object,"fieldValue");

        // 2. 获取私有字段
//        Field fieldPrivateStr = clazz.getDeclaredField("privateStr");
//        fieldPrivateStr.setAccessible(true);
//        fieldPrivateStr.set(object,"privateStr");

        System.out.println("打印对象字段信息 --- 开始");

        // 3. 所有字段集合
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName() + " >>> " + field.getType());

            // 给字段一个默认值
            field.setAccessible(true);
            // 如果是string类型的
            if (field.getType().isAssignableFrom(String.class)) {
                // 字段名叫什么就赋什么值
                field.set(object, field.getName());
            } else if (field.getType().isAssignableFrom(int.class)) {
                // 如果是int那就设置1
                field.set(object, 1);
            }
        }

        // 4. 获取字段的值
//        Field field = clazz.getDeclaredField("privateStr");
//        field.setAccessible(true);
//        System.out.println(field.get(object));
        System.out.println("打印对象字段信息 --- 结束");
    }

    public static void getMehtod(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        System.out.println("打印对象方法信息 --- 开始");
        // 3种
        // 1. 方法返回类或接口声明的所有方法，包括公共、保护、默认（包）访问和私有方法，但不包括继承的方法。
        Method[] methods = clazz.getDeclaredMethods();
        //2. 方法返回某个类的所有公用（public）方法，包括其继承类的公用方法
        //Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            System.out.print("方法名 : [" + method.getName());
            System.out.print("]    返回值 : [" + method.getReturnType());

            int parameterCount = method.getParameterCount();
            Class<?>[] clazzs = method.getParameterTypes();
            System.out.print("]  参数个数 [" + parameterCount + "] 参数列表 :{ ");
            for (Class cla : clazzs) {
                System.out.print(cla.getName());
            }
            System.out.println(" }");
        }

        // 3. 返回一个特定的方法
        // 3.1 公有
//        Method method = clazz.getMethod("methodVoidParams", String.class);
//        System.out.println("方法名 : " + method.getName());
//        method.invoke(clazz.newInstance(), "123");
        // 3.2 私有方法
//        Method declaredMethod = clazz.getDeclaredMethod("methodPrivateVoidParams", String.class);
//        declaredMethod.setAccessible(true);
//        System.out.println("方法名 : " + declaredMethod.getName());
//        declaredMethod.invoke(clazz.newInstance(), "123");
        System.out.println("打印对象方法信息 --- 结束");
    }

    // 获取私有内部类
    public static void getInnerClass() throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        Obj obj = new Obj();
        Class<?> clazz = obj.getClass();

        Class<?>[] innerClazz = clazz.getDeclaredClasses();
        System.out.println("innerClazz.length: " + innerClazz.length);
        for (Class c : innerClazz) {
            System.out.println(c.getName());
            Field fieldPrivateInnerStr = c.getDeclaredField("privateInnerStr");
            fieldPrivateInnerStr.setAccessible(true);
            Object innerObj = c.newInstance();
            System.out.println("privateInnerStr" + fieldPrivateInnerStr.get(innerObj));
            //System.out.println("publicInnerStr" + c.getDeclaredField("publicInnerStr"));
        }
    }

}
