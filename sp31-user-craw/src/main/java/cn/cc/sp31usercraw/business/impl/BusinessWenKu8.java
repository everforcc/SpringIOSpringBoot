/**
 * Project:TODO ADD PROJECT NAME SpringIOSpringBoot
 *
 * @Description
 * @Author Author Date Description
 * ------ ------ ------
 *    TODO 开发人员邮箱前缀 调整时间 年-月-日 主要改动点>5字符
 * @Date 2022-06-14 21:58
 * Copyright
 */

package cn.cc.sp31usercraw.business.impl;

import cn.cc.sp31usercraw.business.IBusiness;
import cn.cc.sp31usercraw.dto.NovelConfigDto;
import com.cc.sp90utils.http.selenium.WebDriverPDto;
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
