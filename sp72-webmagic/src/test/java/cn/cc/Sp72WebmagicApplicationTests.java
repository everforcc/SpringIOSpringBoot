package cn.cc;

import cn.cc.config.pageprocessor.GithubRepoPageProcessor;
import cn.cc.config.pageprocessor.SP01PageProcessor;
import cn.cc.config.pipeline.DtoSQLPipeline;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.scheduler.RedisScheduler;

@SpringBootTest
class Sp72WebmagicApplicationTests {

    @Test
    void contextLoads() {
        Spider.create(new GithubRepoPageProcessor())
                //从https://github.com/code4craft开始抓
                .addUrl("https://github.com/code4craft")
                //设置Scheduler，使用Redis来管理URL队列
                // 2.9.0 高版本 returnResource 变为 protected
                .setScheduler(new RedisScheduler("localhost"))
                //设置Pipeline，将结果以json方式保存到文件
                .addPipeline(new JsonFilePipeline("D:\\data\\webmagic\\1.txt"))
                //开启5个线程同时执行
                .thread(5)
                //启动爬虫
                .run();
    }

    @Test
    void SP01PageProcessor() {
        Spider.create(new SP01PageProcessor())
                //从"https://github.com/code4craft"开始抓
                .addUrl("http://127.0.0.1:8001/open/init/dog")
//                .setScheduler(new RedisScheduler("localhost"))
                //设置Pipeline，将结果以json方式保存到文件
                .addPipeline(new JsonFilePipeline("D:\\data\\webmagic1\\"))
                //开启5个线程抓取
                .thread(5)
                //启动爬虫
                .run();
    }

    @Test
    void dtoSQLPipeline() {
        Spider.create(new SP01PageProcessor())
                //从"https://github.com/code4craft"开始抓
                .addUrl("http://127.0.0.1:8001/open/init/dog")
//                .setScheduler(new RedisScheduler("localhost"))
                //设置Pipeline，将结果以json方式保存到文件
                .addPipeline(new DtoSQLPipeline())
                //开启5个线程抓取
                .thread(5)
                //启动爬虫
                .run();
    }

}
