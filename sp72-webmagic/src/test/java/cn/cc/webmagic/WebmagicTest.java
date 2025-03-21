package cn.cc.webmagic;

import cn.cc.github.pageprocessor.GithubRepoPageProcessor;
import cn.cc.sp01.dto.WebmagicDto;
import cn.cc.sp01.pipeline.WebmagicDtoPipeline;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.scheduler.RedisScheduler;

@Slf4j
@SpringBootTest
public class WebmagicTest {

    @Test
    public void testWebmagic() {
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

    @Autowired
    WebmagicDtoPipeline webmagicDtoPipeline;

    // 待完善
    @Test
    void webmagicDtoPipeline() {
        OOSpider.create(Site.me()
                                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36"),
                        webmagicDtoPipeline,
                        WebmagicDto.class)
                .addUrl("http://127.0.0.1:8001/open/init/dog")
                .thread(5)
                .run();
    }

}
