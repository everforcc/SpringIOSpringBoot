/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-13 23:20
 * Copyright
 */

package cn.cc.utils.commons.web;

import cn.cc.utils.constant.HttpUserAgent;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HttpURLConnection 常用的方法，
 * 还没归类
 */
public class JHttpUrlConnectUtils {

    /**
     * 通过HttpURLConnection和urlconnection获取流
     *
     * @param url  请求地址
     * @param type 请求方法
     * @return 返回流
     */
    public static InputStream getInputStream(String url, String type) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestMethod(type);
            httpURLConnection.setRequestProperty(HttpUserAgent.User_Agent, HttpUserAgent.WIN_Chrome_102);
            httpURLConnection.setDoInput(true);
            return httpURLConnection.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
