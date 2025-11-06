package cn.cc.huifu.schedule;

import cn.cc.huifu.refund.service.IRefundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class RefundSchedule {

    @Resource
    IRefundService iRefundService;

    @Scheduled(cron = "0 30 23 * * ?")
    public void refund() {
        log.info("执行定时操作");
//        iRefundService.refundList();
    }

}
