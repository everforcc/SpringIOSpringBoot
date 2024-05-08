package cn.cc.service.service;

import cn.cc.constant.RuleCacheConstants;
import cn.cc.dto.PCarInfo;
import cn.cc.dto.rule.RuleTempDuration;
import cn.cc.service.IRuleService;
import cn.cc.utils.BigDecimalUtils;
import cn.cc.utils.DateUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @Description : 时长收费逻辑
 * @Author : GKL
 * @Date: 2024-04-28 09:39
 */
@Slf4j
@Service
public class RuleTempDurationDrlImpl implements IRuleService {

    @Resource
    RedisTemplate<Object, Object> redisTemplate;

    /**
     * 免费时长
     */
//    private static final int freeMinute = 30;
//
//    /**
//     * 每多长时间计费一次
//     */
//    private static final int eachMinute = 30;


//    public static BigDecimal park_A_once_fee = new BigDecimal("2.5");

    /**
     * 修改到缓存里
     */
/*    public static RuleTempDuration ruleTempDuration = new RuleTempDuration();

    static {
        ruleTempDuration.setFee(park_A_once_fee);
        ruleTempDuration.setMinute(eachMinute);
    }*/

    /**
     * 根据车辆入场出场时间收费
     *
     * @param pCarInfo 停车车辆信息
     */
    @Override
    public void dealFee(PCarInfo pCarInfo) {
        log.info("当前车场按照时长收费，当前车辆: {}", pCarInfo);
        log.info("入场时间: {}", pCarInfo.getInTime());
        log.info("出场时间: {}", pCarInfo.getOutTime());

        // 取出收费规则
        Object object = redisTemplate.opsForValue().get(RuleCacheConstants.RTempDuration.KEY);
        if (object == null) {
            throw new RuntimeException("时长收费规则不存在");
        }
        RuleTempDuration ruleTempDuration = JSONObject.parseObject(object.toString(), RuleTempDuration.class);
        // 处理计费逻辑
        int durationMinutes = DateUtils.difMinute(pCarInfo.getInTime(), pCarInfo.getOutTime());
        // 1. x小时以内免费
        if (ruleTempDuration.getFreeMinute() > durationMinutes) {
            log.info("不足半小时，免费");
            return;
        }
        // 2. 每 单位多少钱
        BigDecimal cost = ruleTempDuration.getRuleDurationFee().multiply(
                BigDecimal.valueOf(
                        Math.ceil((double)
                                (durationMinutes / ruleTempDuration.getDurationMinute())
                        ))
        );

        log.info("当前收费逻辑《按时收费》，耗时:{},{}m/{}元", durationMinutes, ruleTempDuration.getDurationMinute(), BigDecimalUtils.trans(ruleTempDuration.getRuleDurationFee()));
        log.info("收费: {}", cost);
    }

}
