package com.cc.sp91test.test.jdk.impl;

public class FilePathImpl implements IFilePath{

    private static IFilePath iFilePath = new FilePathImpl();

    String busiPath = "/bil";

//    public String path(){
//        return busiPath;
//    }

    public static void main(String[] args) {
        System.out.println(iFilePath.path());
    }

}
