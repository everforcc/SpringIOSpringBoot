/**
 * @Description
 * @Author everforcc
 * @Date 2023-06-01 18:00
 * Copyright
 */

package cn.cc.sp31usercraw.flow;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class NovelCommonFlowServiceTest {

    @Before
    public void config() {
        System.setProperty("webdriver.chrome.driver", "C:/everforcc/java/environment\\driver\\chromedriver.exe");
    }

    @Test
    public void clickk() {
        String url = "https://m.kxwx.cc/book/242833/";
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriver webDriver = new ChromeDriver(chromeOptions);
        webDriver.get(url);
        WebElement webElement = webDriver.findElement(By.xpath("//div[@class='paging']//li[4]"));
        System.out.println("webElement: {}" + webElement);
        webElement.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("点击后---");
        System.out.println(webDriver.getPageSource());
    }

    @Test
    public void content() {
        String content = "<div class=\"chapterinfo\" id=\"chapterinfo\">       2022年06月14日  " + "a123" + " </div>  <img src=\"/data/0990372354.png\">\n" + " <img src=\"/data/7808420336.png\">风";
        content = content.replaceAll(">\\s+<", "><");
//        content = content.replaceAll("</div>","")
//                .replaceAll("<div .*?>","");

        System.out.println(content);
    }

}



