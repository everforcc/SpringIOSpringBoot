package cn.cc.xiaoyu.client.constant;


import lombok.Data;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 定时任务配置类
 */
@Data
public class ScheduledTask {
    private final String taskName;
    private final long initialDelay;
    private final long period;
    private final TimeUnit timeUnit;
//    private final Supplier<byte[]> dataSupplier;

    // 构造函数和getter方法...
}