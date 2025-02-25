package cn.cc;

import cn.cc.huifu.dto.ZnPayOrderRecord;
import cn.cc.huifu.refund.service.IRefundService;
import cn.cc.huifu.refund.service.IZnPayOrderRecordService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    IZnPayOrderRecordService iZnPayOrderRecordService;

    @Test
    void switchDB136() {
//        List<ZnPayOrderRecord> list136 = iZnPayOrderRecordService.listZnPayOrderRecord136("20250219", null);
//        log.info("136: {}", list136.size());

        List<ZnPayOrderRecord> list138 = iZnPayOrderRecordService.listZnPayOrderRecord138("20250219", null);
        log.info("138: {}", list138.size());
    }

    @Test
    void switchDB() {
        List<ZnPayOrderRecord> list136 = iZnPayOrderRecordService.listZnPayOrderRecord136("20250219", null);
        log.info("总数: 136: {}", list136.size());

        List<ZnPayOrderRecord> list138 = iZnPayOrderRecordService.listZnPayOrderRecord138("20250219", null);
        log.info("总数: 138: {}", list138.size());
    }

}
