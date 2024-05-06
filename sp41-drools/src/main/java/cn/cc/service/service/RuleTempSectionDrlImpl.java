package cn.cc.service.service;

import cn.cc.dto.PCarInfo;
import cn.cc.dto.rule.RuleTempSection;
import cn.cc.service.IRuleService;
import cn.cc.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description : 时段收费
 * @Author : GKL
 * @Date: 2024-05-06 13:54
 */
@Slf4j
@Service
public class RuleTempSectionDrlImpl implements IRuleService {

    // 区间和收费信息
    // [12-13) 2元
    private static List<RuleTempSection> ruleTempSectionList = new ArrayList<>();

    static {
        for (int i = 0; i <= 23; i++) {
            ruleTempSectionList.add(new RuleTempSection(new BigDecimal(i + 1)
                    , i, i + 1));
        }
    }


    @Override
    public void dealFee(PCarInfo pCarInfo) {
        log.info("时段收费.");
        BigDecimal cost = new BigDecimal(0);
        // 时间区间收费，先看当前时间段是否为多天，然后一个一个时间段处理
        Date inTime = pCarInfo.getInTime();
        LocalDateTime inTimeLocalDateTime = DateUtils.toLocalDateTime(inTime);
        Date outTime = pCarInfo.getOutTime();
        LocalDateTime outTimeLocalDateTime = DateUtils.toLocalDateTime(outTime);
        long until = DateUtils.untilLocalDate(inTime, outTime);
        log.info("入场: {}", inTime);
        log.info("出场: {}", outTime);
        log.info("一共: {}", until);
        for (int i = 0; i <= until; i++) {
            log.info("处理第{}天", i + 1);
            LocalDate day = inTimeLocalDateTime.toLocalDate().plusDays(i);

            // 处理隔天逻辑
            // 入场时间
            LocalDateTime tempStartLocalDate;
            // 出厂时间
            LocalDateTime tempEndLocalDate;
            if (day.equals(inTimeLocalDateTime.toLocalDate())) {
                tempStartLocalDate = inTimeLocalDateTime;
            } else {
                tempStartLocalDate = day.atTime(LocalTime.of(0, 0, 0));
            }

            if (day.equals(outTimeLocalDateTime.toLocalDate())) {
                tempEndLocalDate = outTimeLocalDateTime;
            } else {
                tempEndLocalDate = day.atTime(LocalTime.of(23, 59, 59));
            }


            // 比如停车时间 [0506-11:10, 0507-12:20)
            for (RuleTempSection e : ruleTempSectionList) {
                Date startDate = DateUtils.hourOFDay(i * 24 + e.getDurationStart());
                Date endDate = DateUtils.hourOFDay(i * 24 + e.getDurationEnd());
                boolean overLap = DateUtils.isOverLap(DateUtils.localDateTimeToDate(tempStartLocalDate),
                        DateUtils.localDateTimeToDate(tempEndLocalDate),
                        startDate, endDate
                );
                if (overLap) {
                    log.info("触发时间[{},{}),收费规则: {}", DateUtils.localDateTimeToDate(tempStartLocalDate), DateUtils.localDateTimeToDate(tempEndLocalDate), e.getFee());
                    cost = cost.add(e.getFee());
                }
            }
            log.info("第{}天消费消费: {}", i + 1, cost);
        }
        log.info("共消费: {}", cost);
    }

}
