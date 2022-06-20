/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-06 11:11
 * Copyright
 */

package cn.cc.sp31usercraw.flow.impl;

import cn.cc.sp31usercraw.business.IBusiness;
import cn.cc.sp31usercraw.constant.FileConstant;
import cn.cc.sp31usercraw.dto.NovelConfigDto;
import cn.cc.sp31usercraw.dto.NovelContentDto;
import cn.cc.sp31usercraw.dto.NovelMsgDto;
import cn.cc.sp31usercraw.flow.INovelCommonFlowService;
import cn.cc.sp31usercraw.utils.CharsetOCR;
import cn.cc.sp31usercraw.utils.UrlFormatUtils;
import com.cc.sp90utils.commons.io.JFileNameUtils;
import com.cc.sp90utils.commons.io.RFileUtils;
import com.cc.sp90utils.commons.lang.RObjectsUtils;
import com.cc.sp90utils.commons.lang.RStringUtils;
import com.cc.sp90utils.commons.web.HttpParamUtils;
import com.cc.sp90utils.exception.AppCode;
import com.cc.sp90utils.http.selenium.SeleniumPool;
import com.cc.sp90utils.http.selenium.WebDriverPDto;
import com.cc.sp90utils.http.vo.WebSiteDataVO;
import com.cc.sp90utils.jsoup.XSoupUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 处理公共的小说的流程
 */
@Slf4j
@Service
public class NovelCommonFlowService implements INovelCommonFlowService {

    /**
     * web驱动池
     */
    @Autowired
    SeleniumPool seleniumPool;

    // 数据处理的实现接口
    @Resource(name = "businessDefault")
    IBusiness iBusiness;

    /**
     * 根据小说页链接 获取小说基本信息
     *
     * @param url            小说页链接
     * @param novelConfigDto 配置信息
     * @return 小说基本信息
     */
    @Override
    public NovelMsgDto getNovelMsg(String url, NovelConfigDto novelConfigDto) {
        log.info("准备获取小说基本信息");

        // 必传字段校验
        AppCode.A00100.assertNonNull(novelConfigDto, "小说配置不能为空");
        AppCode.A00100.assertNonNull(url, "小说地址不能为空");
        AppCode.A00100.assertNonNull(novelConfigDto.getNovelMsgTileXR(), "小说标题不能为空");

        // 汇总小说基本信息
        NovelMsgDto novelMsgDto = new NovelMsgDto();

        /* 获取小说标题正则 */
        String novelMsgTileXR = novelConfigDto.getNovelMsgTileXR();

        // 发请求拿信息，然后正则匹配数据
        WebDriverPDto webDriverPDto = seleniumPool.getDriverDto();
        WebSiteDataVO webSiteDataVO = webDriverPDto.getHtml(url);
        String pageSource = webSiteDataVO.getPageSource();
        String title = XSoupUtils.htmlToStr(pageSource, novelMsgTileXR);
        log.info("小说名是: {}", title);
        novelMsgDto.setTitle(title);

        /* 小说作者 */
        if (RStringUtils.isNotEmpty(novelConfigDto.getNovelMsgAuthXR())) {
            String novelMsgAuthXR = novelConfigDto.getNovelMsgAuthXR();
            webSiteDataVO = webDriverPDto.getHtml(url);
            pageSource = webSiteDataVO.getPageSource();
            String auther = XSoupUtils.htmlToStr(pageSource, novelMsgAuthXR);
            log.info("小说作者是: {}", auther);
            novelMsgDto.setAuther(auther);
        }

        /* 小说描述 */
        if (RStringUtils.isNotEmpty(novelConfigDto.getNovelMsgDescriptionXR())) {
            String novelMsgDescriptionXR = novelConfigDto.getNovelMsgDescriptionXR();
            webSiteDataVO = webDriverPDto.getHtml(url);
            pageSource = webSiteDataVO.getPageSource();
            String description = XSoupUtils.htmlToStr(pageSource, novelMsgDescriptionXR);
            log.info("小说描述: {}", description);
            novelMsgDto.setDescription(description);
        }

        seleniumPool.close(webDriverPDto);
        return novelMsgDto;
    }

    /**
     * 根据小说地址获取小说目录集合
     *
     * @param url            小说地址
     * @param novelConfigDto 配置信息
     * @return 小说目录集合
     */
    @Override
    public NovelMsgDto getMenu(String url, NovelConfigDto novelConfigDto) {
        log.info("准备获取小说章节地址");
        AppCode.A00100.assertNonNull(novelConfigDto, "小说配置不能为空");
        AppCode.A00100.assertNonNull(url, "小说地址不能为空");
        AppCode.A00100.assertNonNull(novelConfigDto.getNovelCapterMenuXR(), "小说章节地址不能为空");

        NovelMsgDto novelMsgDto = new NovelMsgDto();
        String rootUrl = HttpParamUtils.getRootUrl(url);

        String novelCapterMenuXR = novelConfigDto.getNovelCapterMenuXR();
        WebDriverPDto webDriverPDto = seleniumPool.getDriverDto();
        WebSiteDataVO webSiteDataVO = webDriverPDto.getHtml(url);
        String pageSource = webSiteDataVO.getPageSource();
        String indexMenuUrl = XSoupUtils.htmlToStr(pageSource, novelCapterMenuXR);
        indexMenuUrl = UrlFormatUtils.formatUrl(indexMenuUrl, rootUrl);
        log.info("小说章节地址是: {}", indexMenuUrl);
        novelMsgDto.setIndexMenuUrl(indexMenuUrl);

        /* 章节链接总数 */
        List<String> dealUrlList = new ArrayList<>();
        /* 判断是否有分页 */
        String nextPageUrl = indexMenuUrl;
        do {
            // 给下一页赋值，请求
            indexMenuUrl = nextPageUrl;
            log.info("准备请求目录地址 {}", nextPageUrl);
            // 取出目录中的章节列表
            if (RStringUtils.isNotEmpty(novelConfigDto.getNovelCapterUrlListXR())) {
                /* 取出某一页的章节信息 */
                String novelCapterUrlListXR = novelConfigDto.getNovelCapterUrlListXR();
                webSiteDataVO = webDriverPDto.getHtml(nextPageUrl);
                pageSource = webSiteDataVO.getPageSource();
                List<String> novelCapterUrlList = XSoupUtils.htmlToList(pageSource, novelCapterUrlListXR);
                log.info("小说章节数: {}", novelCapterUrlList.size());
                // 取出链接后进行处理
                /**
                 * 1. http:www/html
                 * 2. /html
                 * 3. html
                 */

                for (String urlStr : novelCapterUrlList) {
                    dealUrlList.add(UrlFormatUtils.formatUrl(urlStr, rootUrl));
                }

            }

            // 获取下一页的地址
            String novelCapterPageNextUrlXR = novelConfigDto.getNovelCapterPageNextUrlXR();
            nextPageUrl = XSoupUtils.htmlToStr(pageSource, novelCapterPageNextUrlXR);
            nextPageUrl = UrlFormatUtils.formatUrl(nextPageUrl, rootUrl);

        } while (!nextPageUrl.equals(indexMenuUrl));
        // 放入链接总数
        log.info("一共有{}个链接", dealUrlList.size());
        novelMsgDto.setNovelCapterUrlList(dealUrlList);
        seleniumPool.close(webDriverPDto);
        return novelMsgDto;
    }

    /**
     * 根据章节链接，上个接口返回的信息，
     * 获取内容信息
     *
     * @param url            章节地址
     * @param novelConfigDto 配置信息
     * @return 章节内容
     */
    @Override
    public NovelContentDto getContent(String url, NovelConfigDto novelConfigDto, NovelMsgDto novelMsgDto) {
        log.info("准备获取小说章节地址");
        AppCode.A00100.assertNonNull(novelConfigDto, "小说配置不能为空");
        AppCode.A00100.assertNonNull(url, "小说地址不能为空");
        AppCode.A00100.assertNonNull(novelConfigDto.getNovelCapterNameXR(), "小说章节名不能为空");

        StringBuffer stringBuffer = new StringBuffer();
        NovelContentDto novelContentDto = new NovelContentDto();

        // 1. 从web中获取当页内容
        WebDriverPDto webDriverPDto = seleniumPool.getDriverDto();
        WebSiteDataVO webSiteDataVO = webDriverPDto.getHtml(url);
        String pageSource = webSiteDataVO.getPageSource();

        // 获取章节名
        String novelCapterNameXR = novelConfigDto.getNovelCapterNameXR();
        String novelCapterName = XSoupUtils.htmlToStr(pageSource, novelCapterNameXR);
        novelCapterName = JFileNameUtils.checkFileName(novelCapterName);
        log.info("章节名 {}", novelCapterName);

        // 2. 从页面获取数据正则
        String novelContentXR = novelConfigDto.getNovelContentXR();
        List<String> novelContentXRFlowList = novelConfigDto.getNovelContentXRFlowList();
        String content = "";
        // 3. 小说是否需要分页
        String novelContentUrlPageXR = novelConfigDto.getNovelContentUrlPageXR();

        // 不需要分页
        if (StringUtils.isEmpty(novelContentUrlPageXR)) {
            // 3.1 解析出内容的正则，并从网页中获取数据
            // 如果不分页，那么当前页html就是本章的html
            content = pageSource;
            novelContentDto.setContent(content);
        } else {
            /**
             * 1. http请求
             * 1.1. 完整地址
             * 1.2. /根目录地址
             * 1.3. a.html 替换当前地址
             * 取出之后自行判断
             *
             * 2. 使用驱动
             * 或者直接模拟点击，建议用这种，上面的判断逻辑麻烦，而且js重新运行特殊情况处理麻烦
             * 2.1. 有下一页按钮
             * 2.2. 没有下一页按钮,从1到最后全部存在，没有省略的模式
             *
             * 当前使用2.2
             */

            // 需要分页,分页的点击超链接集合
            List<String> linkTextList = XSoupUtils.htmlToList(pageSource, novelContentUrlPageXR);
            log.info("小说一共有 {} 页", linkTextList.size());
            for (String linkText : linkTextList) {
                log.info("点击按钮名, {}", linkText);
                // 获取页面数据
                content = iBusiness.endCondition(pageSource, novelConfigDto, webDriverPDto, linkText);
                //log.info("小说章节内容是 {} : {}", linkText, content);
                stringBuffer.append(content);
            }
            // 合并分页数据
            content = stringBuffer.toString();
        }
        // 网页根地址
        String rootUrl = HttpParamUtils.getRootUrl(url);
        //
        /**
         * 1. 处理html各种标签
         * 2. 图片等
         */
        String title = novelMsgDto.getTitle();
        log.info("正在保存小说: 《{}》", title);
        RFileUtils.writeStringToFile(FileConstant.fileRoot + title + "/html/"
                + novelCapterName + "-html.txt", content);
        // 对内容降噪处理
        content = iBusiness.dealContent(content, rootUrl);

        RFileUtils.writeStringToFile(FileConstant.fileRoot + title + "/"
                + novelCapterName + ".txt", content);
        novelContentDto.setContent(content);

        seleniumPool.close(webDriverPDto);
        return novelContentDto;
    }

    public static void main(String[] args) {
        String content = "<div class=\"chapterinfo\" id=\"chapterinfo\">       2022年06月14日  " +
                "a123" +
                " </div>  <img src=\"/data/0990372354.png\">\n" +
                " <img src=\"/data/7808420336.png\">风";
        content = content.replaceAll(">\\s+<", "><");
//        content = content.replaceAll("</div>","")
//                .replaceAll("<div .*?>","");

        System.out.println(content);
    }

}
