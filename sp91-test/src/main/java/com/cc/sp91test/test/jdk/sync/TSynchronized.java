package com.cc.sp91test.test.jdk.sync;

public class TSynchronized {

    public static void main(String[] args) {

//        final TLock tLock = new TLock();
//        tLock.add("修改锁对线 属性 的值");
//        tSyn(tLock);
//        tLock.add("修改锁对线 属性 的值");
//        tSyn(tLock);
//        TLock_2 tLock_2 = new TLock_2();
//        tSyn(tLock_2);
//        tLock_2.add("1",1);
//        tSyn(tLock_2);


        strLock("b");
        strLock("b");
        strLock("b");
        strLock("b");
        strLock("a");
        strLock("b");
        strLock("b");
    }

    /**
     * 测试字符串锁
     * @param str
     */
    public static void strLock(String str){
        synchronized (str){
            if("a".equals(str)){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("休眠");
            }
            System.out.println(str);
        }
    }

    public static class TLock{

        public String str;

        public TLock() {
        }

        void add(String str){
            this.str = str;
        }

        @Override
        public String toString() {
            return "TLock{" +
                    "str='" + str + '\'' +
                    '}';
        }
    }

    public static class TLock_2{

        public String[] strings = new String[128];

        public TLock_2() {
        }

        void add(String str,int i){
            this.strings[i] = str;
        }

        @Override
        public String toString() {
            return "TLock{" +
                    "str='" + strings.length + '\'' +
                    '}';
        }
    }

    public static void tSyn(TLock_2 tLock){
        System.out.println();
        synchronized (tLock){
            long preTime = 0;
            int size = 0;
            while (true){
                long currentTimeMillis = System.currentTimeMillis();
                // 一秒一次
                if(currentTimeMillis%1000 == 0 && preTime!=currentTimeMillis) {
                    System.out.println(currentTimeMillis);
                    System.out.println(tLock.strings[1]);
                    preTime = currentTimeMillis;
                    size++;
                }
                if(size == 5) {
                    break;
                }
            }
        }
    }


}
