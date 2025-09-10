package cn.cc.lkl.merchant;

import cn.cc.lkl.LKLBaseTest;
import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.dto.merchant.V3TkbsCustomerFileUploadRequest;
import cn.cc.lkl.util.LKLPost;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class V3TkbsCustomerFileUploadRequestTest extends LKLBaseTest {

    @Test
    public void test() {
        V3TkbsCustomerFileUploadRequest request = new V3TkbsCustomerFileUploadRequest();
        try {
            String fileBase64 = Base64.encodeBase64String(Files.readAllBytes(Paths.get("D:\\cache\\BaiduSyncdisk\\znkj\\project\\汇付\\用户信息\\cc\\syt.jpg")));
            request.setFileBase64(fileBase64);
            request.setImgType("CHECKSTAND_IMG");
            LKLCommonResponse lklCommonResponse = LKLPost.httpPost(request);
            if (lklCommonResponse.resultSuccess()) {
                log.info("response 成功: \r\n{}", lklCommonResponse);
            } else {
                log.info("response 失败: \r\n{}", lklCommonResponse);
            }
            log.info("response: \r\n{}", JSONObject.toJSONString(lklCommonResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
