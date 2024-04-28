package com.leopard.drools.common.utils;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.spring.KModuleBeanFactoryPostProcessor;

/**
 * @Description : 规则引擎热加载
 * @Author : GKL
 * @Date: 2024-04-26 13:27
 */
public class KieUtils {

    private static KieContainer kieContainer;

    private static KieSession kieSession;

    private static KModuleBeanFactoryPostProcessor kModuleBeanFactoryPostProcessor;

    public static KieContainer getKieContainer() {
        return kieContainer;
    }

    public static void setKieContainer(KieContainer kieContainer) {
        KieUtils.kieContainer = kieContainer;
        kieSession = kieContainer.newKieSession();
    }

    public static KieSession getKieSession() {
        return kieSession;
    }

    public static void setKieSession(KieSession kieSession) {
        KieUtils.kieSession = kieSession;
    }

    public static KieServices getKieServices() {
        return KieServices.Factory.get();
    }

    public static KModuleBeanFactoryPostProcessor getkModuleBeanFactoryPostProcessor() {
        return kModuleBeanFactoryPostProcessor;
    }

    public static void setkModuleBeanFactoryPostProcessor(KModuleBeanFactoryPostProcessor kModuleBeanFactoryPostProcessor) {
        KieUtils.kModuleBeanFactoryPostProcessor = kModuleBeanFactoryPostProcessor;
    }
}