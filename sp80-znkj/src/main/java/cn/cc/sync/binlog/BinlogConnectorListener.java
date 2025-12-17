package cn.cc.sync.binlog;

import cn.cc.sync.binlog.config.MySQLConnectorProperties;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * BinlogConnectorListener 负责在 Spring 启动后拉起 mysql-binlog-connector，并把收到的事件交给处理链。
 * 该类同时结合 BinlogPositionTracker，保证消息落库时有权威位点信息可用。
 */
@Slf4j
@Component
public class BinlogConnectorListener {

    private final BinaryLogClient client;
    private final BinlogEventProcessor eventProcessor;
    private final BinlogPositionTracker positionTracker;

    public BinlogConnectorListener(MySQLConnectorProperties properties,
                                   BinlogEventProcessor eventProcessor,
                                   BinlogPositionTracker positionTracker) {
        this.eventProcessor = eventProcessor;
        this.positionTracker = positionTracker;
        this.client = new BinaryLogClient(
                properties.getHost(),
                properties.getPort(),
                properties.getUsername(),
                properties.getPassword());
        client.setServerId(properties.getServerId());
        client.setBinlogFilename(properties.getBinlogFilename());
        client.setBinlogPosition(properties.getBinlogPosition());
        this.positionTracker.setInitialFile(properties.getBinlogFilename());
    }

    @PostConstruct
    public void start() {
        client.registerEventListener(event -> {
            positionTracker.track(event);
            eventProcessor.process(event);
        });
        CompletableFuture.runAsync(() -> {
            try {
                client.connect();
            } catch (IOException e) {
                log.error("Binlog connect failed", e);
            }
        });
    }
}