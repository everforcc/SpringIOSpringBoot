package cn.cc.lkl.merchant_encry;

import cn.cc.lkl.dto.LKLCommonResponse;
import cn.cc.lkl.dto.merchantencry.V3TkbsCustomerFileUploadRequest;
import cn.cc.lkl.sdk.config.LKLConfig;
import cn.cc.lkl.util.LKLPost;
import com.lkl.laop.sdk.exception.SDKException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class V3TkbsCustomerFileUploadRequestTest {

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
        V3TkbsCustomerFileUploadRequest request = new V3TkbsCustomerFileUploadRequest();
        try {
            String fileBase64 = Base64.encodeBase64String(Files.readAllBytes(Paths.get("C:\\Users\\znkj\\Desktop\\temp\\百度.png")));
            request.setFileBase64(fileBase64);
            request.setImgType("SHOP_OUTSIDE_IMG");
            LKLCommonResponse lklCommonResponse = LKLPost.httpPost(request);
            if (lklCommonResponse.isSuccess()) {
                log.info("response 成功: \r\n{}", lklCommonResponse);
            } else {
                log.info("response 失败: \r\n{}", lklCommonResponse);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
