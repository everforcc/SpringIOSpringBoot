package com.cc.sp90utils.constant;


import com.cc.sp90utils.enums.ContentTypeEnum;
import org.apache.http.HttpHeaders;

public class HttpHeadersConstant {

    /**
     * 两个工具类合并到这边方便后续替换
     *       org.apache.http.HttpHeaders
     * com.google.common.net.HttpHeaders
     */

    public static String AUTH_TOKEN = "token";

    public static String REQUEST_GET = "GET";

    public static String REQUEST_POST = "POST";

    /**
     * apache的工具包常用的请求头
     */
    public static String apacheHTTPHeaders = HttpHeaders.ACCEPT;
    /**
     * google的工具包常用的请求头
     */
    public static String googleHTTPHeaders = com.google.common.net.HttpHeaders.ACCEPT;

    public static final String image_jpeg = "image/jpeg";

    public static final String txt_plain = ContentTypeEnum.txt.type;

    public static final String txt_plain_UTF_8 = ContentTypeEnum.txt.utf8();

    public static final String txt_html = ContentTypeEnum.html.type;

    public static final String txt_html_UTF_8 = ContentTypeEnum.html_UTF_8.type;

    public static String max_age= "max-age=";

    /**
     * 内容类型
     */
    public static String Content_Type = HttpHeaders.CONTENT_TYPE;


    /**
     * 下载文件用
     */
    public static String CONTENT_DISPOSITION = com.google.common.net.HttpHeaders.CONTENT_DISPOSITION;

    /**
     * 内容大小
     */
    public static String CONTENT_LENGTH = HttpHeaders.CONTENT_LENGTH;

    /**
     * 缓存
     */
    public static String CACHE_CONTROL = HttpHeaders.CACHE_CONTROL;
    public static String PRAGMA = HttpHeaders.PRAGMA;

    /**
     * 过期时间
     */
    public static String LAST_MODIFIED = HttpHeaders.LAST_MODIFIED;
    public static String EXPIRES = HttpHeaders.EXPIRES;

    public static String MDC_header = "X-B3-TraceId";

}
