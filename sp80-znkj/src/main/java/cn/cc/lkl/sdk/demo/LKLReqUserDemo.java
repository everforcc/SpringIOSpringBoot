package cn.cc.lkl.sdk.demo;

import cn.cc.lkl.dto.CommonRequestDTO;
import cn.cc.lkl.sdk.config.LKLConfigDemo;
import com.alibaba.fastjson.JSONObject;
import com.lkl.laop.sdk.LKLSDK;
import com.lkl.laop.sdk.exception.SDKException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 拉卡拉
 * 用户自定义参数测试
 */
@Slf4j
public class LKLReqUserDemo {

    public static void main(String[] args) {
        try {
            LKLConfigDemo.demoConfig4();
//            demoUpload(LKLConfigDemo.serverUrl);
            demoOrganizationParentCode(LKLConfigDemo.serverUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 商户进件文件上传
     *
     * @param serverUrl
     * @throws SDKException
     * @throws IOException
     */
    public static void demoUpload(String serverUrl) throws SDKException, IOException {
        CommonRequestDTO commRequest = new CommonRequestDTO();

        JSONObject reqData = new JSONObject();

        String fileBase64 = Base64.encodeBase64String(Files.readAllBytes(Paths.get("C:\\Users\\znkj\\Desktop\\temp\\百度.png")));

        //        System.out.println("fileBase64:" + fileBase64);

        reqData.put("file_base64", "data:image/png;base64," + fileBase64);
        reqData.put("img_type", "SHOP_OUTSIDE_IMG");
        reqData.put("is_ocr", "false");
        reqData.put("sourcechnl", "0");
        reqData.put("prefix", "reg");

        commRequest.setReq_data(reqData);

        String response4 = LKLSDK.httpPost(serverUrl + "/api/v3/tkbs/customer/file/upload", JSONObject.toJSONString(commRequest));

        log.info("response: \r\n{}", response4);
    }

    /**
     * 机构查询
     *
     * @throws SDKException
     */
    public static void demoOrganizationParentCode(String serverUrl) throws SDKException {
        CommonRequestDTO commRequest = new CommonRequestDTO();

        JSONObject reqData = new JSONObject();
        reqData.put("parent_code", "991000");
        reqData.put("org_code", 1);
        commRequest.setReq_data(reqData);
        String json = JSONObject.toJSONString(commRequest);
        log.info("json: \r\n{}", json);
        String response = LKLSDK.httpPost(serverUrl + "/api/v3/tkbs/organization_parent_code", json);

        log.info("response: \r\n{}", response);
    }

}
