package cn.cc.lkl.ordersplit;

import cn.cc.lkl.LKLBaseProdTest;
import cn.cc.lkl.sdk.config.LKLConfigProd;
import com.lkl.laop.sdk.LKLSDK;
import com.lkl.laop.sdk.exception.SDKException;
import com.lkl.laop.sdk.request.V2LaepIndustryEwalletBalanceQueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 余额相关
 */
@Slf4j
public class V2LaepIndustryEwalletBalanceQueryRequestTest extends LKLBaseProdTest {

    @Test
    public void test() {
        V2LaepIndustryEwalletBalanceQueryRequest request = new V2LaepIndustryEwalletBalanceQueryRequest();
        request.setMerchantNo("8224910737200MK");
        request.setOrgNo(LKLConfigProd.orgCode);

        log.info("request: \r\n{}", request.toBody());
        String response = null;
        try {
            response = LKLSDK.httpPost(request);
        } catch (SDKException e) {
            e.printStackTrace();
        }
        log.info("response: \r\n{}", response);

    }

}
