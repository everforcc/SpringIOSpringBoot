package cn.cc.lkl.merchantupdate;

import cn.cc.lkl.LKLBaseTest;
import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.dto.merchantupdate.AttachmentDto;
import cn.cc.lkl.dto.merchantupdate.FeeInfoDto;
import cn.cc.lkl.dto.merchantupdate.V3TkbsOpenMerchantAddTermRequest;
import cn.cc.lkl.dto.merchantupdate.V3TkbsOpenMerchantAddTermResponse;
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
    public void test() {
        V3TkbsOpenMerchantAddTermRequest request = new V3TkbsOpenMerchantAddTermRequest();
        FeeInfoDto feeInfoDto = new FeeInfoDto();

        List<FeeInfoDto> feeInfoDtoList = new ArrayList<>();
        feeInfoDtoList.add(feeInfoDto);
        request.setFees(feeInfoDtoList);

        List<AttachmentDto> attachments = new ArrayList<>();
        request.setAttachments(attachments);

        LKLCommonResponse lklCommonResponse = LKLPost.httpPost(request, true, true);
        log.info("response: \r\n{}", lklCommonResponse.toString());
        log.info("response: \r\n{}", JSONObject.toJSONString(lklCommonResponse));
        log.info("response: \r\n{}", lklCommonResponse.getRespData());
        Object respData = lklCommonResponse.getRespData();

        V3TkbsOpenMerchantAddTermResponse response = JSONObject.parseObject(respData.toString(), V3TkbsOpenMerchantAddTermResponse.class);
        log.info("response: \r\n{}", response.toString());
        log.info("response: \r\n{}", JSONObject.toJSONString(response));
    }

}
