package com.cc.sp91test.test.jdk.impl.impl;

import com.cc.sp91test.test.jdk.impl.IDefault;
import com.cc.sp91test.test.jdk.impl.IFilePath;

public class FilePathImpl implements IFilePath {

    private static IFilePath iFilePath = new FilePathImpl();

    private static IDefault iDefault = new IDefaultImpl();

    String busiPath = "/bil";

//    public String path(){
//        return busiPath;
//    }

    public static void main(String[] args) {
        System.out.println(iFilePath.path());
        iDefault.tSelf();
    }

}
