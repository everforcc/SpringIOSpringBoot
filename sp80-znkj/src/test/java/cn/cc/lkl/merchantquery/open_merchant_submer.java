package cn.cc.lkl.merchantquery;

import cn.cc.lkl.sdk.config.LKLConfig;
import com.lkl.laop.sdk.LKLSDK;
import com.lkl.laop.sdk.exception.SDKException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

@Slf4j
public class open_merchant_submer {

    @Before
    public void pre() {
        try {
            LKLConfig.initSDK();
        } catch (SDKException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        String json = "{\n" +
                "  \"ver\": \"3.0\",\n" +
                "  \"timestamp\": \"1724842957000\",\n" +
                "  \"req_id\": \"req1234567439\",\n" +
                "  \"req_data\": {\n" +
                "    \"merchant_no\": \"8224910581208RL\",\n" +
                "    \"org_code\": \"1951582\"\n" +
                "  }\n" +
                "}\n" +
                "\n";
        try {
            json = LKLSDK.sm4Encrypt(json, LKLConfig.appId);
            String response = LKLSDK.httpPost("https://test.wsmsd.cn/sit/api/v3/tkbs/open_merchant_submer", json);
            response = LKLSDK.sm4Decrypt(response, LKLConfig.appId);
            log.info("response: \r\n{}", response);
        } catch (SDKException e) {
            e.printStackTrace();
        }
    }

}
