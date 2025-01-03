package org.apache.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Slf4j
public class HttpClientsMultipartFileTest {

    @Test
    public void upload() {
        MultipartFile file = null;
        uploadMultipartFile("", null);
    }

    private static void uploadMultipartFile(String url, MultipartFile file) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            URI build = new URIBuilder(url)
                    .build();
            HttpPost httpPost = new HttpPost(build.toString());
            httpPost.setHeader("Authorization", "token");
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("file", file.getInputStream(), ContentType.create(file.getContentType()), file.getOriginalFilename());

            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(StandardCharsets.UTF_8);
            HttpEntity multipart = builder.build();
            httpPost.setEntity(multipart);

            CloseableHttpResponse httpResponse = httpclient.execute(httpPost);
            // 获取响应状态码
            int resCode = httpResponse.getStatusLine().getStatusCode();
            // 获取响应内容
            if (resCode == 200) {
                HttpEntity entity = httpResponse.getEntity();
                String res = EntityUtils.toString(entity);
                log.info("uploadMultipartFile:{}, response:{}", resCode, res);
            }
        } catch (Exception e) {
            log.error("请求失败", e);
            throw new RuntimeException("请求失败");
        }
    }

}
