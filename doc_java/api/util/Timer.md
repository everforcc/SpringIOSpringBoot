<font face="Simsun" size=3>

[TOC]

## 定时任务

- 可以实现轻量级的任务，比较方便
- 由于只是一个 thread，所以前一个任务异常的花会影响到下一个
- InterruptedException 异常后 中断
- 单线程 + 最小堆 + 不断轮询

### 1. 主要结构

- Timer
- TimerThread
- TaskQueue

### 2. 源码分析

### 3. Timer
~~~
public class Timer {

    /**
    *   一个队列一个线程
    */
    private final TaskQueue queue = new TaskQueue();
    /**
     * The timer thread.
     */
    private final TimerThread thread = new TimerThread(queue);
    
    public Timer() {
        this("Timer-" + serialNumber());
    }
    public Timer(String name) {
        thread.setName(name);
        thread.start();
    }
~~~
-
~~~
threadReaper 重写了 object的方法
GC的时候用
~~~


### 4. TimerTask
~~~
public abstract class TimerTask implements Runnable {
~~~
- Timer是一个线程, TimerTask是继承了Runable的接口

---

- 分析下重要的代码
~~~
/**
 *  在上面  Timer(String name) new 之后，线程就已经创建了
 *  new TaskQueue() run(); 这一步之后就一直启用了
 *  
 *  .schedule( 这一步 组装 TimerTask 的相关信息 并 加入队列
 */
 
 class TaskQueue {

     // 从1开始的，
     TimerTask getMin() {
            return queue[1];
     }

~~~
- run方法
~~~
public void run() {
        try {
            mainLoop();
        } finally {
            // Someone killed this Thread, behave as if Timer cancelled
            synchronized(queue) {
                newTasksMayBeScheduled = false;
                queue.clear();  // Eliminate obsolete references
            }
        }
    }

    /**
     * The main timer loop.  (See class comment.)
     */
    private void mainLoop() {
        while (true) {
            try {
                TimerTask task;
                boolean taskFired;
                synchronized(queue) {
                    // Wait for queue to become non-empty
                    while (queue.isEmpty() && newTasksMayBeScheduled)
                        queue.wait();
                    if (queue.isEmpty())
                        break; // Queue is empty and will forever remain; die

                    // Queue nonempty; look at first evt and do the right thing
                    long currentTime, executionTime;
                    // 获取1，0不用
                    task = queue.getMin();
                    synchronized(task.lock) {
                        if (task.state == TimerTask.CANCELLED) {
                            // 如果被取消了，就充队列中移除
                            queue.removeMin();
                            continue;  // No action required, poll queue again
                        }
                        currentTime = System.currentTimeMillis();
                        executionTime = task.nextExecutionTime;
                        
                        // 执行条件，当前时间大于executionTime 就执行，然后移除
                        if (taskFired = (executionTime<=currentTime)) {
                            if (task.period == 0) { // Non-repeating, remove
                                queue.removeMin();
                                task.state = TimerTask.EXECUTED;
                            } else { 
                                // Repeating task, reschedule
                                // 将下标为1的任务，设置时间并排序
                                queue.rescheduleMin(
                                  task.period<0 ? currentTime   - task.period
                                                : executionTime + task.period);
                            }
                        }
                    }
                    if (!taskFired) // Task hasn't yet fired; wait
                        queue.wait(executionTime - currentTime);
                }
                if (taskFired)  // Task fired; run it, holding no locks
                    task.run();
            } catch(InterruptedException e) {
            }
        }
    }
~~~
- cancel( 等取消操作

### 5. TaskQueue

~~~
void quickRemove(int i) {

void heapify() {
        for (int i = size/2; i >= 1; i--)
            fixDown(i);
    }
~~~

</font>