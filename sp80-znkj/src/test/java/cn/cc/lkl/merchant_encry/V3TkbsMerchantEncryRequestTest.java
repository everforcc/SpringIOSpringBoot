package cn.cc.lkl.merchant_encry;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.dto.merchantencry.V3TkbsMerchantEncryRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 商户进件
 */
@Slf4j
public class V3TkbsMerchantEncryRequestTest {

    @Test
    public void jsonToDto(){
        String filePath = "C:/cc/code/gitee/SpringIOSpringBoot/sp80-znkj/docs/lkl/接口文档/test/V3TkbsMerchantEncryRequestTest.json";
        String json = null;
        try {
            json = new String(Files.readAllBytes(Paths.get(filePath)));
            // json转V3TkbsMerchantEncryRequest
            JSONObject jsonObject = JSON.parseObject(json);

            V3TkbsMerchantEncryRequest request = JsonUtil.fromJson(jsonObject.getString("req_data"), V3TkbsMerchantEncryRequest.class);
            log.info("request: \r\n{}", request);
            // request 转json
            String json2 = JSON.toJSONString(request);
            log.info("json2: \r\n{}", json2);
            // 对象转json的时候要恢复下划线，不要驼峰命名法
            String json3 = JsonUtil.toJson(request);
            log.info("json3: \r\n{}", json3);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
