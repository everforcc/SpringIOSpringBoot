package cn.cc.webmagic;

import cn.cc.config.pageprocessor.GithubRepoPageProcessor;
import org.junit.Test;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.scheduler.RedisScheduler;

public class WebmagicTest {


    public void testWebmagic(){
        Spider.create(new GithubRepoPageProcessor())
                //从https://github.com/code4craft开始抓
                .addUrl("https://github.com/code4craft")
                //设置Scheduler，使用Redis来管理URL队列
                // 2.9.0 高版本 returnResource 变为 protected
                .setScheduler(new RedisScheduler("localhost"))
                //设置Pipeline，将结果以json方式保存到文件
                .addPipeline(new JsonFilePipeline("D:\\data\\webmagic"))
                //开启5个线程同时执行
                .thread(5)
                //启动爬虫
                .run();
    }

}
