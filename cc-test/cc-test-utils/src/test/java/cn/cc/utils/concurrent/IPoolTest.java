package cn.cc.utils.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class IPoolTest {

    /**
     * 测试 execute
     * 没保证全部执行完
     */
    @Test
    public void tExecute() {
        for (int i = 0; i < 3; i++) {
            try {
                //Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ThreadPool.getPool().execute(new IPoolThread(i));
        }
    }

    /**
     * 测试 submit
     * 没保证全部执行完
     */
    @Test
    public void tSubmit() {

        for (int i = 0; i < 3; i++) {
            try {
                //Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ThreadPool.getPool().submit(new IPoolThread(i));
        }
        //iPool.closeT(poolVO);
    }

    public static class IPoolThread implements Runnable {

        private int i;

        public IPoolThread(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                System.out.print("i:-ins " + i);
                IPool iPool = IPool.getInstance();
                PoolVO poolVO = iPool.getT();
                System.out.println(" 成功获取 >>> " + poolVO.getIndex());
                //iPool.closeT(poolVO);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("i-err:  " + i + " >>> " + e.toString());

                IPool iPool = IPool.getInstance();
                iPool.closeAll();

                System.out.println("i-err: " + i + " >>> 没有可用对象了，清除后重新获取");
            }
        }

    }

}
