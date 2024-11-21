package cn.cc.utils.http.impl;


import cn.cc.utils.http.selenium.SeleniumPool;
import cn.cc.utils.http.selenium.WebDriverPDto;
import cn.cc.utils.http.ISelenium;
import cn.cc.utils.http.vo.WebSiteDataVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SeleniumChrome implements ISelenium {

    @Override
    public WebSiteDataVO getHTML(String url, SeleniumPool seleniumPool) {
        WebDriverPDto webDriverPDto = seleniumPool.getDriverDto();
        WebSiteDataVO webSiteDataVO = webDriverPDto.getHtml(url);
        seleniumPool.close(webDriverPDto);
        return webSiteDataVO;
    }

    @Override
    public WebSiteDataVO getHTML(String url) {
        SeleniumPool seleniumPool  = SeleniumPool.getInstantce();
        WebDriverPDto webDriverPDto = seleniumPool.getDriverDto();
        WebSiteDataVO webSiteDataVO = webDriverPDto.getHtml(url);
        seleniumPool.close(webDriverPDto);
        return webSiteDataVO;
    }

}
