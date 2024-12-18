package cn.cc.config.pageprocessor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Json;

import java.util.Arrays;


public class SP01PageProcessor implements PageProcessor {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        Json json = page.getJson();

        System.out.println("json: " + json.toString());
        // page.getHtml().get()
        // 部分二：定义如何抽取页面信息，并保存下来
        page.putField("todo", "todo");
        page.putField("author", page.getJson().jsonPath("$.code").get());
        page.putField("name", page.getJson().jsonPath("$.data.list").get());
        if (page.getResultItems().get("name") == null) {
            //skip this page
            page.setSkip(true);
        }
        page.putField("readme", page.getJson().jsonPath("$.data.email").get());

        // 部分三：从页面发现后续的url地址来抓取
        page.addTargetRequests(Arrays.asList("http://127.0.0.1:8001/open/init/dog1"));
//        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new SP01PageProcessor())
                //从"https://github.com/code4craft"开始抓
                .addUrl("http://127.0.0.1:8001/open/init/dog")
                //开启5个线程抓取
                .thread(5)
                //启动爬虫
                .run();
    }
}