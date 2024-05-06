package cn.cc.service.service;

import cn.cc.dto.PCarInfo;
import cn.cc.service.IRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description : 年费会员
 * @Author : GKL
 * @Date: 2024-05-06 10:33
 */
@Slf4j
@Service
public class RuleVIPYearDrlImpl implements IRuleService {

    @Override
    public void dealFee(PCarInfo pCarInfo) {
        log.info("年费会员: {}", pCarInfo);
    }

}
