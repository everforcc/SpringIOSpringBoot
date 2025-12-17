package cn.cc.sync.binlog.config;

import com.mysql.cj.jdbc.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

/**
 * 为 Binlog 元数据查询单独创建数据源，确保使用监听实例而非业务库。
 */
@Configuration
public class BinlogMetadataConfig {

    @Bean("binlogMetadataJdbcTemplate")
    public JdbcTemplate binlogMetadataJdbcTemplate(MySQLConnectorProperties properties) {
        String schema = properties.getDefaultDatabase();
        if (schema == null || schema.trim().isEmpty()) {
            schema = "information_schema";
        }
        String url = String.format(
                "jdbc:mysql://%s:%d/%s?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT",
                properties.getHost(),
                properties.getPort(),
                schema);
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(Driver.class);
        dataSource.setUrl(url);
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        return new JdbcTemplate(dataSource);
    }
}

