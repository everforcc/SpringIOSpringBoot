package com.cc.sp90utils.utils.httpclient.impl;

import com.cc.sp90utils.enums.HttpTypeEnum;
import com.cc.sp90utils.utils.check.ObjectUtils;
import com.cc.sp90utils.utils.httpclient.HttpParam;
import com.cc.sp90utils.utils.httpclient.HttpUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

@Validated
public class HttpUtilsURLConnection implements HttpUtils {

    public static void main(String[] args) {
        HttpParam httpParam = new HttpParam();

        httpParam.setUrl("https://www.baidu.com/");
        //httpParam.setCharset(charset);
        httpParam.setHttpTypeEnum(HttpTypeEnum.GET);
        httpParam.setTimeout(600);
        //requestForMsg(httpParam);
    }

    public String requestForMsg(@Valid HttpParam httpParam) {

        URL url = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            url = new URL(httpParam.getUrl());
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;

            //可以设置请求类型等
            HttpTypeEnum httpTypeEnum = httpParam.getHttpTypeEnum();
            if(ObjectUtils.nonNull(httpTypeEnum)){
                httpURLConnection.setRequestMethod(httpTypeEnum.type);//请求类型
            }
            Map<String,String> headersMap = httpParam.getHeaders();
            if(Objects.nonNull(headersMap)) {
                for(Map.Entry<String,String> entry:headersMap.entrySet()) {
                    httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());//请求头设置
                }
            }
            //获取HttpURLConnection的输入流
            InputStream is = httpURLConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                stringBuffer.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return stringBuffer.toString();
    }

}
