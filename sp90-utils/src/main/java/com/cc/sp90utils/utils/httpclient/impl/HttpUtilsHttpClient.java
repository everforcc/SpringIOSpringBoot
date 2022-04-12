package com.cc.sp90utils.utils.httpclient.impl;

import com.cc.sp90utils.enums.HttpStateEnum;
import com.cc.sp90utils.enums.HttpTypeEnum;
import com.cc.sp90utils.utils.httpclient.HttpParam;
import com.cc.sp90utils.utils.httpclient.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class HttpUtilsHttpClient implements HttpUtils {

    public static void main(String[] args) {
        HttpParam httpParam = new HttpParam();
        httpParam.setUrl("https://www.baidu.com/");
        //httpParam.setCharset(charset);
        httpParam.setHttpTypeEnum(HttpTypeEnum.GET);
        httpParam.setTimeout(600);
        //httpParam.setHeaders(PropertiesHeader.ysCardMap());

        //requestForMsg(httpParam);
    }

    /**
     * 用传入的参数来进行区分
     */
    public String requestForMsg(HttpParam httpParam){

        /**
         * 1. 设置基本的请求头
         */
        BasicHeader basicHeader = new BasicHeader("X-Default-Header", "default header httpclient");
        List<Header> headerList = new ArrayList<>();
        headerList.add(basicHeader);



        /**
         * 2. 创建 HttpClient
         * HttpClient 目前实现类只有两个能用
         *  CloseableHttpClient
         *  InternalHttpClient
         *  MinimalHttpClient
         *
         */
        //HttpClient httpClient = HttpClients.createDefault();
        HttpClientBuilder httpClientBuilder = HttpClients.custom();

        /**
         * 2.1 网络代理
         * 一般的代理只能浏览器，代码要用，要开代理端口，在连接
         */
        HttpHost proxy = httpParam.getProxy().toHttpHost();
        if(Objects.nonNull(proxy)){
            httpClientBuilder.setProxy(proxy);
        }

        CloseableHttpClient httpclient = httpClientBuilder
                .setDefaultHeaders(headerList)
                .build();

        /**
         * 3 设置请求参数
         * 3.1 请求地址
         * 3.2 请求头
         * 3.3 请求参数
         *
         */

        /**
         * 3.1 拿到请求
         */

        RequestBuilder requestBuilder;
        if("GET".equals(httpParam.getHttpTypeEnum())){
            requestBuilder = RequestBuilder.get(httpParam.getUrl());
        }else if("POST".equals(httpParam.getHttpTypeEnum())){
            requestBuilder = RequestBuilder.post();
        }else {
            throw new RuntimeException("暂时不支持的类型: " + httpParam.getHttpTypeEnum());
        }
        //requestBuilder.setUri(httpParam.getUrl());

        /**
         * 3.2 设置请求头
         */
        Map<String,String> headerMap = httpParam.getHeaders();
        if(Objects.nonNull(headerMap)) {
            for (Map.Entry<String,String> entry : headerMap.entrySet()) {
                requestBuilder.setHeader((String) entry.getKey(), (String) entry.getValue());
            }
        }

        /**
         * 3.3 请求参数
         */
        String requestParam = httpParam.getRequestParam();
        if(!StringUtils.isEmpty(requestParam)){
            HttpEntity httpEntity = null;
            try {
                httpEntity = new StringEntity(requestParam);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            requestBuilder.setEntity(httpEntity);
        }


        /**
         * 3.3 生成请求对象
         */
        HttpUriRequest request = requestBuilder.build();

        /**
         * 简单get请求
         */
        //HttpGet httpGet = new HttpGet(httpParam.getUrl());
        HttpResponse httpResponse = null;
        try {

            /**
             * 设置延迟
             */
            httpParam.sleep();

            httpResponse = httpclient.execute(request);
            StatusLine statusLine = httpResponse.getStatusLine();

            if(HttpStateEnum._200.value!=statusLine.getStatusCode()){
                throw new RuntimeException(statusLine.getReasonPhrase());
            }

            log.info(" --- 分隔符 --- ");
            String result = EntityUtils.toString(httpResponse.getEntity(),httpParam.getCharset().charset);
            return result;
        } catch (Exception e) {

            log.error("异常: " + e);
            throw new RuntimeException(e);
        }finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                log.error("异常: " + e);
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 2. POST
     */


}
