package cn.cc.utils.test.pool;

import cn.cc.utils.concurrent.ThreadPool;

public class IPoolTest {

    public static void main(String[] args) {

        for(int i=0;i<3;i++){
            try {
                //Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ThreadPool.getPool().execute(new IPoolThread(i));
        }
        //iPool.closeT(poolVO);

    }

    public static class IPoolThread implements Runnable{

        private int i;

        public IPoolThread(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                System.out.print("i:-ins " + i );
                IPool iPool = IPool.getInstance();
                PoolVO poolVO = iPool.getT();
                System.out.println(" 成功获取 >>> " + poolVO.getIndex());
                //iPool.closeT(poolVO);
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("i-err:  " + i + " >>> " + e.toString());

                IPool iPool = IPool.getInstance();
                iPool.closeAll();

                System.out.println("i-err: " + i + " >>> 没有可用对象了，清除后重新获取");
            }
        }

    }

}
