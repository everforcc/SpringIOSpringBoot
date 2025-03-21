package cn.cc.sp01;

import cn.cc.sp01.dao.WebmagicDao;
import cn.cc.sp01.pageprocessor.SP01PageProcessor;
import cn.cc.sp01.pipeline.DtoSQLPipeline;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;

@SpringBootTest
public class Sp01Tests {

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

    @Autowired
    WebmagicDao webmagicDao;

    @Test
    void dtoSQLPipeline() {
        Spider.create(new SP01PageProcessor())
                //从"https://github.com/code4craft"开始抓
                .addUrl("http://127.0.0.1:8001/open/init/dog")
//                .setScheduler(new RedisScheduler("localhost"))
                //设置Pipeline，将结果以json方式保存到文件
                .addPipeline(new DtoSQLPipeline(webmagicDao))
                //开启5个线程抓取
                .thread(5)
                //启动爬虫
                .run();
    }

}
