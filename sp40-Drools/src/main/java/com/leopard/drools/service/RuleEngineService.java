package com.leopard.drools.service;

import com.leopard.drools.pojo.QueryParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description : 添加规则实现
 * @Author : GKL
 * @Date: 2024-04-26 13:34
 */
@Slf4j
@Service
public class RuleEngineService {

    /**
     * 插入规则
     *
     * @param param
     */
    public void executeAddRule(QueryParam param) {
        log.info("参数数据:" + param.getParamId() + ";" + param.getParamSign());
        log.info("插入规则");
    }

    /**
     * 移除规则
     *
     * @param param
     */
    public void executeRemoveRule(QueryParam param) {
        log.info("参数数据:" + param.getParamId() + ";" + param.getParamSign());
        log.info("移除规则");
    }
}