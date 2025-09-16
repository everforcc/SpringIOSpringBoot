package cn.cc.lkl.config;

import cn.cc.huifu.dto.ZnPayOrderRecord;
import cn.cc.huifu.refund.service.IRefundService;
import cn.cc.huifu.refund.service.IZnPayOrderRecordService;
import cn.cc.lkl.sdk.config.LKLPayConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@SpringBootTest
class LKLTests {

    @Resource
    LKLPayConfig lklPayConfig;

    @Test
    void contextLoads() {
        log.info("配置信息: \r\n{}",lklPayConfig.toString());
    }

}
