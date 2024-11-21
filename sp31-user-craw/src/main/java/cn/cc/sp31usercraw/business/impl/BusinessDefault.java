/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-14 22:48
 * Copyright
 */

package cn.cc.sp31usercraw.business.impl;

import cn.cc.sp31usercraw.business.IBusiness;
import cn.cc.sp31usercraw.dto.NovelConfigDto;
import cn.cc.utils.http.selenium.WebDriverPDto;
import cn.cc.utils.jsoup.XSoupUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 通用的默认实现类
 */
@Slf4j
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
        log.info("rootUrl: [{}]", rootUrl);

        /**
         * 将所有的换行处理为空
         * 最后根据br来换行
         */
        content = content.replace("\n", "")
                .replace("\r\n", "");
        // 删除 标签之间的 空白字符
        //content = content.replaceAll(">(\\s)*?<","");
        content = content.replaceAll(">\\s+<", "><");


        // 换行，处理掉 &nbsp 空格
        content = content.replace("<br><br>", "\n")
                .replace("<br>", "\n")
                .replace("\n\n", "")
                .replace("&nbsp;", " ");
        // 使用非贪婪模式处理调 <div>块
        content = content.replaceAll("</div>", "")
                .replaceAll("<div .*?>", "");

        // 最后处理玩给所有的换行加一行
        content = content.replaceAll("\n", "\n\n");
        return content;
    }
}
