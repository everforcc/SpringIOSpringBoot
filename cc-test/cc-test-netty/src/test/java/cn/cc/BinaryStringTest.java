package cn.cc;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class BinaryStringTest {

    /**
     * DefaultEventExecutorChooserFactory
     * 测试 i&-i == i
     * 2的n次方 为true
     * 其他情况为 false
     */
    @Test
    public void bitOperation() {
        int i = 4;
        String binaryString = Integer.toBinaryString(i);
        String binaryStringMinus = Integer.toBinaryString(-i);
        System.out.println(binaryString);
        System.out.println(binaryStringMinus);

        int a =                              0b100;
        int b = 0b11111111111111111111111111111100;
        // 位运算， 都是1结果为1，否则为0
        int c = a & b;
        int d = i & -i;
        String c_binaryString = Integer.toBinaryString(c);
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(c_binaryString);
        System.out.println(d);
        System.out.println(d == i);

        AtomicInteger idx = new AtomicInteger();
        int idx_i = idx.getAndIncrement();
        System.out.println(idx_i);
        idx_i = idx.getAndIncrement();
        System.out.println(idx_i);
        System.out.println("---");
        System.out.println(idx_i & i);
        System.out.println(2 & i - 1);
        System.out.println(3 & i - 1);
        System.out.println(4 & i - 1);
        System.out.println(5 & i - 1);
        System.out.println(6 & i - 1);
        System.out.println(7 & i - 1);
        for (int j = 0; j < 8; j++) {
            System.out.println("---------------");
            System.out.println("j  : " + j);
            long j_1 = j & i;
            // 先算术运算符，后位运算符
            long j_2 = j & i - 1;
            System.out.println("j_1: " + j_1);
            String j_s =Long.toBinaryString(j_1);
            System.out.println("j_s: " + j_s);
            System.out.println("j_2: " + j_2);
        }

    }

}
