package cn.cc.service.service;

import cn.cc.constant.RuleCacheConstants;
import cn.cc.dto.PCarInfo;
import cn.cc.dto.rule.RuleBase;
import cn.cc.dto.rule.RuleTempOnce;
import cn.cc.service.IRuleService;
import com.alibaba.fastjson.JSONObject;
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
public class RuleTempOnceDrlImpl implements IRuleService {

    @Resource
    RedisTemplate<Object, Object> redisTemplate;

    // 根据停车信息处理收费金额

    /**
     * 按次收费，不需要其他信息
     *
     * @param pCarInfo 停车车辆信息
     */
    @Override
    public BigDecimal dealFee(PCarInfo pCarInfo) {
        log.info("按次收费不需要处理车辆入场出场时间，当前车辆: {}", pCarInfo);

        Object object = redisTemplate.opsForValue().get(RuleCacheConstants.RTempOnce.KEY);
        assert object != null;
        RuleTempOnce ruleTempOnce = JSONObject.parseObject(object.toString(), RuleTempOnce.class);
        log.info("当前收费逻辑《按次收费》，车场:{} ,收费: {}", pCarInfo.getParkId(), ruleTempOnce.getRuleOnceFee());
        return ruleTempOnce.getRuleOnceFee();
    }

}
