package cn.cc.asyn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class SpringAsyncConfig {

    @Bean("AsyncTaskExecutor1")
    public AsyncTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(10);
        // 最大线程数
        executor.setMaxPoolSize(20);
        // 队列容量
        executor.setQueueCapacity(500);
        // 线程空闲时间
        executor.setKeepAliveSeconds(60);
        // 设置线程名的前缀
        executor.setThreadNamePrefix("cc-thread-");
        // 拒绝策略
        // 使用调用线程执行任务。当触发拒绝策略，只要线程池没有关闭的话，则使用调用线程直接运行任务。一般并发比较小，性能要求不高，不允许失败。但是，由于调用者自己运行任务，如果任务提交速度过快，可能导致程序阻塞，性能效率上必然的损失较大
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化线程池
        executor.initialize();
        return executor;
    }
//
//    @Bean("AsyncTaskExecutor2")
//    public AsyncTaskExecutor taskExecutor2() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        // 核心线程数
//        executor.setCorePoolSize(10);
//        // 最大线程数
//        executor.setMaxPoolSize(20);
//        // 队列容量
//        executor.setQueueCapacity(500);
//        // 线程空闲时间
//        executor.setKeepAliveSeconds(60);
//        // 设置线程名的前缀
//        executor.setThreadNamePrefix("dd-thread-");
//        // 拒绝策略
//        // 使用调用线程执行任务。当触发拒绝策略，只要线程池没有关闭的话，则使用调用线程直接运行任务。一般并发比较小，性能要求不高，不允许失败。但是，由于调用者自己运行任务，如果任务提交速度过快，可能导致程序阻塞，性能效率上必然的损失较大
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        // 初始化线程池
//        executor.initialize();
//        return executor;
//    }

}
