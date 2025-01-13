package org.apache.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;

/**
 * 文件上传
 */
@Slf4j
public class HttpClientsDownTest {

    @Test
    public void down() {
        downFile();
    }

    private void downFile() {
        String url = "http://127.0.0.1:8043/down?path=1736739944299百度.png1";
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            URI build = new URIBuilder(url)
                    .build();
            HttpGet httpGet = new HttpGet(build.toString());
            httpGet.setHeader("Authorization", "token");
            CloseableHttpResponse httpResponse = httpclient.execute(httpGet);
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
                String Content_Disposition = "";
                if (httpResponse.containsHeader("Content-Disposition")) {
                    Content_Disposition = httpResponse.getFirstHeader("Content-Disposition").getValue();
                    log.info("Content-Disposition: {}", Content_Disposition);
                }
                try {
                    if (StringUtils.isNotEmpty(Content_Disposition)) {
                        Content_Disposition = Content_Disposition.replace("attachment;filename=", "");
                        Content_Disposition = URLDecoder.decode(Content_Disposition, "UTF-8");
                    } else {
                        Content_Disposition = "1.txt";
                    }
                    System.out.println("下载文件文件名: " + Content_Disposition);
                    // 从响应头取出文件名
                    FileUtils.writeByteArrayToFile(new File("D:/cache/test/" + Content_Disposition), EntityUtils.toByteArray(entity));
                    // 写到http
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
