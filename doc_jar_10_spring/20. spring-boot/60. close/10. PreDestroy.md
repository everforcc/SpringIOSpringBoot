<span  style="font-family: Simsun,serif; font-size: 17px; ">

### @PreDestroy

~~~
@PreDestroy
public void destroySeleniumPool(){
    log.info("清空SeleniumPool...");
    SeleniumPool.getInstantce().destory();
    log.info("已经清空SeleniumPool...");
}
~~~
### DisposableBean接口

### 源码

~~~
CommonAnnotationBeanPostProcessor.java

    public CommonAnnotationBeanPostProcessor() {
        this.setOrder(2147483644);
        this.setInitAnnotationType(PostConstruct.class);
        this.setDestroyAnnotationType(PreDestroy.class);
        this.ignoreResourceType("javax.xml.ws.WebServiceContext");
    }

InitDestroyAnnotationBeanPostProcessor.java

    private InitDestroyAnnotationBeanPostProcessor.LifecycleMetadata buildLifecycleMetadata(Class<?> clazz) {
        if (!AnnotationUtils.isCandidateClass(clazz, Arrays.asList(this.initAnnotationType, this.destroyAnnotationType))) {
            return this.emptyLifecycleMetadata;
        } else {
            List<InitDestroyAnnotationBeanPostProcessor.LifecycleElement> initMethods = new ArrayList();
            List<InitDestroyAnnotationBeanPostProcessor.LifecycleElement> destroyMethods = new ArrayList();
            Class targetClass = clazz;

            do {
                List<InitDestroyAnnotationBeanPostProcessor.LifecycleElement> currInitMethods = new ArrayList();
                List<InitDestroyAnnotationBeanPostProcessor.LifecycleElement> currDestroyMethods = new ArrayList();
                ReflectionUtils.doWithLocalMethods(targetClass, (method) -> {
                    if (this.initAnnotationType != null && method.isAnnotationPresent(this.initAnnotationType)) {
                        InitDestroyAnnotationBeanPostProcessor.LifecycleElement element = new InitDestroyAnnotationBeanPostProcessor.LifecycleElement(method);
                        currInitMethods.add(element);
                        if (this.logger.isTraceEnabled()) {
                            this.logger.trace("Found init method on class [" + clazz.getName() + "]: " + method);
                        }
                    }

                    if (this.destroyAnnotationType != null && method.isAnnotationPresent(this.destroyAnnotationType)) {
                        currDestroyMethods.add(new InitDestroyAnnotationBeanPostProcessor.LifecycleElement(method));
                        if (this.logger.isTraceEnabled()) {
                            this.logger.trace("Found destroy method on class [" + clazz.getName() + "]: " + method);
                        }
                    }

                });
                initMethods.addAll(0, currInitMethods);
                destroyMethods.addAll(currDestroyMethods);
                targetClass = targetClass.getSuperclass();
            } while(targetClass != null && targetClass != Object.class);

            return initMethods.isEmpty() && destroyMethods.isEmpty() ? this.emptyLifecycleMetadata : new InitDestroyAnnotationBeanPostProcessor.LifecycleMetadata(clazz, initMethods, destroyMethods);
        }
    }

~~~


</span>