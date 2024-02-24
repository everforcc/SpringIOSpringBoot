/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-06 11:11
 * Copyright
 */

package cn.cc.sp31usercraw.flow.impl;

import cn.cc.sp31usercraw.business.IBusiness;
import cn.cc.sp31usercraw.config.SysConfig;
import cn.cc.sp31usercraw.dodo.DownDo;
import cn.cc.sp31usercraw.dto.NovelConfigDto;
import cn.cc.sp31usercraw.dto.NovelContentDto;
import cn.cc.sp31usercraw.dto.NovelMsgDto;
import cn.cc.sp31usercraw.flow.INovelCommonFlowService;
import cn.cc.sp31usercraw.utils.DownUtils;
import cn.cc.sp31usercraw.utils.UrlFormatUtils;
import com.cc.sp90utils.commons.io.JFileNameUtils;
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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * 处理公共的小说的流程
 * <p>
 * 1. http请求
 * 1.1. 完整地址
 * 1.2. /根目录地址
 * 1.3. a.html 替换当前地址
 * 取出之后自行判断
 * <p>
 * 2. 使用驱动
 * 或者直接模拟点击，建议用这种，上面的判断逻辑麻烦，而且js重新运行特殊情况处理麻烦
 * 2.1. 有下一页按钮
 * 2.2. 没有下一页按钮,从1到最后全部存在，没有省略的模式
 * <p>
 * 当前使用2.2
 */
@Slf4j
@Service
public class NovelCommonFlowService implements INovelCommonFlowService {

    @Resource
    SysConfig sysConfig;
    /**
     * web驱动池
     * 1. http直接请求,速度快但是编码麻烦
     * 2. selenium运行慢,但是编写速度快
     */
    @Resource
    SeleniumPool seleniumPool;

    // 数据处理的实现接口
    @Resource(name = "businessDefault")
    //@Resource(name = "businessDDDD")
            IBusiness iBusiness;

    /**
     * 1. 获取小说基本信息
     *
     * @param url            小说页链接
     * @param novelConfigDto 配置信息
     * @return 小说基本信息
     */
    @Override
    public NovelMsgDto getNovelMsg(String url, NovelConfigDto novelConfigDto) {
        log.info("获取小说基本信息 [{}]", url);

        // 必传字段校验
        AppCode.A00100.assertNonNull(novelConfigDto, "小说配置不能为空");
        AppCode.A00100.assertNonNull(url, "小说地址不能为空");
        AppCode.A00100.assertNonNull(novelConfigDto.getNovelMsgTileXR(), "小说标题不能为空");

        // 汇总小说基本信息
        NovelMsgDto novelMsgDto = new NovelMsgDto();

        // 小说标题正则
        String novelMsgTileXR = novelConfigDto.getNovelMsgTileXR();

        // 请求客户端
        WebDriverPDto webDriverPDto = null;

        try {
            // 初始化客户端
            webDriverPDto = seleniumPool.getDriverDto();

            // 网站请求返回数据
            WebSiteDataVO webSiteDataVO = webDriverPDto.getHtml(url);
            String pageSource = webSiteDataVO.getPageSource();

            // 1. 小说标题
            String title = XSoupUtils.htmlToStr(pageSource, novelMsgTileXR);
            novelMsgDto.setTitle(title);

            log.info("小说名是: {}", title);

            // 2. 小说作者
            if (RStringUtils.isNotEmpty(novelConfigDto.getNovelMsgAuthXR())) {
                String novelMsgAuthXR = novelConfigDto.getNovelMsgAuthXR();
                //webSiteDataVO = webDriverPDto.getHtml(url);
                pageSource = webSiteDataVO.getPageSource();
                String auther = XSoupUtils.htmlToStr(pageSource, novelMsgAuthXR);
                log.info("小说作者是: {}", auther);
                novelMsgDto.setAuther(auther);
            }

            // 3. 小说描述
            if (RStringUtils.isNotEmpty(novelConfigDto.getNovelMsgDescriptionXR())) {
                String novelMsgDescriptionXR = novelConfigDto.getNovelMsgDescriptionXR();
                //webSiteDataVO = webDriverPDto.getHtml(url);
                pageSource = webSiteDataVO.getPageSource();
                String description = XSoupUtils.htmlToStr(pageSource, novelMsgDescriptionXR);
                log.info("小说描述: {}", description);
                novelMsgDto.setDescription(description);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 最终都要释放
//            if (RObjectsUtils.nonNull(webDriverPDto)) {
//                seleniumPool.close(webDriverPDto);
//            }
        }

        return novelMsgDto;
    }

    /**
     * 2. 根据小说地址获取小说目录集合
     *
     * @param url            小说地址
     * @param novelConfigDto 配置信息
     * @return 小说目录集合
     */
    @Override
    public NovelMsgDto getMenu(String url, NovelConfigDto novelConfigDto, NovelMsgDto novelMsgDto) {
        log.info("获取小说章节 [{}]", url);

        // 必传字段校验
        AppCode.A00100.assertNonNull(novelConfigDto, "小说配置不能为空");
        AppCode.A00100.assertNonNull(url, "小说地址不能为空");
        //AppCode.A00100.assertNonNull(novelConfigDto.getNovelCapterMenuXR(), "小说章节地址不能为空");

        // 从链接获取网站跟地址
        // String rootUrl = HttpParamUtils.getRootUrl(url);

        // 首页地址
        String indexMenuUrl;
        // 目录下一页
        String nextPageUrl;

        // 点击目录出现章节 的链接
        String novelCapterMenuXR = novelConfigDto.getNovelCapterMenuXR();
        // 客户端
        WebDriverPDto webDriverPDto = null;

        try {
            // 初始化客户端
            webDriverPDto = seleniumPool.getDriverDto();
            // 网站请求返回数据
            WebSiteDataVO webSiteDataVO = webDriverPDto.getHtml(url);

            String pageSource = null;

            /* 章节链接总数 */
            List<String> dealUrlList = new ArrayList<>();

            // 如果存在说明需要点击 才能出现目录列表
            if (RStringUtils.isNotEmpty(novelCapterMenuXR)) {
                pageSource = webSiteDataVO.getPageSource();
                indexMenuUrl = XSoupUtils.htmlToStr(pageSource, novelCapterMenuXR);
                // 将返回的链接处理为能直接访问的
                indexMenuUrl = UrlFormatUtils.formatUrl(indexMenuUrl, url);

                // 目录页地址
                nextPageUrl = indexMenuUrl;

                log.info("小说章节地址是: {}", nextPageUrl);
                novelMsgDto.setIndexMenuUrl(nextPageUrl);

            } else { // 如果不存在,说明当前页就是目录页
                // 目录页地址
                nextPageUrl = url;
                novelMsgDto.setIndexMenuUrl(nextPageUrl);
                log.info("小说章节地址是: {}", nextPageUrl);
            }

            boolean nextPageFlag = false;
            String urlIndex = "";

            do {
                // 目录当前页,如果有下一页在下面赋值下一页
                indexMenuUrl = nextPageUrl;
                log.info("准备请求目录地址 {}", nextPageUrl);

                // 当前页的章节 正则
                String novelCapterUrlListXR = novelConfigDto.getNovelCapterUrlListXR();

                // 下一页的地址的 正则
                String novelCapterPageNextUrlXR = novelConfigDto.getNovelCapterPageNextUrlXR();
                String novelCapterPageNextButtonXR = novelConfigDto.getNovelCapterPageNextButtonXR();

                // 取出目录中的章节列表
                if (RStringUtils.isNotEmpty(novelCapterUrlListXR)) {

                    pageSource = webDriverPDto.html();
                    // 从html提取正则
                    List<String> novelCapterUrlList = XSoupUtils.htmlToList(pageSource, novelCapterUrlListXR);

                    log.info("小说章节数: {}", novelCapterUrlList.size());
                    // 对链接进行处理,并放入集合
                    for (int i = 0; i < novelCapterUrlList.size(); i++) {
                        if (i == 0) {
                            if (urlIndex.equals(novelCapterUrlList.get(i))) {
                                novelCapterPageNextUrlXR = null;
                                novelCapterPageNextButtonXR = null;
                                break;
                            } else {
                                urlIndex = novelCapterUrlList.get(i);
                            }
                        }
                        dealUrlList.add(UrlFormatUtils.formatUrl(novelCapterUrlList.get(i), url));
                    }
                }

                // 如果存在正则就 获取
                if (RStringUtils.isNotEmpty(novelCapterPageNextUrlXR)) {
                    // 匹配出下一页地址
                    nextPageUrl = XSoupUtils.htmlToStr(pageSource, novelCapterPageNextUrlXR);
                    // 处理链接 为能直接访问的
                    nextPageUrl = UrlFormatUtils.formatUrl(nextPageUrl, url);
                    if (!nextPageUrl.equals(indexMenuUrl)) {
                        nextPageFlag = true;
                        // 请求链接html
                        webSiteDataVO = webDriverPDto.getHtml(nextPageUrl);
                    } else {
                        nextPageFlag = false;
                    }

                } else if (RStringUtils.isNotEmpty(novelCapterPageNextButtonXR)) {
                    webDriverPDto.click(novelCapterPageNextButtonXR);
                    nextPageFlag = true;
                } else {
                    nextPageFlag = false;
                }
                // 如果不存在就什么都不做，直接退出
            } while (nextPageFlag);

            // 输出链接总数
            log.info("一共有{}个链接", dealUrlList.size());
            // 放入对象
            novelMsgDto.setNovelCapterUrlList(dealUrlList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 最终都要释放
            if (RObjectsUtils.nonNull(webDriverPDto)) {
                seleniumPool.close(webDriverPDto);
            }
        }
        return novelMsgDto;
    }

    /**
     * 3. 根据章节链接,获取章节内容
     *
     * @param url            章节地址
     * @param novelConfigDto 配置信息
     * @param novelMsgDto    测试的时候允许为空
     * @return 章节内容
     */
    @Override
    public NovelContentDto getContent(String url, NovelConfigDto novelConfigDto, NovelMsgDto novelMsgDto) {
        log.info("获取小说章节内容 [{}]", url);

        // 基础参数校验
        AppCode.A00100.assertNonNull(novelConfigDto, "小说配置不能为空");
        AppCode.A00100.assertNonNull(url, "小说地址不能为空");
        AppCode.A00100.assertNonNull(novelConfigDto.getNovelCapterNameXR(), "小说章节名不能为空");

        // 小说章节内容
        StringBuilder stringBuilder = new StringBuilder();
        // 小说内容对象
        NovelContentDto novelContentDto = new NovelContentDto();

        // 1. 从web中获取当页内容
        WebDriverPDto webDriverPDto = null;

        try {
            webDriverPDto = seleniumPool.getDriverDto();
            WebSiteDataVO webSiteDataVO = webDriverPDto.getHtml(url);
            String pageSource = webSiteDataVO.getPageSource();

            // 2. 获取章节名
            // 章节名 正则
            String novelCapterNameXR = novelConfigDto.getNovelCapterNameXR();
            // 章节名
            String novelCapterName = XSoupUtils.htmlToStr(pageSource, novelCapterNameXR);
            // 因为要保存所以校验整洁名
            novelCapterName = JFileNameUtils.checkFileName(novelCapterName);
            log.info("章节名 {}", novelCapterName);

            // 2. 从页面获取数据正则
//        String novelContentXR = novelConfigDto.getNovelContentXR();
//        List<String> novelContentXRFlowList = novelConfigDto.getNovelContentXRFlowList();

            // 章节 html
            String contentHtml;
            // 章节处理后的txt
            String txt;

            // 3. 小说是否需要分页
            String novelContentUrlPageXR = novelConfigDto.getNovelContentUrlPageXR();

            // 不需要分页
            if (StringUtils.isEmpty(novelContentUrlPageXR)) {
                // 根据正则从html中获取 章节内容 数据
                contentHtml = iBusiness.endCondition(pageSource, novelConfigDto, null, null);
            } else { // 需要分页

                // 分页的点击超链接集合
                List<String> linkTextList = XSoupUtils.htmlToList(pageSource, novelContentUrlPageXR);
                log.info("小说一共有 {} 页", linkTextList.size());
                for (String linkText : linkTextList) {
                    log.info("点击按钮名, {}", linkText);
                    // 获取页面数据
                    contentHtml = iBusiness.endCondition(pageSource, novelConfigDto, webDriverPDto, linkText);
                    log.info("小说章节分页地址是 {} ", linkText);
                    stringBuilder.append(contentHtml);
                }
                // 合并分页数据
                contentHtml = stringBuilder.toString();
            }
            // 网页根地址
            String rootUrl = HttpParamUtils.getRootUrl(url);

            // 对内容降噪处理
            txt = iBusiness.dealContent(contentHtml, url);
            // 在内容中加上标题, 标题前后加上换行
            txt = "\r\n" + novelCapterName + "\r\n" + txt;
            // 将处理后的数据放入对象
            novelContentDto.setContent(txt);

            // 如果不为空就下载.为空说明是测试
            if (RObjectsUtils.nonNull(novelMsgDto)) {
                String title = novelMsgDto.getTitle();
                log.info("正在保存小说: 《{}》", title);
                // 这个位置应该再处理下
                StringBuilder saveHtml = new StringBuilder();
                saveHtml.append(sysConfig.getFileRoot())
                        .append(title)
                        .append("/html/")
                        .append(novelCapterName).append(".txt");
                StringBuilder saveCapTxt = new StringBuilder();
                saveCapTxt.append(sysConfig.getFileRoot())
                        .append(title)
                        .append("/txt/")
                        .append(novelCapterName).append(".txt");
                StringBuilder saveAllTxt = new StringBuilder();
                saveAllTxt.append(sysConfig.getFileRoot())
                        .append(title)
                        .append("/all/")
                        .append(title).append(".txt");
//                // 保存html
//                // FileConstant.fileRoot + title + "/html/" + novelCapterName + "-html.txt"
//                RFileUtils.writeStringToFile(saveHtml.toString(), contentHtml);
//                // 保存txt
//                // FileConstant.fileRoot + title + "/txt/" + novelCapterName + ".txt"
//                RFileUtils.writeStringToFile(saveCapTxt.toString(), txt);
//                // 保存全本
//                // FileConstant.fileRoot + title + "/all/" + title + "-all" + ".txt"
//                boolean append = true;
//                RFileUtils.writeStringToFile(saveAllTxt.toString(), txt, append);


                DownUtils downUtils = DownUtils.instantce(rootUrl, sysConfig.getFileRoot());
                // 1. html
                DownDo downDoHtml = new DownDo();
                downDoHtml.setPath(saveHtml.toString());
                downDoHtml.setContent(contentHtml);
                downDoHtml.setAppend(false);

                // 2. 保存章节
                DownDo downDoCapTXT = new DownDo();
                downDoCapTXT.setPath(saveCapTxt.toString());
                downDoCapTXT.setContent(txt);
                downDoCapTXT.setAppend(false);

                // 3. 全本txt
                DownDo downDoAllTXT = new DownDo();
                downDoAllTXT.setPath(saveAllTxt.toString());
                downDoAllTXT.setContent(txt);
                downDoAllTXT.setAppend(true);

                downUtils.add(downDoHtml);
                downUtils.add(downDoCapTXT);
                downUtils.add(downDoAllTXT);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 最终都要释放
            if (RObjectsUtils.nonNull(webDriverPDto)) {
                seleniumPool.close(webDriverPDto);
            }
        }

        return novelContentDto;
    }


}
