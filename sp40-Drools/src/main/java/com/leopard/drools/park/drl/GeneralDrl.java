package com.leopard.drools.park.drl;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description : 生成的drl
 * @Author : GKL
 * @Date: 2024-04-28 10:09
 */
@Slf4j
public class GeneralDrl {

    /*
     * 根据用户选择生成drl
     * 1. 每次选择最后必须落到收费逻辑
     */

    /**
     * 1. 根据车辆类型选择会员类型
     */
    public void carTypeToVIP() {
        log.info("根据车辆类型选择会员类型");
    }

    /**
     * 2. 根据不同的会员类型，选择不同的计费逻辑
     */
    public void vipToRule() {
        log.info("根据不同的会员类型，选择不同的计费逻辑");
        log.info("特殊车辆：免费会员...");
        log.info("包月会员...");
        log.info("年费会员...");
        log.info("...");
        log.info("非会员，根据当前生效的计费逻辑...");
        log.info("非会员，同一个时间段只能有一个基础逻辑，和一个临时逻辑（例如节假日）");
        log.info("...");
        log.info("根据不同的条件，生成不同的实现类，然后调用代码");
    }

}
