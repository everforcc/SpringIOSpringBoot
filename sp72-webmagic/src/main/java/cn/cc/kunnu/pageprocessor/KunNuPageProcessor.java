package cn.cc.kunnu.pageprocessor;

import cn.cc.kunnu.pipeline.NovelFilePipeline;
import cn.cc.novel158692483.pipeline.FilePipeline;
import cn.cc.utils.DataCleanUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.Collections;
import java.util.List;


public class KunNuPageProcessor implements PageProcessor {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(10).setSleepTime(5000);

    int i = 0;

    @Override
    public void process(Page page) {
        Html html = page.getHtml();

        // 判断是否是目录页（书籍首页）
        if (page.getUrl().toString().matches("https://www\\.kunnu\\.com/doupo/")) {
            // 提取章节链接
            // https://www.kunnu.com/doupo/20980.htm
            List<String> links = html.links()
                    .regex("https://www\\.kunnu\\.com/doupo/\\d+\\.htm")
                    .all();
            page.addTargetRequests(links);

            System.out.println("提取到章节数: " + links.size());
        }
        // 判断是否是章节内容页
        else if (page.getUrl().toString().matches("https://www\\.kunnu\\.com/doupo/\\d+\\.htm")) {
            i++;
            String title = html.xpath("//*[@id=\"nr_title\"]/text()").toString()
                    .replace("/", "-")
                    .replaceAll("\\s+", "");

            // tidyText()
            String content = html.xpath("//*[@id=\"nr1\"]").get();
//                    .replaceAll("\\s+", " ")
//                    .trim();

            System.out.println("content");
            System.out.println(content);

            content = DataCleanUtils.cleanData(content);

            System.out.println("title");
            System.out.println(title);
            System.out.println("content");
            System.out.println(content);

            // 保存章节标题和内容
            page.putField("title", title);
            page.putField("content", content);

            // 提取“下一章”链接继续爬取
            String nextLink = html.xpath("//*[@id=\"pagewrap\"]/article/nav[2]/ul/li[2]/a/@href").toString();
            if (nextLink.endsWith(".html")) {
                page.addTargetRequests(Collections.singletonList("https://www.kunnu.com" + nextLink));
            }

        }
    }


    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new KunNuPageProcessor())
                .addUrl("https://www.kunnu.com/doupo/")
                .addPipeline(new NovelFilePipeline()) // 使用新的输出方式
                .thread(1)
                .run();
    }

}