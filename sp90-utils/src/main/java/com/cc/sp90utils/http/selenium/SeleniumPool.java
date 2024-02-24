package com.cc.sp90utils.http.selenium;

import com.cc.sp90utils.commons.lang.RObjectsUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class SeleniumPool {

    /* spring中使用 ，需要将
        getInstantce
        destory
        注册到bean，在初始化的时候初始化，在结束前，关闭驱动
     */

    private static Object lock = new Object();
    private static List<WebDriverPDto> activityDriver = new ArrayList<>();
    private static List<WebDriverPDto> leaveDriver = new ArrayList<>();

    private static SeleniumPool seleniumPool;
    /**
     * 默认创建数量
     */
    private static final int defaultSize = 3;
    /**
     * 现有数量
     */
    private int totalSize = 0;

    private SeleniumPool() {
    }

    private SeleniumPool(int size) {
        // 在这里创建 给定次数的池子
        if(RObjectsUtils.isNull(size)){
            size = defaultSize;
        }
        while (size > totalSize){
            leaveDriver.add(create());
        }
    }

    private WebDriverPDto create(){
        return new WebDriverPDto(totalSize++,SeleniumDriver.chromeDriver());
    }

    public static SeleniumPool getInstantce(){
        synchronized (lock){
            if (seleniumPool == null){
                seleniumPool = new SeleniumPool(defaultSize);
            }
            return seleniumPool;
        }
    }
    public static SeleniumPool getInstantce(int size){
        synchronized (lock) {
            if (seleniumPool == null) {
                seleniumPool = new SeleniumPool(size);
            }
            return seleniumPool;
        }
    }

    public WebDriverPDto getDriverDto(){
        return getDriverDto(0);
    }

    @SneakyThrows
    public WebDriverPDto getDriverDto(int index){
        synchronized (lock) {
            // 如果大于0就会拿到对象返回
            // 如果不大于就释放锁，重新获取
            if(leaveDriver.size() > 0){
                // 如果方法全包裹，sleep会影响性能，可能同时进外层的大于，所以这里再判断一次
                // if(leaveDriver.size() == 0){ getDriverDto(index); }
                WebDriverPDto webDriverPDto = leaveDriver.remove(0);
                activityDriver.add(webDriverPDto);
                return webDriverPDto;
            }
        }

        index++;
        // 设置上限不能一直获取阻塞
        log.info("连接池用尽，第{}次重新获取...",index);
        nowMsg();
        Thread.sleep(5000);
        return getDriverDto(index);

    }

    public static void nowMsg(){
        String leavePool = Arrays.toString(leaveDriver.stream().mapToInt(WebDriverPDto::getIndex).toArray());
        String activityPool = Arrays.toString(activityDriver.stream().mapToInt(WebDriverPDto::getIndex).toArray());
        log.info("可用池[{}],不可用池[{}]",leavePool,activityPool);
    }

    public void close(WebDriverPDto webDriverPDto){
        synchronized (lock){
            activityDriver.remove(webDriverPDto);
            leaveDriver.add(webDriverPDto);
            log.info("链接 {} 已释放",webDriverPDto.getIndex());
        }
    }

    public void closeAll(){
        synchronized (lock){
            while (activityDriver.size() > 0){
                WebDriverPDto webDriverPDto = activityDriver.remove(0);
                leaveDriver.add(webDriverPDto);
            }
        }
    }

    /* 清空所有的驱动 */
    public void destory(){
        leaveDriver.forEach(WebDriverPDto::quit);
        activityDriver.forEach(WebDriverPDto::quit);
    }

    private class SeleniumPoolCheckP extends Thread{

        private SeleniumPool seleniumPool;
        public SeleniumPoolCheckP(SeleniumPool seleniumPool) {
            this.seleniumPool = seleniumPool;
        }

        /**
         * 检测活动数,
         * 待机数
         *
         * 设置校验,主类在使用的时候，每个方法都做个代理，每次使用都设置时间标志
         * 在这里校验如果超过1min未使用就标记为不活跃，再过一分钟就断开
         *
         */
        @Override
        public void run() {
            super.run();
        }
    }

}
