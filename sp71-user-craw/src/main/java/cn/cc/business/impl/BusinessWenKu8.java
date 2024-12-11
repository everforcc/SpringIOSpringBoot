/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-14 21:58
 * Copyright
 */

package cn.cc.business.impl;

import cn.cc.business.IBusiness;
import cn.cc.dto.NovelConfigDto;
import cn.cc.utils.selenium.selenium.WebDriverPDto;
import org.springframework.stereotype.Service;

/**
 * wenku8的实现类
 * https://www.wenku8.net/novel/1/1973/75974.htm
 */
@Service("businessWenKu")
public class BusinessWenKu8 implements IBusiness {

    @Override
    public String endCondition(String pageSource, NovelConfigDto novelConfigDto, WebDriverPDto webDriverPDto, String linkText) {
        return null;
    }

    @Override
    public String dealContent(String content, String rootUrl) {
        return null;
    }
}
