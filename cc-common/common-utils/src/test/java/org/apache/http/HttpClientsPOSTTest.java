package org.apache.http;

import cn.cc.utils.commons.lang.RObjectsUtils;
import cn.cc.utils.constant.CharsetsConstant;
import cn.cc.utils.constant.LogConstant;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试 HttpClients POST
 * 2.1 发送application/json类型的post请求,一般和其他系统对接也就是用这个了；
 * 2.2 form表单 发送application/x-www-form-urlencoded类型的post请求；
 */
public class HttpClientsPOSTTest {

    @Test
    public void postTest() {
        post();
    }

    /**
     * 简单psot请求
     */
    private static void post() {

        // 1. 创建默认的httpClient实例.
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 2. 创建httppost
        HttpPost httpPost = new HttpPost("http://localhost:81/open/auth/login");

        /**
         * 3.1 参数: form表单
         */
        UrlEncodedFormEntity urlEncodedFormEntity;
        // 创建参数队列
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("username", "cc"));
        formparams.add(new BasicNameValuePair("password", "asdfasdf"));

        /**
         * 3.2 参数: string格式
         */
        HttpEntity reqEntity;

        CloseableHttpResponse response = null;
        try {
            urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, CharsetsConstant.UTF_8);
            reqEntity = new StringEntity("{\n" +
                    "\"username\": \"cc\",\n" +
                    "\"password\": \"asdfasdf\"\n" +
                    "}");

            // 4. 设置请求参数
            httpPost.setEntity(reqEntity);
            httpPost.addHeader("Content-Type", "application/json");

            System.out.println("executing request " + httpPost.getURI());

            /**
             * 5. 拿到返回数据
             */
            response = httpClient.execute(httpPost);

            System.out.println("返回值状态: " + response.getStatusLine().getStatusCode());

            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                System.out.println(LogConstant.SPLIT);
                System.out.println("Response content: " + EntityUtils.toString(resEntity, "UTF-8"));
                System.out.println(LogConstant.SPLIT);

                EntityUtils.consume(resEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            /**
             * 释放资源
             */
            if (RObjectsUtils.nonNull(response)) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            if (RObjectsUtils.nonNull(httpClient)) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
