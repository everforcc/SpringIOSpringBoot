package com.cc.sp90utils.commons.lang;

public class RRandomUtils {

    public static int randomInt(Integer start,Integer end){
        return org.apache.commons.lang3.RandomUtils.nextInt(start,end);
    }

    public static int randomInt(){
        return org.apache.commons.lang3.RandomUtils.nextInt();
    }

}
