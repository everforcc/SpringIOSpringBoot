package cn.cc.lkl.util;

import cn.cc.lkl.dto.LKLBaseRequest;
import cn.cc.lkl.dto.LKLBaseRequestV2;
import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.dto.LKLCommonResponseV2;
import cn.cc.lkl.sdk.config.LKLConfig;
import com.alibaba.fastjson.JSON;
import com.lkl.laop.sdk.LKLSDK;
import com.lkl.laop.sdk.exception.SDKException;
import com.lkl.laop.sdk.request.LklRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LKLPost {

    /**
     * sdk 自带，简化处理相应
     */
    public static LKLCommonResponse httpPost(LklRequest request) {
        return httpPost(request, false, false);
    }

    public static LKLCommonResponseV2 httpPostV2(LklRequest request) {
        return httpPostV2(request, false, false);
    }

    /**
     * sdk 自带，简化处理相应
     */
    public static LKLCommonResponse httpPost(LklRequest request, boolean reqEncrypt, boolean respDecrypt) {
        try {
            String response = LKLSDK.httpPost(request, reqEncrypt, respDecrypt);
            return JSON.parseObject(response, LKLCommonResponse.class);
        } catch (SDKException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static LKLCommonResponseV2 httpPostV2(LklRequest request, boolean reqEncrypt, boolean respDecrypt) {
        try {
            String response = LKLSDK.httpPost(request, reqEncrypt, respDecrypt);
            return JSON.parseObject(response, LKLCommonResponseV2.class);
        } catch (SDKException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 自定义请求
     */
    public static LKLCommonResponse httpPost(LKLBaseRequest lklBaseRequest) {
        return httpPost(lklBaseRequest, false, false);
    }

    /**
     * 自定义请求
     */
    public static LKLCommonResponse httpPost(LKLBaseRequest lklBaseRequest, boolean reqEncrypt, boolean respDecrypt) {
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

    public static LKLCommonResponseV2 httpPost(LKLBaseRequestV2 lklBaseRequest) {
        return httpPost(lklBaseRequest, false, false);
    }

    /**
     * 自定义请求
     */
    public static LKLCommonResponseV2 httpPost(LKLBaseRequestV2 lklBaseRequest, boolean reqEncrypt, boolean respDecrypt) {
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
            return JSON.parseObject(response, LKLCommonResponseV2.class);
        } catch (SDKException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
