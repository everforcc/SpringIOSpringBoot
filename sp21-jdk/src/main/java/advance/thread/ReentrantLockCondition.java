package advance.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockCondition {

        private ReentrantLock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();

        public void waitMethod() {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName()+"获取到了锁");
                System.out.println("wait 1");
                condition.await();
                System.out.println("wait 2");
                lock.unlock();
                System.out.println(Thread.currentThread().getName()+"释放了锁");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void singleMethod() {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName()+"获取到了锁");
                System.out.println("single 1");
                condition.signal();
                System.out.println("single 2");
                lock.unlock();
                System.out.println(Thread.currentThread().getName()+"释放了锁");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    class ThreadA extends Thread{
        private ReentrantLockCondition conditionTest;
        public ThreadA(ReentrantLockCondition conditionTest) {
            this.conditionTest = conditionTest;
        }
        @Override
        public void run() {
            conditionTest.waitMethod();
        }
    }

    class ThreadB extends Thread{
        private ReentrantLockCondition conditionTest;
        public ThreadB(ReentrantLockCondition conditionTest) {
            this.conditionTest = conditionTest;
        }
        @Override
        public void run() {
            conditionTest.singleMethod();
        }
    }

    class ConditionTest{
        public static void main(String[] args) {
            ReentrantLockCondition conditionTest = new ReentrantLockCondition();
            ThreadA a = new ThreadA(conditionTest);
            a.start();

            ThreadB b = new ThreadB(conditionTest);
            b.start();
        }
    }
