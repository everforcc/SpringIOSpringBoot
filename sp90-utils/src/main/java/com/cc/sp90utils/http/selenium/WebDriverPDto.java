package com.cc.sp90utils.http.selenium;

import com.cc.sp90utils.http.vo.WebSiteDataVO;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Function;

@Slf4j
public class WebDriverPDto {

    private int index;
    private WebDriver webDriver;

    public int getIndex() {
        return index;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    protected WebDriverPDto() {
    }

    /**
     * 请求url获取html等信息
     *
     * @param url 网址
     * @return 请求结果
     */
    public WebSiteDataVO getHtml(String url) {
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

    /**
     * 取出html，上面的那个方法在点击页面后无法获取新的html信息
     *
     * @return html
     */
    public String html() {
        return webDriver.getPageSource();
    }

    public void click(String xPath) {
        WebElement webElement = webDriver.findElement(By.xpath(xPath));
        log.info("点击 webElement: {}", webElement);
        webElement.click();
        WebDriverWait wait = new WebDriverWait(webDriver, 1);
        wait.until((Function<WebDriver, Object>) webDriver -> {
            webDriver.findElement(By.xpath(xPath)).click();
            return webDriver;
        });
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 点击超链接，用文字判断
     *
     * @param linkText 超链接文字
     * @return 请求结果
     */
    public WebSiteDataVO clickLinkText(String linkText) {
        WebSiteDataVO webSiteDataVO = new WebSiteDataVO();
        webDriver.findElement(By.linkText(linkText)).click();
        String html = webDriver.getPageSource();
        String title = webDriver.getTitle();
        webSiteDataVO.setPageSource(html);
        webSiteDataVO.setTitle(title);
//        log.info("title {}",title);
//        log.info("驱动 {},请求的url: {}",index,url);
        return webSiteDataVO;
    }

    /**
     * 测试修改为 public 随后修改回来
     */
    public WebDriverPDto(int index, WebDriver webDriver) {
        this.index = index;
        this.webDriver = webDriver;
        log.info("创建webDriver: 坐标:{} 驱动类型{}", index, webDriver.getClass());
    }

    public void quit() {
        webDriver.quit();
        log.info("关闭webDriver: 坐标:{} ", index);
    }

}
