package cn.cc.huifu.regist;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
        params.put("req_seq_id", new Random().nextLong() % 1000000000000000000L + 1000000000000000000L + "");
        params.put("req_date", new SimpleDateFormat("yyyyMMdd").format(new Date()));
        // 国徽 F03
        // 人像面 F02
        // 结算银行卡 F13
        params.put("file_type", "F105");
        //如果商户还未成功进件huifu_id可以为空
//        params.put("huifu_id", "6666000151772004");
        String data = JSON.toJSONString(params);
        String localFile = "C:\\Users\\znkj\\Desktop\\新建文件夹\\线下经营-收银台.jpg";
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
    // {"data":{"resp_desc":"成功","file_id":"06dbba25-f9dc-3a2b-b3e1-98e730525566","resp_code":"00000000"}}
    // 正面.jpg
    // {"data":{"resp_desc":"成功","file_id":"79b7fd5a-a009-3a75-bf06-dc1544d13d0e","resp_code":"00000000"}}
    // 结算卡正面
    // {"data":{"resp_desc":"成功","file_id":"8d88f358-a753-3670-b0a9-65f7b7a54dce","resp_code":"00000000"}}
    // 线下经营-门头照
    // {"data":{"resp_desc":"成功","file_id":"896ee35f-d91d-35af-9ca9-30cd0ac668ba","resp_code":"00000000"}}
    // 线下经营-内景照
    // {"data":{"resp_desc":"成功","file_id":"dcf8035e-68ba-3630-8e87-26f626b5e175","resp_code":"00000000"}}
    // 线下经营-收银台
    // {"data":{"resp_desc":"成功","file_id":"09c09fc2-d01a-30e8-9766-259c15b6649b","resp_code":"00000000"}}

}
