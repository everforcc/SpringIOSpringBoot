package cn.cc.lkl.ordertranspre;

import cn.cc.lkl.LKLBaseProdTest;
import cn.cc.lkl.sdk.config.LKLConfigProd;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lkl.laop.sdk.LKLSDK;
import com.lkl.laop.sdk.request.V3LabsQueryTradequeryRequest;
import com.lkl.laop.sdk.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * https://o.lakala.com/#/home/document/detail?id=116
 * 聚合扫码-交易查询
 */
@Slf4j
public class V3LabsQueryTradequeryRequestTest extends LKLBaseProdTest {

    /**
     * 交易查询 V3LabsQueryTradequeryRequest
     */
    @Test
    public void test() throws Exception {
        V3LabsQueryTradequeryRequest request = new V3LabsQueryTradequeryRequest();
        request.setMerchantNo(LKLConfigProd.merchantNo);
        request.setTermNo(LKLConfigProd.termNo);
        request.setOutTradeNo("1651137300413444006");

        log.info("request: \r\n{}", request.toBody());
        String response = LKLSDK.httpPost(request);
        log.info("response: \r\n{}", JSONObject.toJSONString(response, SerializerFeature.PrettyFormat));
        // JsonUtil.toJson
        log.info("response: \r\n{}", JsonUtils.toJSONString(response));
    }

}
