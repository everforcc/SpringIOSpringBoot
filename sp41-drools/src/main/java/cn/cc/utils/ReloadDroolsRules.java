package cn.cc.utils;

import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;

/**
 * @Description : 热加载
 * @Author : GKL
 * @Date: 2024-04-29 10:04
 */
@Slf4j
public abstract class ReloadDroolsRules {

    protected abstract void loadRule(String drlName, KieFileSystem kfs);

    public void reload(String drlName) {
        KieServices kieServices = KieUtils.getKieServices();
        KieFileSystem kfs = kieServices.newKieFileSystem();
        loadRule(drlName, kfs);
        KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            System.out.println(results.getMessages());
            throw new IllegalStateException("### errors ###");
        }
        KieUtils.setKieContainer(kieServices.newKieContainer(KieUtils.getKieServices().getRepository().getDefaultReleaseId()));
        log.info("新规则重载成功");
    }

}
