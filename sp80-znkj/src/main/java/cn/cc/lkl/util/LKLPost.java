package cn.cc.lkl.util;

import cn.cc.lkl.dto.LKLBaseRequest;
import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.sdk.config.LKLConfig;
import com.alibaba.fastjson.JSON;
import com.lkl.laop.sdk.LKLSDK;
import com.lkl.laop.sdk.exception.SDKException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LKLPost {

    public static LKLCommonResponse httpPost(LKLBaseRequest lklBaseRequest) {
        return httpPostWithSm4(lklBaseRequest, false, false);
    }

    public static LKLCommonResponse httpPostWithSm4(LKLBaseRequest lklBaseRequest, boolean reqEncrypt, boolean respDecrypt) {
        try {
            String body = lklBaseRequest.toBody();
            log.info("请求参数: {}", body);
            if (reqEncrypt) {
                body = LKLSDK.sm4Encrypt(lklBaseRequest.toBody(), LKLConfig.appId);
            }
            String response = LKLSDK.httpPost(LKLConfig.serverUrl + lklBaseRequest.getFunctionCode().getUrl(), body);
            if (respDecrypt) {
                response = LKLSDK.sm4Decrypt(response, LKLConfig.appId);
            }
            return JSON.parseObject(response, LKLCommonResponse.class);
        } catch (SDKException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
