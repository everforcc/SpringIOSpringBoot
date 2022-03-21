package com.cc.sp90utils.utils;

/**
 * apache的包，用这个是为了方便随时替换其他的包
 */
public class RandomUtils {

    public static int randomInt(Integer start,Integer end){
        return org.apache.commons.lang3.RandomUtils.nextInt(start,end);
    }

    public static int randomInt(){
        return org.apache.commons.lang3.RandomUtils.nextInt();
    }

}
