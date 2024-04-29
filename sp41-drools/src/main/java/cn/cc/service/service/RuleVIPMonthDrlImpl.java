package cn.cc.service.service;

import cn.cc.dto.PCarInfo;
import cn.cc.service.IRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description : 会员按月收费
 * @Author : GKL
 * @Date: 2024-04-28 10:01
 */
@Slf4j
@Service
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
