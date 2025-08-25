package cn.cc.alarm;

import org.junit.Test;

import java.util.BitSet;

public class BitSetTest {

    @Test
    public void test() {
        BitSet bitSet = new BitSet();
        System.out.println(bitSet == null);
        System.out.println(bitSet.isEmpty());
        bitSet.set(0);
        System.out.println(bitSet.isEmpty());

        Long a = null;
        System.out.println("a: " + a);
        String str = String.valueOf(a);
        System.out.println("str: " + str);
        if (str != null) {
            // Redis命中，直接返回
            System.out.println("不等于null");
        }else {
            System.out.println("等于null");
        }

        if("null".equals(str)){
            System.out.println("等于nullstr");
        }else {
            System.out.println("不等于nullstr");
        }

    }

}
