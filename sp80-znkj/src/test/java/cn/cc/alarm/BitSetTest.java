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

    }

}
