package cn.cc.lkl.controller;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.dto.merchant.V3MmsOpenApiEcApplyCallbackResponse;
import com.lkl.laop.sdk.LKLSDK;
import com.lkl.laop.sdk.exception.SDKException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * http://t9338923.natappfree.cc/lkl/res
 */
@Slf4j
@RestController
@RequestMapping("/lkl")
public class HTController {

    /**
     * @param httpServletRequest
     */
    @PostMapping("/res")
    public void refundPost(HttpServletRequest httpServletRequest) {
        //3. 回调(如果不指定,则使用第一个初始化的appid)
        try {
            log.info("refundPost");
            // 从request attribute中获取已在拦截器中解析并验签的body数据
            String body = (String) httpServletRequest.getAttribute("LKL_REQUEST_BODY");
            System.out.println("body: \r\n" + body);
            /**
             * cc
             * body:
             * {"ecApplyId":1018887776751775744,"ecName":"特约商户支付服务合作协议V3.3","ecNo":"QY20250912526101505","ecStatus":"COMPLETED","orderNo":"687d065c2d444b56aa522f841cf850ae","orgId":1,"version":"1.0"}
             */
            V3MmsOpenApiEcApplyCallbackResponse v3MmsOpenApiEcApplyCallbackResponse = JsonUtil.fromJson(body, V3MmsOpenApiEcApplyCallbackResponse.class);
            System.out.println("v3MmsOpenApiEcApplyCallbackResponse: \r\n" + v3MmsOpenApiEcApplyCallbackResponse);
            // 判断是否处理成功
            log.info("处理结果: {}", v3MmsOpenApiEcApplyCallbackResponse.resultCompleted());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/res")
    public void refundGet(HttpServletRequest httpServletRequest) {
        //3. 回调(如果不指定,则使用第一个初始化的appid)
        try {
            log.info("refundGet");
            String body = LKLSDK.notificationHandle(httpServletRequest);
            System.out.println("body: \r\n" + body);
        } catch (SDKException e) {
            e.printStackTrace();
        }
    }

}
