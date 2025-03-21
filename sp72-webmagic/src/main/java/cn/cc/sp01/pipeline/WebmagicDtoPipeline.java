package cn.cc.sp01.pipeline;

import cn.cc.sp01.dao.WebmagicDao;
import cn.cc.sp01.dto.WebmagicDto;
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
