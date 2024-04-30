package cn.cc.cache;

import lombok.Data;

import java.time.Duration;

/**
 * @Description : 系统缓存变量
 * @Author : GKL
 * @Date: 2024-04-30 13:27
 */
@Data
public class TestCache {

    private String name;

    private Duration expire;

}
