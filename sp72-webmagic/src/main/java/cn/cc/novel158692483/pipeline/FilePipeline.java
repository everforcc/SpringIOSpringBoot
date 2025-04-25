package cn.cc.novel158692483.pipeline;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
public class FilePipeline implements Pipeline {


    public FilePipeline() {
    }


    @Override
    public void process(ResultItems resultItems, Task task) {
        String fileName = "";
        String content = "";
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            log.info("k: {}, v:{}", entry.getKey(), entry.getValue());
            if ("k".equals(entry.getKey())) {
                fileName = entry.getValue() + ".txt";
            }
            if ("v".equals(entry.getKey())) {
                content = entry.getValue().toString();
            }
        }

        try {
            FileUtils.writeStringToFile(new File("D:/temp/test/" + fileName), content, StandardCharsets.UTF_8);
            FileUtils.writeStringToFile(new File("D:/temp/test/绚丽人生.txt"), content, StandardCharsets.UTF_8, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
