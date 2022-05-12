package thread;

/**
 * 测试线程上下文切换
 */
public class ThreadContext {

    public static void main(String[] args) {
        try {
            concurrency();
//            series();
//            System.out.println(11/10);
//            System.out.println(12/10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 我们可以通过对比串联执行和并发执行进行对比。
     * 10000     43 : 1
     * 100000    43 : 2
     * 1000000   59 : 5
     * 10000000  50 : 12
     * ...
     * 结果是次数少的话，串联的速度快，是因为上下文切换导致额外的开销
     */
    private static final long count = 10 * 100 * 100 * 100;

    private static void concurrency() throws InterruptedException {
        // 开始时间
        long start = System.currentTimeMillis();
        Thread thread = new Thread(()->{
            int a = 0;
            for(int i=0;i<count;i++){
                a += 1;
                if(a%1000000 == 0){
                    System.out.println("a: " + a);
                }
            }
        });
        // 启动线程
        thread.start();

        // 使用主线程执行b--
        int b = 0;
        for(long  i = 0; i< count; i++){
            b--;
            if(b%1000000 == 0){
                System.out.println("b: " + b);
            }
        }

        /**
         * 合并线程，统计时间
         * 保证这个线程执行完，在运行 main线程，最后统计时间
         */
        thread.join();
        long end = System.currentTimeMillis() - start;
        System.out.println("concurrency: " + end + "ms,b= " + b);
    }

    /**
     * 串联执行
     */
    private static void series() {
        long start = System.currentTimeMillis();
        int a = 0;
        for (long i = 0; i < count; i++) {
            a += 1;
            if(a%1000000 == 0){
                System.out.println("a: " + a);
            }
        }
        int b = 0;
        for (int i = 0; i < count; i++) {
            b--;
            if(b%1000000 == 0){
                System.out.println("b: " + b);
            }
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("Serial：" + time + "ms, b = " + b + ", a = " + a);
    }


}
