package com.cc.sp91test.test.jdk.impl;

public interface IFilePath {

    String busiPath = "/test";

    default String path(){
        return busiPath;
    }

}