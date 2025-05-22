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

        params.put("file_type", ZnHFFileContants.XIAN_XIA_NEI_JING_ZHAO);
        //如果商户还未成功进件huifu_id可以为空
//        params.put("huifu_id", "6666000151772004");
        String data = JSON.toJSONString(params);
        String localFile = "D:\\temp\\线下经营-内景照.jpg";
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
    // {"data":{"resp_desc":"成功","file_id":"4610bef1-2df1-3f0a-ba22-32a4f1e9e50c","resp_code":"00000000"}}
    // 身份证人像面
    // {"data":{"resp_desc":"成功","file_id":"76ec9902-bbb5-37b3-9b64-0ea4e02124e2","resp_code":"00000000"}}
    // 结算卡正面
    // {"data":{"resp_desc":"成功","file_id":"a5ca3709-22a6-3376-b0e0-2fdf51c23ee2","resp_code":"00000000"}}
    // 线下经营-收银台
    // {"data":{"resp_desc":"成功","file_id":"7cf4f5ce-4d00-321b-8bf7-b1d5770f9607","resp_code":"00000000"}}
    // 线下经营-门头照
    // {"data":{"resp_desc":"成功","file_id":"c71d64fe-0329-3282-9b84-a9deedf32a31","resp_code":"00000000"}}
    // 线下经营-内景照
    // {"data":{"resp_desc":"成功","file_id":"be544e2e-6257-3736-928c-6516a567437c","resp_code":"00000000"}}

}
