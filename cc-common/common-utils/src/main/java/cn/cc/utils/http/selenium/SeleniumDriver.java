package cn.cc.utils.http.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class SeleniumDriver {

    public static WebDriver chromeDriver(){
        ChromeOptions chromeOptions = new ChromeOptions();
        //chromeOptions.setHeadless(true);
        WebDriver driver = new ChromeDriver(chromeOptions);
        return driver;
    }

    public static WebDriver phantomJSDriver(){
        // 设置到环境变量里面
        String phantomjsPath = "D:/java/environment/driver/phantomjs.exe";
        String phantomjsLogPath = "D:/java/code/github/OneForAll/default_log_home/phantomjsdriver.log";
        String[] phantomArgs = { "--webdriver-loglevel=INFO" };

        DesiredCapabilities dcap = new DesiredCapabilities();

        File logfile = new File(phantomjsLogPath);

        PhantomJSDriverService pjsds = new PhantomJSDriverService.Builder()
                .usingPhantomJSExecutable(new File(phantomjsPath))
                .usingAnyFreePort()
                //.withProxy(proxy)
                .usingCommandLineArguments(phantomArgs)
                .withLogFile(logfile)
                .build();

        return new PhantomJSDriver(pjsds, dcap);
    }

}
