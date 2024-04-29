package cn.cc.service.service;

import cn.cc.service.IRuleService;
import cn.cc.dto.PCarInfo;
import cn.cc.utils.BigDecimalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Description : 时长收费逻辑
 * @Author : GKL
 * @Date: 2024-04-28 09:39
 */
@Slf4j
@Service
public class RuleDurationDrlImpl implements IRuleService {

    public static final String park_name_a = "A";

    /**
     * 车场B
     * 两小时2.5
     */
    public static final int hour = 2;

    /**
     * 车场B
     */
    public static final BigDecimal park_A_once_fee = new BigDecimal(2.5);

    /**
     * 根据车辆入场出场时间收费
     *
     * @param pCarInfo 停车车辆信息
     */
    @Override
    public void dealFee(PCarInfo pCarInfo) {
        log.info("当前车场按照时长收费，当前车辆: {}", pCarInfo);
        log.info("入场时间: {}", pCarInfo.getInTime());
        log.info("出厂时间: {}", pCarInfo.getOutTime());
        log.info("当前收费逻辑《按时收费》，车场:{} ,{}h/{}元", park_name_a, hour, BigDecimalUtils.trans(park_A_once_fee));
    }

}
