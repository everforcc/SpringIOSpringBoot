package cn.cc.xiaoyu.client.handler;

import cn.cc.xiaoyu.client.constant.ScheduledTask;
import cn.cc.xiaoyu.client.constant.ScheduledTaskConstant;
import cn.cc.xiaoyu.client.instant.IXiaoYuClient;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 处理晓宇定时任务
 */
@Slf4j
public class XiaoYuTimeHandler extends ChannelInboundHandlerAdapter {

    private final List<ScheduledTask> scheduledTasks = new ArrayList<>();
    private final IXiaoYuClient xiaoYuClient;

    public XiaoYuTimeHandler(IXiaoYuClient xiaoYuClient) {
        this.xiaoYuClient = xiaoYuClient;
    }

    /**
     * 添加定时任务
     */
    public void addScheduledTask(String taskName, long initialDelay,
                                 long period, TimeUnit unit) { //, Supplier<byte[]> dataSupplier
        ScheduledTask task = new ScheduledTask(taskName, initialDelay, period, unit);
        scheduledTasks.add(task);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("晓宇充电桩 启动定时任务 当前注册任务数: {}", scheduledTasks.size());
        // 启动所有注册的定时任务
        for (ScheduledTask task : scheduledTasks) {
            log.info("晓宇充电桩 注册定时任务 任务名: {}", task.getTaskName());
            ctx.executor().scheduleAtFixedRate(() -> {
                if (ctx.channel().isActive()) {
//                    log.info("晓宇充电桩 执行定时任务: {}", task.getTaskName());
                    byte[] data = new byte[0];
                    if (ScheduledTaskConstant.PORT_STATUS.equals(task.getTaskName())) {
                        int id = ByteBuffer.wrap(xiaoYuClient.getId()).getInt();
                        if (XiaoYuClientHandler.orderMap.containsKey(id)) {
                            data = xiaoYuClient.getPortStatusData(XiaoYuClientHandler.orderMap.get(id), (byte) 1);
                            log.info("晓宇充电桩 执行定时任务:9.1 充电桩主动上报各端口状态: {}", xiaoYuClient.getId());
                        }
                    } else if (ScheduledTaskConstant.END_ELEC.equals(task.getTaskName())) {
                        int id = ByteBuffer.wrap(xiaoYuClient.getId()).getInt();
                        if (XiaoYuClientHandler.orderMap.containsKey(id)) {
                            data = xiaoYuClient.getEndElecData(XiaoYuClientHandler.orderMap.get(id), (byte) 1, (byte) 1);
                            XiaoYuClientHandler.orderMap.remove(id);
                            log.info("晓宇充电桩 执行定时任务:8.1 充电桩主动结束充电: {}", id);
                        }
                    }
                    if (data != null && data.length > 0) {
                        ByteBuf buffer = ctx.alloc().directBuffer(data.length);
                        buffer.writeBytes(data);
                        ctx.writeAndFlush(buffer);
                    }
                }
            }, task.getInitialDelay(), task.getPeriod(), task.getTimeUnit());
        }

        super.channelActive(ctx);
    }
}

