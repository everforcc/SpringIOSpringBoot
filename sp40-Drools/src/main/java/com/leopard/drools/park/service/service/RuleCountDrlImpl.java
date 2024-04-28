package com.leopard.drools.park.service.service;

import com.leopard.drools.park.service.IRuleService;
import com.leopard.drools.park.dto.PCarInfo;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * @Description : 按次收费逻辑
 * @Author : GKL
 * @Date: 2024-04-28 09:38
 */
@Slf4j
public class RuleCountDrlImpl implements IRuleService {

    public static final String park_name = "A";
    /**
     * 车场A，停一次车价格
     */
    public static final BigDecimal park_A_once_fee = new BigDecimal(1.1);

    // 根据停车信息处理收费金额

    /**
     * 按次收费，不需要其他信息
     *
     * @param pCarInfo 停车车辆信息
     */
    @Override
    public void dealFee(PCarInfo pCarInfo) {
        log.info("按次收费不需要处理车辆入场出场时间，当前车辆: {}", pCarInfo);
        log.info("当前收费逻辑《按次收费》，车场:{} ,收费: {}", park_name, park_A_once_fee);
    }

}
