package cn.cc.kunnu;

import cn.cc.kunnu.dao.CcCrawBookCapterDao;
import cn.cc.kunnu.dao.CcCrawBookContentDao;
import cn.cc.kunnu.pageprocessor.KunNuPageProcessor;
import cn.cc.kunnu.pipeline.NovelFilePipeline;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import us.codecraft.webmagic.Spider;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
public class KunNuPageProcessorTest {

    @Resource
    CcCrawBookCapterDao ccCrawBookCapterDao;

    @Resource
    CcCrawBookContentDao ccCrawBookContentDao;

    @Test
    void contextLoads() {
        Spider.create(new KunNuPageProcessor())
                .addUrl("https://www.kunnu.com/doupo/")
                .addPipeline(new NovelFilePipeline(ccCrawBookCapterDao, ccCrawBookContentDao)) // 使用新的输出方式
                .thread(Runtime.getRuntime().availableProcessors() * 2)
                .run();
    }

    // 重新构建章节列表

}
