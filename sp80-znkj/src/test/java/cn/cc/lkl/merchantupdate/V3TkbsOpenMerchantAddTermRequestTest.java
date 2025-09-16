package cn.cc.lkl.merchantupdate;

import cn.cc.config.JsonUtil;
import cn.cc.lkl.LKLBaseTest;
import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.dto.merchantupdate.*;
import cn.cc.lkl.sdk.config.LKLConfig;
import cn.cc.lkl.util.LKLPost;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 增终进件
 */
@Slf4j
public class V3TkbsOpenMerchantAddTermRequestTest extends LKLBaseTest {

    @Test
    public void test() throws Exception {
        V3TkbsOpenMerchantAddTermRequest request = new V3TkbsOpenMerchantAddTermRequest();
        request.setMerchantNo("8224910581208RL");
        request.setBzPos("WECHAT_PAY");
        request.setTermNum(1);
        request.setShopId(36362L);
        request.setOrgCode(LKLConfig.orgCode);

        FeeInfoDto feeInfoDto = new FeeInfoDto();

        List<FeeInfoDto> feeInfoDtoList = new ArrayList<>();
        feeInfoDtoList.add(feeInfoDto);
        request.setFees(feeInfoDtoList);

        List<AttachmentDto> attachments = new ArrayList<>();
        request.setAttachments(attachments);

        log.info("增终进件:请求 \r\n{}", JsonUtil.toJson(request));

        LKLCommonResponse lklCommonResponse = LKLPost.httpPost(request, true, true);
        log.info("增终进件:结果 \r\n{}", JsonUtil.toJson(lklCommonResponse));
        log.info("response: \r\n{}", lklCommonResponse.toString());
        log.info("response: \r\n{}", JSONObject.toJSONString(lklCommonResponse));
        log.info("response: \r\n{}", lklCommonResponse.getRespData());
        Object respData = lklCommonResponse.getRespData();

        V3TkbsOpenMerchantAddTermResponse response = JSONObject.parseObject(respData.toString(), V3TkbsOpenMerchantAddTermResponse.class);
        log.info("response: \r\n{}", response.toString());
        log.info("response: \r\n{}", JSONObject.toJSONString(response));
    }

    @Test
    public void testUpdateResult() throws Exception {
        V3TkbsCustomerUpdateReviewRequest request = new V3TkbsCustomerUpdateReviewRequest();
        request.setRevieRelatedId("351078265970775");
        request.setOrgCode(LKLConfig.orgCode);

        log.info("增终进件结果:请求 \r\n{}", JsonUtil.toJson(request));
        LKLCommonResponse lklCommonResponse = LKLPost.httpPost(request);
        log.info("增终进件结果:结果 \r\n{}", JsonUtil.toJson(lklCommonResponse));
    }

}
