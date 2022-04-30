package com.cc.sp70craw.utils.webdriver.remote;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class RemoteDriver {

    public static void main(String[] args) {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        WebDriver driver = null;
        try {
            driver = new RemoteWebDriver(new URL("http://www.example.com"), firefoxOptions);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver.get("http://www.google.com");
        driver.quit();

    }

}
