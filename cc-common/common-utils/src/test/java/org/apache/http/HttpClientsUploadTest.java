package org.apache.http;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * 3. 发送multipart/form-data类型上传文件的post请求，需要一个依赖httpmime。
 */
public class HttpClientsUploadTest {

    @Test
    public void upload() {
        uploadFileBody();
    }

    /**
     * 上传文件
     */
    private static void uploadFileBody() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost("http://localhost:81/c/upload/write");
            httppost.addHeader("token", "7a1620347169f7942aa59cda3a61eebd88d610630b9751a8445dfb4e69838707cdc8f7cf3e53cadddaf3014d75ecd5290a640d0167e2750cee4319a64c9a5acb2c21a45eac123acc3dca249efcc52963fdf16e7241593ef06bef4b326a6581904c1b4b85d1caec83b0714b7d9f63fa2bb96830488925f29b1e6953e84d9b217e791a56b96626d383711a1f00c46ee1e5a8da8308f07affca5336c064883ba7bcabce0f854b2adb8b98d34e873eb7ab56920e4aabf2b8051a9cb2e712bf53c0554bb9f6dc8058a895fa7ddcad1a3d671d3800ff1d51848e2230505f40261d5999f4dcee0febf0e2cef32328cf0152bd1ee25fdd2442967108e672b724487ffad508bcebb01ed57c13db95693a104ce896bed4c09307653f63d253537a32806a93975712196f55014b9787a6638aa2cf969efb7e5514ad73a07253676c42e1337747977deb79c77b66f06acd8385b71df03b04f41e0cb4a5e1bb3a628904653941b947bd92724889abc97a070b3203cf9ac56922f23227a995ecb68c567877e6979fab5b300266e4c2f25bc94a51b778d631063e58db88f0d230d5950071f97e33adba019ae0c45f9c5e5868ff613c1a671753b4d7408f1cd36cc473e7369a5578f2bb2941133f87040811f883700be01f25e207254adce9c0f10e6fbef3f90658");
            FileBody file = new FileBody(new File("E:\\test\\水晶蝶 - 情动 - 副本.mp3"));
            StringBody comment = new StringBody("A binary file of some kind", ContentType.TEXT_PLAIN);
            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("file", file).build();//.addPart("comment", comment)
            httppost.setEntity(reqEntity);
            System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    System.out.println("Response content length: " + resEntity.getContentLength());
                    System.out.println("Response content: " + EntityUtils.toString(resEntity, "UTF-8"));
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
