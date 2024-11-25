package cn.cc.config;

import cn.cc.constant.RuleEngineConstants;
import cn.cc.utils.KieUtils;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.spring.KModuleBeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

/**
 * @Description : 规则引擎配置
 * @Author : GKL
 * @Date: 2024-04-29 09:35
 */
@Slf4j
@Configuration
public class RuleEngineConfig {

    /**
     * 初始化 源码注释推荐这样
     *
     * @return KieServices
     */
    private KieServices getKieServices() {
        return KieServices.Factory.get();
    }

    /**
     * @return KieFileSystem
     * ConditionalOnMissingBean 它是修饰bean的一个注解， 主要实现的是，当你的bean被注册之后，如果而注册相同类型的bean，就不会成功，
     * 它会保证你的bean只有一个，即你的实例只有一个，当你注册多个相同的bean时，会出现异常
     */
    @Bean
    @ConditionalOnMissingBean(KieFileSystem.class)
    public KieFileSystem kieFileSystem() throws IOException {
        KieFileSystem kieFileSystem = getKieServices().newKieFileSystem();
        //获取初始化规则文件所在路径
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] files = resourcePatternResolver.getResources(
                RuleEngineConstants.BASE_RULES_PATH + RuleEngineConstants.RULES_PATH + RuleEngineConstants.filePattern);
        String path = null;
        for (Resource file : files) {
            path = RuleEngineConstants.RULES_PATH + file.getFilename();
            //将规则文件写规则引擎系统内
            kieFileSystem.write(ResourceFactory.newClassPathResource(path, RuleEngineConstants.charset));
        }
        return kieFileSystem;
    }

    @Bean
    @ConditionalOnMissingBean(KieContainer.class)
    public KieContainer kieContainer() throws IOException {

        final KieRepository kieRepository = getKieServices().getRepository();
        kieRepository.addKieModule(kieRepository::getDefaultReleaseId);
        /*
         * @Configuration
         * 类上没加注释的时候报错
         * Method annotated with @Bean is called directly. Use dependency injection instead.
         */
        KieBuilder kieBuilder = getKieServices().newKieBuilder(kieFileSystem());
        kieBuilder.buildAll();
        KieContainer kieContainer = getKieServices().newKieContainer(kieRepository.getDefaultReleaseId());
        KieUtils.setKieContainer(kieContainer);
        return getKieServices().newKieContainer(kieRepository.getDefaultReleaseId());
    }


    @Bean
    @ConditionalOnMissingBean(KieBase.class)
    public KieBase kieBase() throws IOException {
        return kieContainer().getKieBase();
    }

    @Bean
    @ConditionalOnMissingBean(KieSession.class)
    public KieSession kieSession() throws IOException {
        KieSession kieSession = kieContainer().newKieSession();
        KieUtils.setKieSession(kieSession);
        return kieSession;
    }

    @Bean
    @ConditionalOnMissingBean(KModuleBeanFactoryPostProcessor.class)
    public KModuleBeanFactoryPostProcessor kModuleBeanFactoryPostProcessor() {
        return new KModuleBeanFactoryPostProcessor();
    }

}
