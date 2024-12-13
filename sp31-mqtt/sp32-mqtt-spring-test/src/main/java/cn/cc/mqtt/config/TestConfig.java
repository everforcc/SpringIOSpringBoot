package cn.cc.mqtt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
    @Value("${test.dir}")
    private String testDir;
}
