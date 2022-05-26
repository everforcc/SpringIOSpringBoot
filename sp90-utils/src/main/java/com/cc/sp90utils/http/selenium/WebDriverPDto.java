package com.cc.sp90utils.http.selenium;

import com.cc.sp90utils.http.vo.WebSiteDataVO;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

@Slf4j
public class WebDriverPDto {

    private int index;
    private WebDriver webDriver;

    public int getIndex() {
        return index;
    }

    /*public WebDriver getWebDriver() {
        return webDriver;
    }*/

    protected WebDriverPDto() {
    }

    public WebSiteDataVO getHtml(String url){
        WebSiteDataVO webSiteDataVO = new WebSiteDataVO();
        webDriver.get(url);
        String html = webDriver.getPageSource();
        String title = webDriver.getTitle();
        webSiteDataVO.setPageSource(html);
        webSiteDataVO.setTitle(title);
//        log.info("title {}",title);
//        log.info("驱动 {},请求的url: {}",index,url);
        return webSiteDataVO;
    }

    protected WebDriverPDto(int index, WebDriver webDriver) {
        this.index = index;
        this.webDriver = webDriver;
        log.info("创建webDriver: 坐标:{} 驱动类型{}",index,webDriver.getClass());
    }



}
