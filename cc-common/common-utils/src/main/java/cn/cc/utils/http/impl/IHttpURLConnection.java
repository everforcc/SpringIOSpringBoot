package cn.cc.utils.http.impl;

import cn.cc.utils.enums.HttpTypeEnum;
import cn.cc.utils.commons.lang.RObjectsUtils;
import cn.cc.utils.http.dto.HttpParamDto;
import cn.cc.utils.http.IHttp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

public class IHttpURLConnection implements IHttp {

    public static void main(String[] args) {
        HttpParamDto httpParamDto = new HttpParamDto();

        httpParamDto.setUrl("https://www.baidu.com/");
        //httpParam.setCharset(charset);
        httpParamDto.setHttpTypeEnum(HttpTypeEnum.GET);
        httpParamDto.setTimeout(600);
        //requestForMsg(httpParam);
    }

    public String requestForMsg( HttpParamDto httpParamDto) {

        URL url = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            url = new URL(httpParamDto.getUrl());
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;

            //可以设置请求类型等
            HttpTypeEnum httpTypeEnum = httpParamDto.getHttpTypeEnum();
            if(RObjectsUtils.nonNull(httpTypeEnum)){
                httpURLConnection.setRequestMethod(httpTypeEnum.type);//请求类型
            }
            Map<String,String> headersMap = httpParamDto.getHeaders();
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
