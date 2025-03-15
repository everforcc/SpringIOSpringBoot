package cn.cc.nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.channels.Selector;

public class SelectorTest {

    @Test
    public void selectorClass(){
        Selector selector = null;
        try {
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("当前IO多路复用实现类");
        System.out.println(selector.getClass());
    }

}
