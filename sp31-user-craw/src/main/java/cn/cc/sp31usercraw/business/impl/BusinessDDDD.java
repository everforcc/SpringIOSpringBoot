/**
 * Project:TODO ADD PROJECT NAME SpringIOSpringBoot
 *
 * @Description
 * @Author Author Date Description
 * ------ ------ ------
 *    TODO 开发人员邮箱前缀 调整时间 年-月-日 主要改动点>5字符
 * @Date 2022-06-14 22:49
 * Copyright
 */

package cn.cc.sp31usercraw.business.impl;

import cn.cc.sp31usercraw.business.IBusiness;
import cn.cc.sp31usercraw.dto.NovelConfigDto;
import cn.cc.sp31usercraw.utils.CharsetOCR;
import com.cc.sp90utils.commons.lang.RObjectsUtils;
import com.cc.sp90utils.commons.lang.RStringUtils;
import com.cc.sp90utils.http.selenium.WebDriverPDto;
import com.cc.sp90utils.http.vo.WebSiteDataVO;
import com.cc.sp90utils.jsoup.XSoupUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 */
@Slf4j
@Service("businessDDDD")
public class BusinessDDDD implements IBusiness {

    @Override
    public String endCondition(String pageSource, NovelConfigDto novelConfigDto, WebDriverPDto webDriverPDto, String linkText) {
        // 页面内容
        String content = null;
        do {
            // 因为有不满足的条件，所以要在这里重新请求
            WebSiteDataVO clickLinkTextData = webDriverPDto.clickLinkText(linkText);
            pageSource = clickLinkTextData.getPageSource();

            // 如果一次就能取出数据
            String novelContentXR = novelConfigDto.getNovelContentXR();
            // 需要多次判断才能取出
            List<String> novelContentXRFlowList = novelConfigDto.getNovelContentXRFlowList();
            // 如果不为空就直接取出
            if (RStringUtils.isNotEmpty(novelContentXR)) {
                content = XSoupUtils.htmlToStr(pageSource, novelContentXR);
            } else if (RObjectsUtils.nonNull(novelContentXRFlowList)) {
                // 如果为空就多次判断
                for (String regex : novelContentXRFlowList) {
                    content = XSoupUtils.htmlToStr(pageSource, regex);
                    if (RStringUtils.isNotEmpty(content)) {
                        break;
                    }
                }
            } else {
                // 如果都没有就报错
                throw new RuntimeException("必须配置小说内容正则");
            }
            // 如果含有notice则说明报错了，这个判断的还不严谨
        } while (content.contains("Notice"));
        return content;
    }

    private static String getContentByRegex(String novelContentXR, String pageSource, List<String> novelContentXRFlowList) {
        String content = null;
        if (RStringUtils.isNotEmpty(novelContentXR)) {
            content = XSoupUtils.htmlToStr(pageSource, novelContentXR);
        } else if (RObjectsUtils.nonNull(novelContentXRFlowList)) {
            for (String regex : novelContentXRFlowList) {
                content = XSoupUtils.htmlToStr(pageSource, regex);
                if (RStringUtils.isNotEmpty(content)) {
                    break;
                }
            }

        } else {
            throw new RuntimeException("必须配置小说内容正则");
        }
        return content;
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

        // 将图片替换为文字
        // 1. xsoup匹配出所有的图片
        List<String> imgList = XSoupUtils.htmlToList(content, "//img");
        // 2. 对图片进行保存和识别
        // <img src="/toimg/data/0744208851.png">
        Set<String> stringSet = new HashSet<>(imgList);
        for (String imgUrl : stringSet) {
            log.info("imgUrl [{}]", imgUrl);
            String urlPath = imgUrl
                    .replace("<img src=\"", "")
                    .replace("\">", "");
            log.info("urlPath [{}]", urlPath);
            // 3. 将图片替换为文字
            String result = CharsetOCR.ocr(rootUrl + urlPath);

            //  处理前后带空格的这些情况
            content = content.replaceAll("\n " + imgUrl + " \n", result);
            content = content.replaceAll("\n " + imgUrl + " ", result);
            content = content.replaceAll("\n " + imgUrl, result);
            content = content.replaceAll(" " + imgUrl, result);
            content = content.replaceAll(imgUrl, result);

        }

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
