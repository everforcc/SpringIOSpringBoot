package cn.cc.codec.indexes;

import org.junit.Test;

/**
 * 文件三级索引测试
 */
public class Indexes {

    @Test
    public void t1() {
        // 文件后面可以绑定索引表
        int a = 1;
        // 直接
        int b0 = 1 * 5;
        // 对应 256个索引编号
        int b1 = 256 * 2;
        int b2 = 256 * 256;
        int b_all = b0 + b2 + b1;
        System.out.println(b_all);
    }

    @Test
    public void t2() {
        // 文件后面可以绑定索引表
        int a = 1;
        // 直接
        int b0 = 1 * 5;
        // 对应 256个索引编号
        int b1 = 256 * 3;
        int b_all = b0 + b1;
        System.out.println(b_all);
    }
}
