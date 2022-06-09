/**
 * Project:TODO ADD PROJECT NAME SpringIOSpringBoot
 *
 * @Description
 * @Author Author Date Description
 * ------ ------ ------
 *    TODO 开发人员邮箱前缀 调整时间 年-月-日 主要改动点>5字符
 * @Date 2022-06-09 23:38
 * Copyright
 */

package com.cc.sp91test.test.jdk.classloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

public class ClassLoaderTest {

    /**
     * 加载网络上的类
     */
    private static void webClassLoader() {
        // 1. 原始路径,但是加载需要把类放到loadClass参数
        // https://gitee.com/MyYukino/media/raw/master/class/ObjectRef.class
        String webClassUrl = "https://gitee.com/MyYukino/media/raw/master/class/";
        URL[] urls = new URL[2];
        try {
            // 2. 加载网络地址
            urls = new URL[]{new URL(webClassUrl)};
            URLClassLoader urlClassLoader = new URLClassLoader(urls);
            // 3. 加载地址下执行的类，可以带包名
            Class<?> ObjectRefClass = urlClassLoader.loadClass("ObjectRef");
            // 实例化
            Object ObjectRef = ObjectRefClass.newInstance();

            // 4. 打印类信息
            System.out.println("类信息");
            System.out.println("类名: " + ObjectRefClass.getName());
            System.out.println("类加载类型: " + ObjectRefClass.getClassLoader());
            System.out.println("类路径地址" + ObjectRef.getClass().toString());

            // 5. 类字段，并给string和int设置默认值
            ObjRefUtils.getField(ObjectRefClass, ObjectRef);

            // 6. 类放心信息
            ObjRefUtils.getMehtod(ObjectRefClass);

            // 7. 方法调用
            System.out.println("调用方法");
            // 参数，方法名，属性数组
            Method methodVoidParams = ObjectRefClass.getDeclaredMethod("methodVoidParams", String.class);
            // 参数，如果有返回值就强转出来
            methodVoidParams.invoke(ObjectRef, "自定义参数");


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    private static void jarClassLoader() {
        // 1. 原始路径,但是加载需要把类放到loadClass参数
        String RFileUtilsUrl = "file:" + "D:\\java\\code\\github\\OneForAll\\target\\classes\\";
        String commons_io_26_jar = "file:" + "E:\\Cache\\Maven-Jar\\commons-io\\commons-io\\2.6\\commons-io-2.6.jar";
        URL[] urls = new URL[2];
        try {
            // 2. 加载网络地址
            urls = new URL[]{new URL(RFileUtilsUrl),new URL(commons_io_26_jar)};
            URLClassLoader urlClassLoader = new URLClassLoader(urls);
            // 3. 加载地址下执行的类，可以带包名
            Class<?> ObjectRefClass = urlClassLoader.loadClass("cn.cc.dawn.utils.commons.io.RFileUtils");
            // 实例化
            Object ObjectRef = ObjectRefClass.newInstance();

            // 4. 打印类信息
            System.out.println("类信息");
            System.out.println("类名: " + ObjectRefClass.getName());
            System.out.println("类加载类型: " + ObjectRefClass.getClassLoader());
            System.out.println("类路径地址" + ObjectRef.getClass().toString());

            // 5. 类字段，并给string和int设置默认值
            ObjRefUtils.getField(ObjectRefClass, ObjectRef);

            // 6. 类放心信息
            ObjRefUtils.getMehtod(ObjectRefClass);

            // 7. 方法调用
            System.out.println("调用方法");
            // 参数，方法名，属性数组
            Method sayConstant = ObjectRefClass.getDeclaredMethod("readLines", String.class, String.class);
            // 参数，如果有返回值就强转出来
            List<String> stringList = (List<String>)sayConstant.invoke(ObjectRef, "F:\\磁盘.txt", "UTF-8");
            stringList.forEach(System.out::println);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        //webClassLoader();
        jarClassLoader();
    }

}
