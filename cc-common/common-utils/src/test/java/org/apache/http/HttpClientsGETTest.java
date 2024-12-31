package org.apache.http;

import cn.cc.utils.commons.lang.RObjectsUtils;
import cn.cc.utils.constant.LogConstant;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

public class HttpClientsGETTest {

    @Test
    public void getTest(){
        get();
    }

    /**
     * 简单get请求
     */
    private static void get(){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpGet httpGet = new HttpGet("https://www.baidu.com/");
        try {
            response = httpClient.execute(httpGet);

            System.out.println(response.getStatusLine());

            HttpEntity entity = response.getEntity();
            System.out.println("请求返回状态: " + response.getStatusLine().getStatusCode());
            if (entity != null) {
                System.out.println(LogConstant.SPLIT);
                System.out.println(LogConstant.SPLIT);
                System.out.println("Response content length: " + entity.getContentLength());
                System.out.println(EntityUtils.toString(entity));

                /**
                 * 释放资源
                 */
                EntityUtils.consume(entity);

                System.out.println(LogConstant.SPLIT);
                System.out.println(LogConstant.SPLIT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            /**
             * 释放资源
             */
            if(RObjectsUtils.nonNull(response)){
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
