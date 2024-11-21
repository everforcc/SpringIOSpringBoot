/**
 * @Description
 * @Author everforcc
 * @Date 2022-06-25 22:36
 * Copyright
 */

package cn.cc.sp31usercraw.config;

import cn.cc.sp31usercraw.dodo.NovelConfigDo;
import cn.cc.sp31usercraw.dto.NovelConfigDto;
import com.alibaba.fastjson.JSONObject;
import cn.cc.utils.commons.io.RFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 小说配置初始化
 */
@Slf4j
@Component
public class NovelCrawConfig {

    /**
     * 初始化小说配置文件
     *
     * @return 配置bean
     */
    @Bean("novelConfigDo")
    private NovelConfigDo initNovelConfig() {

        log.info("初始化小说配置");
        NovelConfigDo novelConfigDo = new NovelConfigDo();
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:json");
            log.info(" 是否是文件夹: {} ", file.isDirectory());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        File[] files = file.listFiles();
        log.info("一共有 [{}] 个配置文件", files.length);
        for (File f : files) {
            String configJson = RFileUtils.readFileToString(f);
            NovelConfigDto novelConfigDto = JSONObject.parseObject(configJson, NovelConfigDto.class);
            novelConfigDo.putConfig(novelConfigDto);
        }
        log.info("已新增 [{}] 个配置", novelConfigDo.size());
        return novelConfigDo;
    }

}
