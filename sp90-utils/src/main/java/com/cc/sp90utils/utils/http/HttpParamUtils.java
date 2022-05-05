package com.cc.sp90utils.utils.http;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HttpParamUtils {


    /**
     * google的工具包
     * @param source
     * @return
     */
    public static String asUrlParamsGuava(Map<String, String> source){
        // TODO 如果要编码的话自己加下编码逻辑
        return Joiner.on("&")
                // 用指定符号代替空值,key 或者value 为null都会被替换
                .useForNull(" ")
                .withKeyValueSeparator("=")
                .join(source);
    }

    /**
     * 只要确保你的编码输入是正确的,就可以忽略掉 UnsupportedEncodingException
     */
    public static String asUrlParams(Map<String, String> source){
        Iterator<String> it = source.keySet().iterator();
        StringBuilder paramStr = new StringBuilder();
        while (it.hasNext()){
            String key = it.next();
            String value = source.get(key);
            if (StringUtils.isBlank(value)){
                continue;
            }
            try {
                // URL 编码
                value = URLEncoder.encode(value, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // do nothing
            }
            paramStr.append("&").append(key).append("=").append(value);
        }
        // 去掉第一个&
        return paramStr.substring(1);
    }

    public static String getRootUrl(String weburl){
        URL url = null;
        try {
            url = new URL(weburl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url.getProtocol() + "://" + url.getHost();
    }

    public static String getRootPathUrl(String weburl){
        URL url = null;
        try {
            url = new URL(weburl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url.getProtocol() + "://" + url.getHost() + url.getPath();
    }

    public static Map<String,String> getUrlQueryMap(String weburl){
        Map<String,String> map = new HashMap<>();
        URL url = null;
        try {
            url = new URL(weburl);
            String params = url.getQuery();
            if(StringUtils.isBlank(params)){
                return map;
            }

            /**
             * 用 & 拆分
             */
            String[] paramsAry =  params.split("&");

            for(String str: paramsAry){
                /**
                 * 每个参数按 = 拆分
                 */
                String[] paramAry = str.split("=");
                if (1 == paramAry.length){
                    map.put(paramAry[0]," ");
                }else if(2 == paramAry.length){
                    map.put(paramAry[0],paramAry[1]);
                }else {
                    throw new RuntimeException("参数处理异常");
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static String updateUrlQueryMap(String webUrl,Map<String,String> updateMap){
        Map<String,String> queryMap = getUrlQueryMap(webUrl);
        for(Map.Entry entry:updateMap.entrySet()){
            if (!queryMap.containsKey(entry.getKey())){
                throw new RuntimeException("修改参数异常");
            }
            queryMap.put((String)entry.getKey(),(String)entry.getValue());
        }
        return urlWithOutParams(webUrl) + "?" + asUrlParamsGuava(queryMap);
    }

    public static String addOrUpdateUrlQueryMap(String webUrl,Map<String,String> updateMap){
        Map<String,String> queryMap = getUrlQueryMap(webUrl);
        for(Map.Entry entry:updateMap.entrySet()){
            queryMap.put((String)entry.getKey(),(String)entry.getValue());
        }
        return urlWithOutParams(webUrl) + "?" + asUrlParamsGuava(queryMap);
    }

    public static String getUrlQueryValue(String webUrl,String key){
        if(StringUtils.isEmpty(key)){
            throw new RuntimeException("查询参数不能为空");
        }
        Map<String,String> queryMap = getUrlQueryMap(webUrl);
        return queryMap.get(key);
    }

    public static String urlWithOutParams(String webUrl){
        URL url = null;
        try {
            url = new URL(webUrl);
            return url.getProtocol() + "://" + url.getHost() + url.getPath();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static boolean checkUrlEffect(String weburl){
        URL url = null;
        try {
            url = new URL(weburl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);

        }
        return true;
    }

    /**
     * www.baidu.com/1/b/c.html a.html
     * www.baidu.com/1/b/a.html
     * @param url
     * @param path
     * @return
     */
    public static String replaceLastPath(String url,String path){
        if(StringUtils.isBlank(url)||StringUtils.isBlank(path)){
            throw new RuntimeException("数据有误");
        }
        return url.substring(0,url.lastIndexOf("/") + 1) + path;
    }

    /**
     * www.baidu.com/1/b/c.html c.html
     * @param url
     * @return
     */
    public static String getLastPath(String url){
        if(StringUtils.isBlank(url)){
            throw new RuntimeException("数据有误");
        }
        return url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * www.baidu.com/1/b/c.html
     * 0 c.html
     * 1 b
     * 2 1
     * @param tagUrl
     * @param index
     * @return
     */
    public static String getLastPath(String tagUrl,int index){
        if(StringUtils.isBlank(tagUrl)){
            throw new RuntimeException("数据有误");
        }

        URL url = null;
        try {
            url = new URL(tagUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String urlPath = url.getPath();
        for(int i=0; i<index; i++) {
            urlPath = urlPath.substring(0, urlPath.lastIndexOf("/"));
        }
        urlPath = urlPath.substring(urlPath.lastIndexOf("/") + 1);

        return urlPath;
    }

    public static void main(String[] args) {


            String weburl = "https://webstatic.mihoyo.com/hk4e/event/e20190909gacha/index.html?authkey_ver=1&sign_type=2&auth_appid=webview_gacha&init_type=301&gacha_id=50eef7be3b7945930041f9f20310e0bbcb8a7a&timestamp=1648597804&lang=zh-cn&device_type=mobile&ext=%7b%22loc%22%3a%7b%22x%22%3a-3540.392333984375%2c%22y%22%3a237.4633026123047%2c%22z%22%3a-3238.4052734375%7d%2c%22platform%22%3a%22Android%22%7d&game_version=CNRELAndroid2.6.0_R6461336_S6575074_D6551263&plat_type=android&region=cn_gf01&authkey=rgoooaaX4F%2fkJQUC89LEP8SuvXY%2bNnwdVIcfuzCas%2b29vOo5YYAF8ZqRQ0WR7AsF2t3ZcJXVI5xFmSLvYUhkHKnDXKJldNunc9botakvWtLJpRF6bK%2b58jv3nzZsXZfFPE3ciJXtx9ynfr%2fWrGKXGBpK2UXRXq1MGz7P0MYciN0vN3qa%2fkjhIWNqeuv7X4BxvrFjPxzeaoGPdp2nFonHEjZe3RedkBjIQOD%2fHaPuYGYpRIbW%2fYKZUEVj7hf8gZIQGwH%2b%2fy7bfaZDjC904Fq%2fggacCXdIdQLIEueC7ygH8lwBHO9iQe7NlI4rkwLRtJErrm4bvYLHMJvY4Nrz7Y4f3Y1SErVh9DJyA0frmSGMU1iejGKo%2bsJn0cbnxvyPGmngpaKa4Vmdg4TBzcjWTLEDswfR5gJQ1N5UMJTg74Ku3EUZIFKfeKde2BWbaoDhhA7tAXSImXnkfqge4KKb%2bG7Baihl3ZklvvqCFJMKUTfczWNJFnNEBApasA3HdkRbnAL0WutdR5lJviF7R1WlhWd1iL8W7TSENysXlVwcu0OW4%2bv4NAToa9ocuyl5ZQswH7%2fuOSTw9fEodwiFKOPF%2f54T%2ftCAZYJze3zvoosqDfuFC870Q67q2XvR00CqSJBh13qt9PAxbss4Q8RyMnm2%2bcpwWYeucY0o9nyK9YJUNbew0Hfui7j7lYlpwYamzXd0cMQL2xSM4R0Vu%2fZThBr0dUVYG8wAuDZ1rfvCgtxF0Kv%2fxykSO98Hf%2bR5dbE42r5LuuaJfxq1zkCyDe98FAjd4MVRUE2n4AIEVU1Lyw%2bviezgqHCwqmbis4kDj2lJ93jQhj20kZJnG7kvFPw2gm52Qz%2biUYhY3ABc1OPK5zV%2fG9RJtcgjvhYzzGa1IViQlfzmsDls8MjIWY%2fzVofvM6iDswLHueu1yI7YrbqmEwamOTio%2fZ%2fb6hfiScFC3cqEFXwkhl2eK8aQ0U8JYjuaQbSTkMwauhWVRD4xdHX7ljCd3mDrdASnZkpg%2ftLM%2fbjGjNgnym%2f9SzOduI%2fffU12Gcxf33Fh3HBhj7wBBIs9EJtCY8P5HeJJXMKlEOoY%2bRkDt7qgIVgq3gXmEvyXNzJdMfiLhSfXRTqvmfBINVVz%2fNPJt6rs69yBdKQJon4kJyNOXRBMNtz6MXhPjz68lYDtHMyeupvtDWmyy029Q47sJSrB5ZqVa4M%2bdwz%2b8ZX5dTwW4Bt%2bQQvpwrGI9WnHMgqE135s5kbz4quSoUFlRYPcM%2bbM2uIk5EgbWtSVBFJfB%2fSXMfoyjtmJrq1UyU%2bygMeCRskSLCmaSFN1HACSDJ8zfWNzuUunldArVWx%2bF%2bioWfaPchjQVKibaa%2bganNh0IcTCBRnoQdIvA%3d%3d&game_biz=hk4e_cn#/log";

            try {
//                URL url = new URL(weburl);
//                System.out.println(url.getPath());
//                System.out.println(url.getRef());
//                System.out.println(url.getQuery());
//                System.out.println(url.getProtocol());
//                System.out.println(url.getPort());
//                System.out.println("://");
//                System.out.println(url.getHost());
//
//                Map<String,String> map = getUrlQueryMap(weburl);
//
//                map.put("authkey_ver","authkey_ver");
//
//                String updateUrl = updateUrlQueryMap(weburl,map);
//
//                System.out.println(weburl);
//                System.out.println(updateUrl);
                //  map.forEach((k,v) -> System.out.println("k: " + k + " \r\nv: " + v));
                //  map.forEach((k,v) -> System.out.println(k + " :" + v));

                String a = "www.baidu.com/1/b/c.html";
                String b = "a.html";
                System.out.println(getLastPath(a));

            } catch (Exception e) {
                e.printStackTrace();
            }


    }

}
