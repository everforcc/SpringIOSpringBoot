package com.leopard.drools.park.service.service;

import com.leopard.drools.park.service.IRuleService;
import com.leopard.drools.park.dto.PCarInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description : 会员按月收费
 * @Author : GKL
 * @Date: 2024-04-28 10:01
 */
@Slf4j
public class RuleVIPMonthDrlImpl implements IRuleService {

    public static final String park_name_a = "A";

    /**
     * 月费会员
     *
     * @param pCarInfo 停车车辆信息
     */
    @Override
    public void dealFee(PCarInfo pCarInfo) {
        log.info("会员按月收费，当前车辆: {}", pCarInfo);
        log.info("当前收费逻辑《会员按月收费》，车场:{} ", park_name_a);
    }
}
