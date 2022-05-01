package com.cc.sp70craw.utils.craw.http.impl;

import com.cc.sp70craw.utils.craw.http.IHttp;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.logging.Level;

public class SeleniumIHttp implements IHttp {

    @Override
    public String requestUrl(String url) {

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        options.addArguments("headless");
        //禁用沙盒nage
        options.addArguments("no-sandbox");
        options.addArguments("disable-gpu");
        cap.setCapability(ChromeOptions.CAPABILITY, options);

        WebDriver driver = new ChromeDriver();
        driver.get(url);
        String html = driver.getPageSource();
        driver.close();
        return html;
    }

    public static void psj(){

        String phantomjsPath = "D:/java/environment/driver/phantomjs.exe";
        String phantomjsLog = "--logfile=D:/java/code/github/SpringIOSpringBoot/log_home/phantomjsdriver.log";
        String phantomjsLogPath = "D:/java/code/github/SpringIOSpringBoot/log_home/phantomjsdriver.log";
        String phantomjsLogLevel = "--webdriver-loglevel=DEBUG";
        // DEBUG INFO
        String[] phantomArgs = { "--webdriver-loglevel=INFO" };
//        Proxy proxy = new Proxy();
//        proxy.setHttpProxy("120.0.0.1:41091");

//        DesiredCapabilities caps = new DesiredCapabilities();
//        caps.setJavascriptEnabled(true);
//        caps.setCapability("takesScreenshot", false);
//        caps.setCapability(
//                PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS,
//                new String[] { "--logfile=D:/java/code/github/SpringIOSpringBoot/log_home/phantomjsdriver.log" });
        //caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,path);
        //   此处是加载phantomjs.exe配置，放在那里就写哪里的路径
        //System.setProperty("phantomjs.binary.path",phantomjsPath);

        DesiredCapabilities dcap = new DesiredCapabilities();
        File logfile = new File(phantomjsLogPath);

        PhantomJSDriverService pjsds = new PhantomJSDriverService.Builder()
                .usingPhantomJSExecutable(new File(phantomjsPath))
                .usingAnyFreePort()
                //.withProxy(proxy)
                .usingCommandLineArguments(phantomArgs)
                .withLogFile(logfile)
                .build();

        WebDriver driver = new PhantomJSDriver(pjsds, dcap);
        driver.get("http://www.baidu.com");
        driver.findElement(By.id("kw")).sendKeys("phantomJS");
        try {
            //Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //   获取title
        String title = driver.getTitle();
        System.out.println(title);
        System.out.println(driver.getPageSource());
        driver.quit();
    }

    public static void main(String[] args) {
        psj();
    }

}
