/**
 * @Description
 * @Author everforcc
 * @Date 2023-06-01 18:06
 * Copyright
 */

package cn.cc.sp31usercraw.utils;

import org.junit.Test;

public class UrlFormatUtilsTest {

    @Test
    public void tFormatUrl() {
        String currentUrl = "http://www.everforcc.com/abc/i.html";

        String m_1 = "http://www.everforcc.com/abc/iiiii.html";
        System.out.println(UrlFormatUtils.formatUrl(m_1, currentUrl));

        String m_2 = "/index.html";
        System.out.println(UrlFormatUtils.formatUrl(m_2, currentUrl));

        String m_3 = "index.html";
        System.out.println(UrlFormatUtils.formatUrl(m_3, currentUrl));

    }

}
