package cn.cc.sync.binlog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 通过 binlog.mysql.* 配置自动绑定 mysql-binlog-connector 所需的连接与位点信息。
 */
@Data
@Component
@ConfigurationProperties(prefix = "binlog.mysql")
public class MySQLConnectorProperties {

    private String host = "127.0.0.1";
    private int port = 3306;
    private String username;
    private String password;
    private long serverId = 1337L;
    private String binlogFilename;
    private long binlogPosition = 4L;
    private String defaultDatabase;
}
