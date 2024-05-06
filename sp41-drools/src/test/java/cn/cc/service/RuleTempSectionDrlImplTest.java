package cn.cc.service;

import cn.cc.dto.PCarInfo;
import cn.cc.service.service.RuleTempSectionDrlImpl;
import cn.cc.utils.DateUtils;
import org.junit.Test;

/**
 * @Description : 时段收费测试
 * @Author : GKL
 * @Date: 2024-05-06 14:58
 */
public class RuleTempSectionDrlImplTest {

    @Test
    public void testDealFee() {
        PCarInfo pCarInfo = new PCarInfo();
        pCarInfo.setInTime(DateUtils.hourOFDay(1));
        pCarInfo.setOutTime(DateUtils.hourOFDay(30));

        IRuleService iRuleService = new RuleTempSectionDrlImpl();
        iRuleService.dealFee(pCarInfo);
    }

}
