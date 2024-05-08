package cn.cc.service;

import cn.cc.dto.PCarInfo;
import cn.cc.service.service.RuleTempSectionDrlImpl;
import org.junit.Test;

import java.util.Date;

/**
 * @Description : 时段收费测试
 * @Author : GKL
 * @Date: 2024-05-06 14:58
 */
public class RuleTempSectionDrlImplTest {

    @Test
    public void testDealFee() {
        PCarInfo pCarInfo = new PCarInfo();

        Date inTime = new Date(2024 - 1900, 5, 6, 23, 1, 1);
        Date outTime = new Date(2024 - 1900, 5, 8, 12, 1, 1);

//        pCarInfo.setInTime(DateUtils.hourOFDay(1));
//        pCarInfo.setOutTime(DateUtils.hourOFDay(30));
        pCarInfo.setCarType(1);
        pCarInfo.setVipLevel(0);
        pCarInfo.setParkId(123);
        pCarInfo.setCarNum("豫A123456");
        pCarInfo.setInTime(inTime);
        pCarInfo.setOutTime(outTime);

        System.out.println(pCarInfo);

//        IRuleService iRuleService = new RuleTempSectionDrlImpl();
//        iRuleService.dealFee(pCarInfo);
    }

}
