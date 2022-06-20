/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-14 22:48
 * Copyright
 */

package cn.cc.sp31usercraw.business.impl;

import cn.cc.sp31usercraw.business.IBusiness;
import cn.cc.sp31usercraw.dto.NovelConfigDto;
import com.cc.sp90utils.http.selenium.WebDriverPDto;
import com.cc.sp90utils.jsoup.XSoupUtils;
import org.springframework.stereotype.Service;

/**
 * 通用的默认实现类
 */
@Service("businessDefault")
public class BusinessDefault implements IBusiness {

    @Override
    public String endCondition(String pageSource, NovelConfigDto novelConfigDto, WebDriverPDto webDriverPDto, String linkText) {
        // 取出拿数据的正则
        String novelContentXR = novelConfigDto.getNovelContentXR();
        // 正则取出数据
        return XSoupUtils.htmlToStr(pageSource, novelContentXR);
    }

    @Override
    public String dealContent(String content, String rootUrl) {
        return content;
    }
}
