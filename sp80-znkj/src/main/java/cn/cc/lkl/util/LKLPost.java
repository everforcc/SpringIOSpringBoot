package cn.cc.lkl.util;

import cn.cc.lkl.dto.*;
import cn.cc.lkl.sdk.config.LKLConfig;
import cn.cc.lkl.sdk.config.LKLConfigProd;
import com.alibaba.fastjson.JSON;
import com.lkl.laop.sdk.LKLSDK;
import com.lkl.laop.sdk.exception.SDKException;
import com.lkl.laop.sdk.request.LklRequest;
import com.lkl.laop.sdk.request.V2CommRequest;
import com.lkl.laop.sdk.request.V3CommRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LKLPost {

    private static String appId = LKLConfigProd.appId;
    private static String serverUrl = LKLConfigProd.serverUrl;

    /**
     * sdk 自带，简化处理相应
     */
    public static LKLCommonResponse httpPost(V3CommRequest request) {
        return httpPost(request, false, false);
    }

    public static LKLCommonResponseV2 httpPost(LKLBaseRequestV1 request) {
        return httpPost(request, false, false);
    }

    public static LKLCommonResponseV2 httpPost(V2CommRequest request) {
        return httpPost(request, false, false);
    }

    /**
     * sdk 自带，简化处理相应
     */
    public static LKLCommonResponse httpPost(V3CommRequest request, boolean reqEncrypt, boolean respDecrypt) {
        try {
            String response = LKLSDK.httpPost(request, reqEncrypt, respDecrypt);
            return JSON.parseObject(response, LKLCommonResponse.class);
        } catch (SDKException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static LKLCommonResponseV2 httpPost(V2CommRequest request, boolean reqEncrypt, boolean respDecrypt) {
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
                body = LKLSDK.sm4Encrypt(lklBaseRequest.toBody(), appId);
            }
            String response = LKLSDK.httpPost(serverUrl + lklBaseRequest.getFunctionCode().getUrl(), body);
            if (respDecrypt) {
                response = LKLSDK.sm4Decrypt(response, appId);
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
                body = LKLSDK.sm4Encrypt(lklBaseRequest.toBody(), appId);
            }
            String response = LKLSDK.httpPost(serverUrl + lklBaseRequest.getFunctionCode().getUrl(), body);
            if (respDecrypt) {
                response = LKLSDK.sm4Decrypt(response, appId);
            }
            return JSON.parseObject(response, LKLCommonResponseV2.class);
        } catch (SDKException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static LKLCommonResponseV2 httpPost(LKLBaseRequestV1 lklBaseRequest, boolean reqEncrypt, boolean respDecrypt) {
        try {
            String body = lklBaseRequest.toBody();
            log.info("请求参数: {}", body);
            if (reqEncrypt) {
                body = LKLSDK.sm4Encrypt(lklBaseRequest.toBody(), appId);
            }
            String response = LKLSDK.httpPost(serverUrl + lklBaseRequest.getFunctionCode().getUrl(), body);
            if (respDecrypt) {
                response = LKLSDK.sm4Decrypt(response, appId);
            }
            return JSON.parseObject(response, LKLCommonResponseV2.class);
        } catch (SDKException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
