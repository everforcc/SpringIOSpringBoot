package cn.cc.webmagic.pageprocessor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Json;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;


public class WebMagicPageProcessor implements PageProcessor {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {

        // 1. 获取数据

        // jsonPath 处理
        Json json = page.getJson();
        String code = json.jsonPath("$.code").get();
        List<String> dataList = json.jsonPath("$.data.list").all();

        // xpath 处理
        Html html = page.getHtml();
        String name = page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString();

        // 处理url
        Selectable url = page.getUrl();
        String author = url.regex("https://github\\.com/(\\w+)/.*").toString();

        System.out.println("json: " + json.toString());

        // 部分二：定义如何抽取页面信息，并保存下来
        page.putField("todo", "todo");
        page.putField("author", page.getJson());
        page.putField("name", page.getJson().jsonPath("$.data.list").get());
        // 校验抽取结果
        if (page.getResultItems().get("name") == null) {
            //skip this page, 跳过页面
            page.setSkip(true);
        }
        page.putField("readme", page.getJson().jsonPath("$.data.email").get());

        // 部分三：从页面发现后续的url地址来抓取
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider spider = Spider.create(new WebMagicPageProcessor());
            spider
                .addUrl("https://github.com/code4craft") //开始抓
                .addPipeline(new ConsolePipeline())
                //开启5个线程抓取
                .thread(5)
                //启动爬虫
                .run();
        // 可以停止
    }
}