package cn.cc.lkl.auth;

import cn.cc.lkl.sdk.config.LKLConfig;
import cn.cc.lkl.util.DateUtils;
import cn.cc.lkl.util.StringUtils;
import com.lkl.laop.sdk.LKLSDK;
import com.lkl.laop.sdk.exception.SDKException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

@Slf4j
public class V2MmsOpenApiRealNameSaveAlipayContactInfoRequestTest {

    @Before
    public void pre() {
        try {
            LKLConfig.initSDK();
        } catch (SDKException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAliReq(){
        String json = "{\n" +
                "  \"reqData\": {\n" +
                "    \"merInnerNo\": \"4002021012659676355\",\n" +
                "    \"orderNo\": \"20250909175612587854\",\n" +
                "    \"version\": \"1.0\",\n" +
                "    \"orgCode\": "+ LKLConfig.org_code +",\n" +
                "    \"contactType\":\"SUPER\",\n" +
                "    \"name\":\"郭凯龙\",\n" +
                "    \"contactIdDocType\":\"RESIDENT\",\n" +
                "    \"idCardNumber\":\"41018219960126531X\",\n" +
                "    \"mobile\":\"15738573601\",\n" +
                "    \"realNameType\": \"ZFBZF\"\n" +
                "  },\n" +
                "  \"ver\":\"1.0.0\",\n" +
                "  \"timestamp\":\""+ DateUtils.getTimeStamp() +"\",\n" +
                "  \"reqId\":\""+ StringUtils.getSerialNumber() +"\"\n" +
                "}\n" +
                "\n";

        try {
            String response = LKLSDK.httpPost("https://test.wsmsd.cn/sit/api/v2/mms/openApi/realName/saveAlipayContactInfo",  json);
            log.info("response: \r\n{}", response);
        } catch (SDKException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAliStatus(){

        String json = "{\n" +
                "\"ver\":\"1.0.0\",\n" +
                "\"timestamp\":\""+ DateUtils.getTimeStamp() +"\",\n" +
                "\"reqId\":\""+ StringUtils.getSerialNumber() +"\",\n" +
                "\"reqData\":{\n" +
                "\"merchantNo\":\"8224910581208RL\",\n" +
                "\"tradeMode\":\"ALIPAY\",\n" +
                "\"subMerchantId\":\"808107443\"\n" +
                "}\n" +
                "}\n";
        log.info("json: \r\n{}", json);
        try {
            String response = LKLSDK.httpPost("https://test.wsmsd.cn/sit/api/v2/mms/sme/mrchAuthStateQuery", json);
            log.info("response: \r\n{}", response);
        } catch (SDKException e) {
            e.printStackTrace();
        }
    }

}
