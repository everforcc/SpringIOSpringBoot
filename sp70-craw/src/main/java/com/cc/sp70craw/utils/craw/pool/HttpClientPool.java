package com.cc.sp70craw.utils.craw.pool;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpClientPool {

    /**
     * 超时后重试次数
     */
    private static int retryCount = 5;

    private static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    private static HttpRequestRetryHandler httpRequestRetryHandler = new StandardHttpRequestRetryHandler(retryCount,true);

    static {
        //将最大连接数增加到200
        cm.setMaxTotal(4);
        //将每个路由的默认最大连接数增加到20
        cm.setDefaultMaxPerRoute(2);
        //将http://www.baidu.com:80的最大连接增加到50
        //HttpHost httpHost = new HttpHost("http://www.baidu.com",80);
        //cm.setMaxPerRoute(new HttpRoute(httpHost),50);
    }

    /**
     * 调用者注意
     * RequestConfig config = RequestConfig.custom().setConnectTimeout(5000) //连接超时时间
     *                                 .setConnectionRequestTimeout(500) //从线程池中获取线程超时时间
     *                                 .setSocketTimeout(8000) //设置数据超时时间
     *                                 //.setStaleConnectionCheckEnabled(true) //提交请求前检查连接是否可用
     *                                 .build();
     * 必须设置 setConnectionRequestTimeout 从线程池中获取线程的超时时间，否则会一直等待
     */
    public static HttpClient getClient(){
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setRetryHandler(httpRequestRetryHandler).build();
        return httpClient;
    }

    public static void main(String[] args) {
        /**
         * 测试不回收的异常
         */

        try {
            for (int i = 0; i < 10; i++) {
                final int index = i;
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("开始新线程: " + index);
                        HttpClient httpClient = getClient();
                        HttpGet httpGet = new HttpGet("https://www.baidu.com/");

                        RequestConfig config = RequestConfig.custom().setConnectTimeout(5000) //连接超时时间
                                .setConnectionRequestTimeout(500) //从线程池中获取线程超时时间
                                .setSocketTimeout(8000) //设置数据超时时间
                                //.setStaleConnectionCheckEnabled(true) //提交请求前检查连接是否可用
                                .build();
                        httpGet.setConfig(config);

                        try {
                            HttpResponse httpResponse = httpClient.execute(httpGet);
                            int code = httpResponse.getStatusLine().getStatusCode();
                            HttpEntity httpEntity = httpResponse.getEntity();
                            //String result = EntityUtils.toString(httpEntity);
                            //EntityUtils.consume(httpEntity);
                            System.out.println(index + ": [index]" + httpClient.toString());
                            System.out.println(index + ": [index]" + "code: " + code);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("err: " + e.toString());
        }

    }

}
