package com.cc.sp70craw.utils.craw.test;

import com.cc.sp70craw.utils.craw.pool.BusiPool;

public class TBusiPool {

    public static void main(String[] args) {
        BusiPool busiPool_1 = BusiPool.singleBusiPool("abc");

        busiPool_1.addTag("abcdabcd");
        System.out.println(busiPool_1.VIRGIN_List_Size());

        busiPool_1.addTag("abcdabcd");
        System.out.println(busiPool_1.VIRGIN_List_Size());

        BusiPool busiPool_2 = BusiPool.singleBusiPool("abc");

        busiPool_2.addTag("abcdabcde");
        System.out.println(busiPool_2.VIRGIN_List_Size());

        BusiPool busiPool_3 = BusiPool.singleBusiPool("abcd");
        busiPool_3.addTag("abcdabcde");

        System.out.println(busiPool_3.VIRGIN_List_Size());
        System.out.println(busiPool_2.VIRGIN_List_Size());
        System.out.println(busiPool_1.VIRGIN_List_Size());

        System.out.println("----------");

        System.out.println(busiPool_1.getRoot());
        System.out.println(busiPool_2.getRoot());
        System.out.println(busiPool_3.getRoot());

        busiPool_1.addTag("abcdabcd");
        busiPool_1.addTag("abcdabcd");
        busiPool_2.addTag("abcdabcd");
        busiPool_2.addTag("abcdabcd");
        busiPool_2.addTag("abcdabcd");
        busiPool_1.addTag("abcdabcd");
        busiPool_1.addTag("abcdabcd");



    }

}
