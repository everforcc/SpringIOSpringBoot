package cn.cc.config.pipeline;

import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

@Slf4j
public class DtoSQLPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            log.info("k: {}, v:{}", entry.getKey(), entry.getValue());
        }
    }
}
