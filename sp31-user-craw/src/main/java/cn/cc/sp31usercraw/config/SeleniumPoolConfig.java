/**
 * Project:TODO ADD PROJECT NAME SpringIOSpringBoot
 *
 * @Description
 * @Author Author Date Description
 * ------ ------ ------
 *    TODO 开发人员邮箱前缀 调整时间 年-月-日 主要改动点>5字符
 * @Date 2022-06-06 11:54
 * Copyright
 */

package cn.cc.sp31usercraw.config;

import com.cc.sp90utils.http.selenium.SeleniumPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * webdriver驱动
 */
@Slf4j
@Component
public class SeleniumPoolConfig {

    /**
     * 初始化驱动
     * @return bean
     */
    @Bean
    public SeleniumPool getSeleniumPool(){
        log.info("初始化SeleniumPool...");
        SeleniumPool seleniumPool = SeleniumPool.getInstantce();
        log.info("已完成初始化SeleniumPool...");
        return seleniumPool;
    }

    /**
     * 关闭spring前关闭驱动池
     */
    @PreDestroy
    public void destroySeleniumPool(){
        log.info("清空SeleniumPool...");
        SeleniumPool.getInstantce().destory();
        log.info("已经清空SeleniumPool...");
    }

}
