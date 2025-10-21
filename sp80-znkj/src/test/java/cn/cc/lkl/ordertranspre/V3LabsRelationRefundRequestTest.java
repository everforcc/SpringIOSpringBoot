package cn.cc.lkl.ordertranspre;

import cn.cc.lkl.LKLBaseProdTest;
import cn.cc.lkl.sdk.config.LKLConfigProd;
import cn.cc.lkl.util.StringUtils;
import com.lkl.laop.sdk.LKLSDK;
import com.lkl.laop.sdk.exception.SDKException;
import com.lkl.laop.sdk.request.V3LabsRelationRefundRequest;
import com.lkl.laop.sdk.request.model.V3LabsTradeLocationInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * https://o.lakala.com/#/home/document/detail?id=113
 * 扫码-退款交易
 */
@Slf4j
public class V3LabsRelationRefundRequestTest extends LKLBaseProdTest {

    /**
     *
     * @throws SDKException
     */
    @Test
    public void test() throws SDKException {
        V3LabsRelationRefundRequest request = new V3LabsRelationRefundRequest();
        request.setMerchantNo(LKLConfigProd.merchantNo);
        request.setTermNo(LKLConfigProd.termNo);
        request.setOutTradeNo(StringUtils.getSerialNumber());
        request.setRefundAmount("2");
        request.setRefundReason("测试");
        // 原商户交易流水号
        request.setOriginOutTradeNo("1651137300413444006");
        // 原拉卡拉交易流水号
//        request.setOriginTradeNo("");
        // 原对账单流水号
//        request.setOriginLogNo("");
        V3LabsTradeLocationInfo locationInfo = new V3LabsTradeLocationInfo();
        locationInfo.setRequestIp("10.176.1.192");
        locationInfo.setLocation("+37.123456789,-121.123456789");
        request.setLocationInfo(locationInfo);

        log.info("request: \r\n{}", request.toBody());
        String response = LKLSDK.httpPost(request);
        log.info("response: \r\n{}", response);
    }

}

