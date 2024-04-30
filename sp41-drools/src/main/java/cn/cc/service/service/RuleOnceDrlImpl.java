package cn.cc.service.service;

import cn.cc.constant.RuleCacheConstants;
import cn.cc.dto.PCarInfo;
import cn.cc.service.IRuleService;
import cn.cc.utils.BigDecimalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @Description : 按次收费逻辑
 * @Author : GKL
 * @Date: 2024-04-28 09:38
 */
@Slf4j
@Service
public class RuleOnceDrlImpl implements IRuleService {

    @Resource
    RedisTemplate<Object, String> redisTemplate;

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
        log.info("当前收费逻辑《按次收费》，车场:{} ,收费: {}", park_name, BigDecimalUtils.trans(park_A_once_fee));
        String str = redisTemplate.opsForValue().get(RuleCacheConstants.RULE_COUNT_DRL);
//        String redisOnce = BigDecimalUtils.trans(obj);
        log.info("从redis取出数据: {}", new BigDecimal(str));
    }

}
