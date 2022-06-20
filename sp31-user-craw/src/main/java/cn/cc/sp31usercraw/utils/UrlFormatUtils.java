/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-13 21:37
 * Copyright
 */

package cn.cc.sp31usercraw.utils;

import com.cc.sp90utils.commons.web.HttpParamUtils;

public class UrlFormatUtils {

    public static String formatUrl(String urlStr, String indexMenuUrl){
        if(urlStr.startsWith("http")){
            return urlStr;
        }else if(urlStr.startsWith("/")){
            return HttpParamUtils.getRootUrl(indexMenuUrl) + urlStr;
        }else {
            return HttpParamUtils.replaceLastPath(indexMenuUrl, urlStr);
        }
    }

}
