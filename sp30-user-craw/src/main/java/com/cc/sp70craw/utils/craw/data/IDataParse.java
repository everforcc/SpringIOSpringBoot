package com.cc.sp70craw.utils.craw.data;

import java.util.List;

public interface IDataParse {

    /**
     * 用户自定义处理
     */

    /**
     * 将返回的数据，处理为需要的数据
     */
    String parseJsonStr(String json,String pattern);

    String parseHTMLStr(String html,String pattern);

    List<String> parseJsonList(String json, String pattern);

    List<String> parseHTMLList(String html,String pattern);

}
