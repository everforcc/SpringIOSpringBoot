package cn.cc.sync.binlog.mq;

import cn.cc.sync.binlog.service.MessageStorageService;
import cn.cc.sync.binlog.cloud.BinlogMessageBuffer;
import cn.cc.sync.binlog.dto.BinlogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * BinlogMessageSender 是 BinlogMessage 的发送者，将 BinlogMessage 推到内存 Map 与持久层
 * 发送到mq和持久化数据。
 */
@Slf4j
@Service
public class BinlogMessageSender {

    private final BinlogMessageBuffer buffer;
    private final MessageStorageService messageStorageService;

    public BinlogMessageSender(BinlogMessageBuffer buffer,
                               MessageStorageService messageStorageService) {
        this.buffer = buffer;
        this.messageStorageService = messageStorageService;
    }

    public void send(BinlogMessage message) {
        String key = message.getDatabase() + "." + message.getTable();
        buffer.putMessage(key, message);
        log.info("Binlog message sent: \r\n{}", message);
        messageStorageService.store(message);
    }
}