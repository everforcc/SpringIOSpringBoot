package cn.cc.utils.selenium;

import cn.cc.utils.selenium.selenium.SeleniumPool;
import cn.cc.utils.selenium.vo.WebSiteDataVO;

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
