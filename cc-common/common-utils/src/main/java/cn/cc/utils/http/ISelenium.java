package cn.cc.utils.http;

import cn.cc.utils.http.selenium.SeleniumPool;
import cn.cc.utils.http.vo.WebSiteDataVO;

public interface ISelenium {

    /**
     * 并发下使用，需要先传入连接池
     * @param url
     * @param seleniumPool
     * @return
     */
    WebSiteDataVO getHTML(String url, SeleniumPool seleniumPool);

    /**
     * 单线程下使用，有默认的连接池
     * @param url
     * @return
     */
    WebSiteDataVO getHTML(String url);

}
