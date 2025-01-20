package cn.cc;

import cn.cc.huifu.refund.service.IRefundService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
class Sp80ZnkjApplicationTests {

    @Resource
    IRefundService iRefundService;

    @Test
    void contextLoads() {
        log.info("开始退款");
        iRefundService.refundList();
        log.info("退款结束");
    }

}
