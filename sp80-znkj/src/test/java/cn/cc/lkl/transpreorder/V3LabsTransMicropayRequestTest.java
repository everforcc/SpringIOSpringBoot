package cn.cc.lkl.transpreorder;

import cn.cc.lkl.LKLBaseTest;
import cn.cc.lkl.util.StringUtils;
import com.lkl.laop.sdk.LKLSDK;
import com.lkl.laop.sdk.exception.SDKException;
import com.lkl.laop.sdk.request.V3LabsTransMicropayRequest;
import com.lkl.laop.sdk.request.model.V3LabsTradeLocationInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * https://o.lakala.com/#/home/document/detail?id=112
 * 聚合被扫
 */
@Slf4j
public class V3LabsTransMicropayRequestTest extends LKLBaseTest {

    @Test
    public void test() throws SDKException {
        V3LabsTransMicropayRequest request = new V3LabsTransMicropayRequest();
        request.setMerchantNo("822290059430BF9");
        request.setTermNo("D9261076");
        request.setOutTradeNo(StringUtils.getSerialNumber());
        request.setAuthCode("135178236713755038");
        request.setTotalAmount("2");
        V3LabsTradeLocationInfo locationInfo = new V3LabsTradeLocationInfo();
        locationInfo.setRequestIp("10.176.1.192");
        locationInfo.setLocation("+37.123456789,-121.123456789");
        request.setLocationInfo(locationInfo);

        log.info("request: \r\n{}", request.toBody());
        String response = LKLSDK.httpPost(request);
        log.info("response: \r\n{}", response);
    }

}
