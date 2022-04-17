package com.cc.sp70craw.utils.craw.http.impl;

import com.cc.sp70craw.utils.craw.http.IHttp;
import com.cc.sp70craw.utils.craw.pool.HttpClientPool;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

public class HttpClientIHttp implements IHttp {

    @SneakyThrows
    @Override
    public String requestUrl(String url) {
        HttpClient httpClient = HttpClientPool.getClient();
        HttpGet httpGet = new HttpGet(url);

        HttpResponse httpResponse = httpClient.execute(httpGet);
        StatusLine statusLine = httpResponse.getStatusLine();
        if(statusLine.getStatusCode() != 200){
            System.err.println(url + " :返回异常");
            throw new RuntimeException("返回异常");
        }

        return EntityUtils.toString(httpResponse.getEntity(),"gbk");
    }

}
