package thread;

import java.util.concurrent.*;

public class CountDownLatchTest {

    public static void main(String[] args) {
        try {
            int size = 10;
            ExecutorService exec = Executors.newFixedThreadPool(10);
            CountDownLatch count = new CountDownLatch(size);
            for (int i = size; i > 0; i--) {
                final int sleepI = i;
                exec.submit((Callable<Boolean>) () -> {
                    try {
                        System.out.println("start: ----------------: " + sleepI);
                        Thread.sleep(sleepI * 1000L);
                        System.out.println("end: ----------------: " + sleepI);
                        return null;
                    } finally {
                        System.out.println("count.getCount() countDown: " + count.getCount());
                        count.countDown();
                    }
                });
            }
//            count.await();
            boolean flag = count.await(11, TimeUnit.SECONDS);
            System.out.println("最多等待5秒后向下执行: " + flag);
            System.out.println("count.getCount() await: " + count.getCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
