package com.leopard.drools.park.service;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description : 当前使用计费规则
 * @Author : GKL
 * @Date: 2024-04-28 09:34
 */
@Slf4j
public class RuleService {

    /**
     * 获取当前的计费规则
     */
    public void getRule() {
        log.info("从数据库或缓存获取当前计费规则...");
    }

    /**
     * 生成计费规则
     */
    public void generalRule(){
        log.info("生成计费规则存到数据库和缓存...");
    }

}
