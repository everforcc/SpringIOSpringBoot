package com.leopard.drools.park.service;

import com.leopard.drools.park.dto.PCarInfo;

/**
 * @Description : 收费规则接口
 * @Author : GKL
 * @Date: 2024-04-28 09:55
 */
public interface IRuleService {

    /**
     * 根据不同信息处理计费规则
     *
     * @param pCarInfo 停车车辆信息
     */
    public void dealFee(PCarInfo pCarInfo);

}
