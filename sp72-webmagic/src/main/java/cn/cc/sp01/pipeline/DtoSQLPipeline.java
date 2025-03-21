package cn.cc.sp01.pipeline;

import cn.cc.sp01.dao.WebmagicDao;
import cn.cc.sp01.dto.WebmagicDto;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Date;
import java.util.Map;

@Slf4j
public class DtoSQLPipeline implements Pipeline {

    WebmagicDao webmagicDao;

    public DtoSQLPipeline() {
    }

    public DtoSQLPipeline(WebmagicDao webmagicDao) {
        this.webmagicDao = webmagicDao;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            log.info("k: {}, v:{}", entry.getKey(), entry.getValue());
            webmagicDao.insert(new WebmagicDto(entry.getKey(), 1, new Date()));
        }

    }
}
