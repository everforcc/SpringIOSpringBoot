package org.apache.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * 文件上传
 */
@Slf4j
public class HttpClientsDownTest {

    @Test
    public void down(){
        downFile();
    }

    private void downFile() {
        String url = "";
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            URI build = new URIBuilder(url)
                    .build();
            HttpPost httpPost = new HttpPost(build.toString());
            httpPost.setHeader("Authorization", "token");
            CloseableHttpResponse httpResponse = httpclient.execute(httpPost);
            // 获取响应状态码
            int resCode = httpResponse.getStatusLine().getStatusCode();
            // 获取响应内容
            if (resCode == 200) {
                HttpEntity entity = httpResponse.getEntity();
                if (httpResponse.containsHeader("Content-Type")) {
                    String jsonHeader = httpResponse.getFirstHeader("Content-Type").getValue();
                    // application/json;charset=UTF-8
                    if ("application/json".equals(jsonHeader)) {
                        HttpEntity strEntity = httpResponse.getEntity();
                        String result = EntityUtils.toString(strEntity);//, StandardCharsets.UTF_8)
                        log.info("uploadMultipartFile: {}", result);
                        return;
                    }
                }
                if (httpResponse.containsHeader("Content-Disposition")) {
                    String Content_Disposition = httpResponse.getFirstHeader("Content-Disposition").getValue();
                    log.info("Content-Disposition: {}", Content_Disposition);
                }
                try {
                    // 从响应头取出文件名
                    FileUtils.writeByteArrayToFile(new File("D:\\1.txt"), EntityUtils.toByteArray(entity));
//                    out.write(EntityUtils.toByteArray(entity));
                } catch (IOException e) {
                    throw new RuntimeException("Error while exporting the Excel file", e);
                } finally {
                    EntityUtils.consume(entity);
                }
            }
        } catch (Exception e) {
            log.error("请求失败", e);
            throw new RuntimeException("请求失败");
        }
    }

}
