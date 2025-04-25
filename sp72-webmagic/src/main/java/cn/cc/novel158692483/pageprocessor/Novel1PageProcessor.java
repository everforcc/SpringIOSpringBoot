package cn.cc.novel158692483.pageprocessor;

import cn.cc.novel158692483.pipeline.FilePipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.Collections;


public class Novel1PageProcessor implements PageProcessor {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(10).setSleepTime(5000);

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
//        Json json = page.getJson();
        Html html = page.getHtml();
//        String divHtml = html.xpath("//div[@id='nr1']/text()").toString();
        String divHtml = html.xpath("//div[@id='nr1']").get();
        divHtml = divHtml.replaceAll("&nbsp;", " ")
                .replaceAll("<div.*?>", "")
                .replaceAll("</div.*?>", "")
                .replaceAll("<br>", "")
                .replaceAll("<script>_17mb_top\\(\\);</script>", "")
                .replace("搜索，用户注册与阅读记录，书架等功能重新开放 \n" +
                        "  \n" +
                        "  百度搜索新暖才文学网,即可找到我们,网址为拼音缩写https://www.xncwxw.me \n" +
                        " (前面加https，http可能无法访问), \n" +
                        " 即将改版,更多精彩小说请点击\"首页-分类-其他小说\" \n" +
                        " \n" +
                        "  书架功能已恢复，可注册登录账号 ", "")
//                .replace("\n","")
                .replaceAll(" \n", "\n")
                .replaceAll(" \n+", "\n")
                .replaceAll("\n+", "\n")
//                .replaceAll("\r+", "\r")
//                .replace("\r\n+", "\n\r")
        ;
        System.out.println("---");
        System.out.println(divHtml);
        System.out.println("---");
        String title = html.xpath("//div[@id='nr_title']/text()").toString()
                .replace("/", "-")
                .replaceAll(" ", "");
        System.out.println(title);
        System.out.println("---");
        page.putField("k", title);
        page.putField("v", divHtml);

        String nextLink = html.xpath("//a[@id='pb_next']/@href").toString();

        // http://158.69.248.3/52_52457/21987814_7.html
        //                    /52_52457/19435660_2.html
        System.out.println(nextLink);
        System.out.println("---");
        // 部分三：从页面发现后续的url地址来抓取
        if (nextLink.endsWith(".html")) {
            page.addTargetRequests(Collections.singletonList("http://158.69.248.3" + nextLink));
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new Novel1PageProcessor())
                //从"https://github.com/code4craft"开始抓
                .addUrl("http://158.69.248.3/52_52457/19435660.html")
                .addPipeline(new FilePipeline())
                //开启5个线程抓取
                .thread(1)
                //启动爬虫
                .run();
    }
}