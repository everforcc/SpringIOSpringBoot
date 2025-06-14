package cn.cc.huifu.business.file;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cn.cc.huifu.constants.ZnHFFileContants;
import cn.cc.huifu.constants.ZnHFReqParamsContants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.alibaba.fastjson.JSON;
public class ZnHFFileUpload {

    public static void main(String[] args) throws Exception {
        //图片上传接口地址
        String url = "https://api.huifu.com/v2/supplementary/picture";
        // 请求参数
        Map<String, Object> params = new HashMap<String, Object>();
        // 请求流水号，需保证当天商户下唯一，推荐采用日期时间+几位流水号的形式
        params.put("req_seq_id", ZnHFReqParamsContants.REQ_SEQ_ID);
        params.put("req_date", ZnHFReqParamsContants.REQ_DATE);

        params.put("file_type", ZnHFFileContants.XIAN_XIA_MEN_TOU_ZHAO);
        //如果商户还未成功进件huifu_id可以为空
//        params.put("huifu_id", "6666000151772004");
        String data = JSON.toJSONString(params);
        String localFile = "D:\\temp\\mtz.jpg";
        File file = new File(localFile);

        CloseableHttpClient httpclient1 = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = null;

        try {
            HttpPost httpPost = new HttpPost(url);
            ContentType contentType = ContentType.create("text/plain", Charset.forName("UTF-8"));
            MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
            mEntityBuilder.addTextBody("sys_id", "6666000151772004", contentType);
            mEntityBuilder.addTextBody("product_id", "PAYUN", contentType);
            mEntityBuilder.addTextBody("data", data, contentType);
            mEntityBuilder.addBinaryBody("file", file);

            httpPost.setEntity(mEntityBuilder.build());
            response = httpclient1.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = response.getEntity();
                result = EntityUtils.toString(resEntity);
                System.out.print(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 国徽
    // {"data":{"resp_desc":"成功","file_id":"7c15494a-544b-351f-810e-2713b83071fc","resp_code":"00000000"}}
    // 身份证人像面
    // {"data":{"resp_desc":"成功","file_id":"ead9f625-a40d-3b3d-8ce7-6429de85298f","resp_code":"00000000"}}
    // 结算卡正面
    // {"data":{"resp_desc":"成功","file_id":"2847276a-4809-3a86-8efb-f9c8108a2423","resp_code":"00000000"}}
    // 线下经营-收银台
    // {"data":{"resp_desc":"成功","file_id":"881366c7-7be8-3a07-aec8-c9ec8c7607f4","resp_code":"00000000"}}
    // 线下经营-门头照
    // {"data":{"resp_desc":"成功","file_id":"507da3d0-53aa-35f9-a643-fdc16f685a0d","resp_code":"00000000"}}
    // 线下经营-内景照
    // {"data":{"resp_desc":"成功","file_id":"6ecb4693-7e66-3027-a599-3a3805a2e266","resp_code":"00000000"}}

}
