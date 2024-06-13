package cn.cc.sp31usercraw.busi;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;

/**
 * @Description
 * @Author everforcc
 * @Date 2024-05-09 21:14
 * Copyright
 */
public class biq7 {

    private static final String root = "";
    private static String url = "https://www.biq7.com/34/34155/79.html";
    private static final String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36";


    @Test
    public void flow() {
//        System.setProperty(chromeDriverKey, chromeDriverPath);
        //chromeOptions.setHeadless(true);
        WebDriver driver = phantomJSDriver();
        while (true) {
            driver.get(url);
//        System.out.println(driver.getPageSource());
            WebElement webElement = driver.findElement(By.id("chaptercontent"));
//            System.out.println(webElement.getText());
            down(webElement.getText());
            String href = driver.findElement(By.id("pb_next")).getAttribute("href");
            if (href.startsWith("java")) {
                System.out.println(href);
                System.out.println("下载结束");
                break;
            }
            // "https://www.biq7.com" +
            url = href;
            System.out.println("当前下载地址: " + url);
        }
    }

    @Test
    public void flow2() {
        String url2 = "https://www.xywx.cc/aikan/5064643_148182687.html0";
//        System.setProperty(chromeDriverKey, chromeDriverPath);
        //chromeOptions.setHeadless(true);
        WebDriver driver = phantomJSDriver();
        while (true) {
            driver.get(url2);
//        System.out.println(driver.getPageSource());
            WebElement webElement = driver.findElement(By.id("chaptercontent"));
//            System.out.println(webElement.getText());
            down(webElement.getText());
            String href = driver.findElement(By.id("pb_next")).getAttribute("href");
            if (href.startsWith("java")) {
                System.out.println(href);
                System.out.println("下载结束");
                break;
            }
            // "https://www.biq7.com" +
            url = href;
            System.out.println("当前下载地址: " + url);
        }
    }

    public static File file = new File("D:\\java\\temp\\3.txt");

    public static void down(String content) {
        try {
            System.out.println("下载一章");
            FileUtils.write(file, content, "UTF-8", true);
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WebDriver phantomJSDriver() {
        final String chromeDriverKey = "webdriver.chrome.driver";
        final String chromeDriverPath = "D:\\java\\environment\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe";
        // 设置到环境变量里面
//        String phantomjsPath = "D:/java/environment/driver/phantomjs.exe";
//        String phantomjsLogPath = "D:/java/code/github/OneForAll/default_log_home/phantomjsdriver.log";
        String[] phantomArgs = {"--webdriver-loglevel=INFO"};

        DesiredCapabilities dcap = new DesiredCapabilities();

//        File logfile = new File(phantomjsLogPath);

        PhantomJSDriverService pjsds = new PhantomJSDriverService.Builder()
                .usingPhantomJSExecutable(new File(chromeDriverPath))
                .usingAnyFreePort()
                //.withProxy(proxy)
                .usingCommandLineArguments(phantomArgs)
//                .withLogFile(logfile)
                .build();

        return new PhantomJSDriver(pjsds, dcap);
    }

}
