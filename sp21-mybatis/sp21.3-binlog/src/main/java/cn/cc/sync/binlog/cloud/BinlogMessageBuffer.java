package cn.cc.sync.binlog.cloud;

import cn.cc.sync.binlog.dto.BinlogMessage;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 移动到sql模块儿
 * BinlogMessageBuffer 是一个内存缓存，用于暂存 BinlogMessage，
 * 模拟云服务
 */
@Component
public class BinlogMessageBuffer {

    private final Map<String, Deque<BinlogMessage>> buffer = new ConcurrentHashMap<>();
    private final int maxSizePerKey = 1000; // 单个 key 的最大缓存数量

    public void putMessage(String key, BinlogMessage message) {
        Deque<BinlogMessage> queue = buffer.computeIfAbsent(key, k -> new LinkedList<>());
        synchronized (queue) {
            queue.addLast(message);
            if (queue.size() > maxSizePerKey) {
                queue.removeFirst(); // 简单淘汰策略
            }
        }
    }

    /**
     * 近实时处理
     * @param key
     * @return
     */
    public List<BinlogMessage> drainMessages(String key) {
        Deque<BinlogMessage> queue = buffer.get(key);
        if (queue == null) {
            return Collections.emptyList();
        }
        synchronized (queue) {
            List<BinlogMessage> list = new ArrayList<>(queue);
            queue.clear();
            return list;
        }
    }
}
