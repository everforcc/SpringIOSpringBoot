/**
 * @Description
 * @Author everforcc
 * @Date 2022-08-08 22:47
 * Copyright
 */

package cn.cc.sp31usercraw.game;

import com.cc.sp90utils.http.selenium.SeleniumPool;
import com.cc.sp90utils.http.selenium.WebDriverPDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://xxtyme.github.io/EatCxk/">...</a>
 */
@Slf4j
@RequestMapping("/game")
@RestController
public class XXTGameController {

    /**
     * 模拟点击
     */
    @Resource
    SeleniumPool seleniumPool;


    @SneakyThrows
    @GetMapping("/size")
    public void size() {
        // 1. 打开浏览器
        WebDriverPDto webDriverPDto = seleniumPool.getDriverDto();
        WebDriver webDriver = webDriverPDto.getWebDriver();
        webDriver.get("https://xxtyme.github.io/EatCxk/");
        // 2. 点击开始游戏
        webDriver.findElement(By.xpath("//*[@id=\"btn_group\"]/a[1]")).click();
        // 3.
        // //*[@id="GameLayer1-0"]
        for (int i = 0; i < Integer.MAX_VALUE; i++) {

            // 网页结构是 40个一组
            int pre = i / 40 + 1;
            int ind = i % 40;

            Map<String, WebElement> mapElement = new HashMap<>();
            Map<String, Boolean> mapFlag = new HashMap<>();

            WebElement webElement_1 = webDriver.findElement(By.xpath("//*[@id=\"GameLayer" + pre + "-" + ind + "\"]"));
            String classVal_1 = webElement_1.getAttribute("class");
            log.info(classVal_1);
            put(classVal_1, webElement_1, mapElement, mapFlag);

            i++;
            pre = i / 40 + 1;
            ind = i % 40;

            WebElement webElement_2 = webDriver.findElement(By.xpath("//*[@id=\"GameLayer" + pre + "-" + ind + "\"]"));
            String classVal_2 = webElement_2.getAttribute("class");
            log.info(classVal_2);
            put(classVal_2, webElement_2, mapElement, mapFlag);

            i++;
            pre = i / 40 + 1;
            ind = i % 40;

            WebElement webElement_3 = webDriver.findElement(By.xpath("//*[@id=\"GameLayer" + pre + "-" + ind + "\"]"));
            String classVal_3 = webElement_3.getAttribute("class");
            log.info(classVal_3);
            put(classVal_3, webElement_3, mapElement, mapFlag);

            i++;
            pre = i / 40 + 1;
            ind = i % 40;

            WebElement webElement_4 = webDriver.findElement(By.xpath("//*[@id=\"GameLayer" + pre + "-" + ind + "\"]"));
            String classVal_4 = webElement_4.getAttribute("class");
            log.info(classVal_4);
            put(classVal_4, webElement_4, mapElement, mapFlag);

            for (Map.Entry<String, Boolean> entry : mapFlag.entrySet()) {
                log.info("准备点击: {}", entry.getValue());
                if (entry.getValue()) {
                    String key = entry.getKey();
                    WebElement markWebElement = mapElement.get(key);
                    // 点击这个界面
                    markWebElement.click();
                    log.info("已点击");
                    continue;
                }
            }
            Thread.sleep(500);
        }
    }

    /**
     * 如果
     */
    public static void put(String classVal_1, WebElement webElement, Map<String, WebElement> mapElement, Map<String, Boolean> mapFlag) {
        if (mapFlag.containsKey(classVal_1)) {
            mapFlag.put(classVal_1, false);
        } else if ("block".equals(classVal_1)) {
            mapFlag.put(classVal_1, false);
        } else {
            mapFlag.put(classVal_1, true);
        }
        mapElement.put(classVal_1, webElement);
    }

}
