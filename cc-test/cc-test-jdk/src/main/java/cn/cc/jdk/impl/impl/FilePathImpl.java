package cn.cc.jdk.impl.impl;

import cn.cc.jdk.impl.IDefault;
import cn.cc.jdk.impl.IFilePath;

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
