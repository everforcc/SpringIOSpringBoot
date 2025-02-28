package cn.cc.jdk.time;

public class NanoTimeTests {

    public static void main(String[] args) {
        try {
            for (int i = 0; i < 10; i++) {
                long nanoTime = System.nanoTime();
                System.out.println(nanoTime);
                System.out.println(nanoTime / 10);
//                System.out.println(System.currentTimeMillis());
//                System.out.println(Instant.now().toEpochMilli());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
