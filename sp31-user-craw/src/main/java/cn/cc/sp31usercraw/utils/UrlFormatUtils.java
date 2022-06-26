/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-13 21:37
 * Copyright
 */

package cn.cc.sp31usercraw.utils;

import com.cc.sp90utils.commons.web.HttpParamUtils;

public class UrlFormatUtils {

    /**
     * 获得链接的地址 http://www.everforcc.com/abc/i.html
     * <p>
     * 1. "http://www.everforcc.com/index.html“ -> "http://www.everforcc.com/index.html“
     * 2. "/index.html" -> "http://www.everforcc.com/index.html“
     * 3. "index.html" -> "http://www.everforcc.com/abc/index.html"
     *
     * @param indexUrl 点击的链接
     * @param url      当前页的链接
     * @return 返回处理后的链接
     */
    public static String formatUrl(String indexUrl, String url) {
        // 网站根地址
        String rootUrl = HttpParamUtils.getRootUrl(url);
        if (indexUrl.startsWith("http")) {
            return indexUrl;
        } else if (indexUrl.startsWith("/")) {
            return HttpParamUtils.getRootUrl(rootUrl) + indexUrl;
        } else {
            return HttpParamUtils.replaceLastPath(url, indexUrl);
        }
    }

    public static void main(String[] args) {
        String currentUrl = "http://www.everforcc.com/abc/i.html";

        String m_1 = "http://www.everforcc.com/abc/iiiii.html";
        System.out.println(formatUrl(m_1, currentUrl));

        String m_2 = "/index.html";
        System.out.println(formatUrl(m_2, currentUrl));

        String m_3 = "index.html";
        System.out.println(formatUrl(m_3, currentUrl));

    }

}
