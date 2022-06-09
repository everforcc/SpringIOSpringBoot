package com.cc.sp91test.test.jdk.classloader;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 加载本地类待完善
 *
 * @author everforcc 2021-11-12
 */
public class PathClassLoader extends ClassLoader {
    // C:\environment\test\MyUtils\target\classes
    public static final String drive = "F:/Cache/ChromeDown/";
    public static final String fileName = "ObjectRef";
    public static final String fileTypeClass = ".class";
    public static final String fileTypeJava = ".java";

    public static void main(String[] args) throws Exception {
        //loadClass();
        dynamicLoadClass();
    }

    public static void dynamicLoadClass() throws Exception {
        Class<?> clazz;
        //while (true) {
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardFileManager = javaCompiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> iterable = standardFileManager.getJavaFileObjects(drive + fileName + fileTypeJava);

        // 执行编译任务
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null, standardFileManager, null, null, null, iterable);
        task.call();
        standardFileManager.close();

        //自定义类加载器的加载路径
        PathClassLoader myClassLoader = new PathClassLoader();

        //编译后生成的字节码
        clazz = myClassLoader.findClass(fileName);

        if (clazz != null) {
            Object obj = clazz.newInstance();
            Method method = clazz.getDeclaredMethod("say", String.class, int.class);
            method.invoke(obj, "world", 18);
        }

        Thread.sleep(10000);
        //}
    }

    public void loadClass() throws Exception {
        PathClassLoader loader = new PathClassLoader();
        Class<?> clazz = loader.loadClass("ConstantCharSet", true);
        Object obj = clazz.newInstance();
        System.out.println(clazz.getName());
        System.out.println(clazz.getClassLoader());
        System.out.println(obj.getClass().toString());
        ObjRefUtils.getField(clazz, obj);
    }

    public Class<?> findClass(String name) {
        byte[] data = loadClassData(name);
        // 将一个 byte 数组转换为 Class// 类的实例
        return defineClass(name, data, 0, data.length);
    }

    public byte[] loadClassData(String name) {
        FileInputStream fis = null;
        byte[] data = null;
        try {
            fis = new FileInputStream(new File(drive + name + fileTypeClass));
            //fis = new FileInputStream(name);
            // 只要是输入流就好，那么网络的输入流应该也可以，放gitee一个试试

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int ch = 0;
            while ((ch = fis.read()) != -1) {
                baos.write(ch);
            }
            data = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}