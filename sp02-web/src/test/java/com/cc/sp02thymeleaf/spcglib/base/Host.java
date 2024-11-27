package com.cc.sp02thymeleaf.spcglib.base;

public class Host implements Rent{
    @Override
    public void rent() {
        System.out.println("出租房子");
    }
}
