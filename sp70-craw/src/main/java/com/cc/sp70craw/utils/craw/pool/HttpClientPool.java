package com.cc.sp70craw.utils.craw.pool;

import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpClientPool {

    /**
     * 超时后重试次数
     */
    private static int retryCount = 5;

    private static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    private static HttpRequestRetryHandler httpRequestRetryHandler = new StandardHttpRequestRetryHandler(5,true);

    static {
        //将最大连接数增加到200
        cm.setMaxTotal(200);
        //将每个路由的默认最大连接数增加到20
        cm.setDefaultMaxPerRoute(20);
        //将http://www.baidu.com:80的最大连接增加到50
        //HttpHost httpHost = new HttpHost("http://www.baidu.com",80);
        //connectionManager.setMaxPerRoute(new HttpRoute(httpHost),50);
    }

    public static HttpClient getClient(){
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setRetryHandler(httpRequestRetryHandler).build();
        return httpClient;
    }

}
