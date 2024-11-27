package cn.cc.jdk.impl;

public interface IFilePath {

    String busiPath = "/test";

    default String path(){
        return busiPath;
    }

}