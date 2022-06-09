package com.cc.sp91test.test.jdk.classloader;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

/**
 * 自定义的web类加载器
 * 但是自带的URLClassLoader就够用了，学习用
 */
public class WebURLClassLoader extends URLClassLoader {

    public WebURLClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public WebURLClassLoader(URL[] urls) {
        super(urls);
    }

    public WebURLClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }

}
