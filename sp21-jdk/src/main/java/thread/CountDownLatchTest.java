package thread;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchTest {

    public static void main(String[] args) {
        try {
            int size = 10;
            ExecutorService exec = Executors.newFixedThreadPool(10);
            CountDownLatch count = new CountDownLatch(size);
            for (int i = size; i > 0; i--)
                exec.submit(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        try {
                            System.out.println("----------------");
                            return null;
                        } finally {
                            System.out.println("count.getCount() countDown: " + count.getCount());
                            count.countDown();
                        }
                    }
                });
            count.await();
            System.out.println("count.getCount() await: " + count.getCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
