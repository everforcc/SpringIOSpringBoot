package com.cc.sp90utils.http.impl;


import com.cc.sp90utils.http.ISelenium;
import com.cc.sp90utils.http.selenium.SeleniumPool;
import com.cc.sp90utils.http.selenium.WebDriverPDto;
import com.cc.sp90utils.http.vo.WebSiteDataVO;
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
