package cn.cc.config.pipeline;

import cn.cc.test.dao.WebmagicDao;
import cn.cc.test.dto.WebmagicDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

@Slf4j
@Component("webmagicDtoPipeline")
public class WebmagicDtoPipeline implements PageModelPipeline<WebmagicDto> {

    @Autowired
    WebmagicDao webmagicDao;

    @Override
    public void process(WebmagicDto webmagicDto, Task task) {
        log.info("webmagicDto:{}", webmagicDto);
        webmagicDao.insert(webmagicDto);
    }
}
