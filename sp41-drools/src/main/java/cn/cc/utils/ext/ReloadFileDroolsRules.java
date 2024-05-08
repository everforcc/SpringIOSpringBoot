package cn.cc.utils.ext;

import cn.cc.constant.RuleEngineConstants;
import cn.cc.dto.RuleDrlDto;
import cn.cc.utils.ReloadDroolsRules;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.builder.KieFileSystem;
import org.kie.internal.io.ResourceFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @Description : 加载文件数据
 * @Author : GKL
 * @Date: 2024-04-29 10:16
 */
@Slf4j
@Component
public class ReloadFileDroolsRules extends ReloadDroolsRules {
    @Override
    protected void loadRule(RuleDrlDto ruleDrlDto, KieFileSystem kfs) {
        Resource[] resources;
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        try {
            if (StringUtils.isEmpty(ruleDrlDto.getFileName())) {
                resources = resourcePatternResolver.getResources(RuleEngineConstants.Load_PATH + RuleEngineConstants.filePattern);
            } else {
                resources = resourcePatternResolver.getResources(RuleEngineConstants.Load_PATH + "**/" + ruleDrlDto.getFileName() + ".*");
            }
            for (Resource file : resources) {
                kfs.write(ResourceFactory.newClassPathResource(RuleEngineConstants.Load_PATH + file.getFilename(), RuleEngineConstants.charset));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("规则文件加载失败");
        }
        log.info("从文件加载drl规则");
    }
}
