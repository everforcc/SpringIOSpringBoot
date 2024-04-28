package com.leopard.drools.park.service.service;

import com.leopard.drools.park.dto.PCarInfo;
import com.leopard.drools.park.service.IRuleService;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description : 免费会员
 * @Author : GKL
 * @Date: 2024-04-28 10:12
 */
@Slf4j
public class RuleVIPFreeDrlImpl implements IRuleService {

    @Override
    public void dealFee(PCarInfo pCarInfo) {
        log.info("车辆信息: {}", pCarInfo);
        log.info("当前计费逻辑为《免费》");
    }

}
