package cn.cc.service.service;

import cn.cc.dto.PCarInfo;
import cn.cc.service.IRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description : 免费会员
 * @Author : GKL
 * @Date: 2024-04-28 10:12
 */
@Slf4j
@Service
public class RuleVIPFreeDrlImpl implements IRuleService {

    @Override
    public void dealFee(PCarInfo pCarInfo) {
        log.info("车辆信息: {}", pCarInfo);
        log.info("当前计费逻辑为《免费》");
    }

}
