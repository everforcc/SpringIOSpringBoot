<span  style="font-family: Simsun,serif; font-size: 17px; ">

- [原文](https://cloud.tencent.com/developer/article/1520392)

- java定义

CommonAnnotationBeanPostProcessor是Spring中用于处理JavaEE5中常用注解(主要是EJB相关的注解)和Java6中关于JAX-WS相关的注解，可以处理@PostConstruct、@PreDestroy等Bean生命周期相关事件的注解，该后置处理最核心的是处理@Resource注解，同时还可以处理JAX-WS相关的注解。


## 1. 触发方式

1. Spring容器在每个Bean实例化之后，调用后置处理器CommonAnnotationBeanPostProcessor的postProcessMergedBeanDefinition方法，查找该Bean是否有@Resource注解。

源码分析
~~~
1. 
    @Override
	public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
		super.postProcessMergedBeanDefinition(beanDefinition, beanType, beanName);
		InjectionMetadata metadata = findResourceMetadata(beanName, beanType, null);
		metadata.checkConfigMembers(beanDefinition);
	}
2. findResourceMetadata

org.springframework.context.annotation.CommonAnnotationBeanPostProcessor.findResourceMetadata
    private InjectionMetadata findResourceMetadata(String beanName, final Class<?> clazz, @Nullable PropertyValues pvs) {
		// Fall back to class name as cache key, for backwards compatibility with custom callers.
		String cacheKey = (StringUtils.hasLength(beanName) ? beanName : clazz.getName());
		// Quick check on the concurrent map first, with minimal locking.
		InjectionMetadata metadata = this.injectionMetadataCache.get(cacheKey);
		if (InjectionMetadata.needsRefresh(metadata, clazz)) {
			synchronized (this.injectionMetadataCache) {
				metadata = this.injectionMetadataCache.get(cacheKey);
				if (InjectionMetadata.needsRefresh(metadata, clazz)) {
					if (metadata != null) {
						metadata.clear(pvs);
					}
					metadata = buildResourceMetadata(clazz);
					this.injectionMetadataCache.put(cacheKey, metadata);
				}
			}
		}
		return metadata;
	}
3. buildResourceMetadata
3.0 取出含有注解 @Resource 的变量，方法
3.1 校验不能是 static 变量
3.1 校验不能是 static 方法

判断依据
java.lang.reflect.Modifier ## 参考api，已总结

~~~



2. Spring在每个Bean实例化的时候，调用populateBean进行属性注入的时候，即调用postProcessPropertyValues方法，查找该Bean是否有@Resource注解。

同上


## 2. 构造函数

~~~
    /**
	 * Create a new CommonAnnotationBeanPostProcessor,
	 * with the init and destroy annotation types set to
	 * {@link javax.annotation.PostConstruct} and {@link javax.annotation.PreDestroy},
	 * respectively.
	 */
	public CommonAnnotationBeanPostProcessor() {
		setOrder(Ordered.LOWEST_PRECEDENCE - 3);
		// 设置 初始类型为 PostConstruct
		setInitAnnotationType(PostConstruct.class);
		// 销毁注解为 PreDestroy
		setDestroyAnnotationType(PreDestroy.class);
		// 忽略
		ignoreResourceType("javax.xml.ws.WebServiceContext");
	}
~~~

## 3。 注入

~~~
// 往下翻代码    
    @Deprecated
	@Override
	public PropertyValues postProcessPropertyValues(
			PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) {

		return postProcessProperties(pvs, bean, beanName);
	}
// 最后会到
    protected void inject(Object target, @Nullable String requestingBeanName, @Nullable PropertyValues pvs)
				throws Throwable {

			if (this.isField) {
				Field field = (Field) this.member;
				ReflectionUtils.makeAccessible(field);
				field.set(target, getResourceToInject(target, requestingBeanName));
			}
			else {
				if (checkPropertySkipping(pvs)) {
					return;
				}
				try {
					Method method = (Method) this.member;
					ReflectionUtils.makeAccessible(method);
					method.invoke(target, getResourceToInject(target, requestingBeanName));
				}
				catch (InvocationTargetException ex) {
					throw ex.getTargetException();
				}
			}
		}
// 获取 Resource的值

getResourceToInject
我们要的是 Resource 所以选择实现类
private class ResourceElement extends LookupElement {

        @Override
		protected Object getResourceToInject(Object target, @Nullable String requestingBeanName) {
			return (this.lazyLookup ? buildLazyResourceProxy(this, requestingBeanName) :
					getResource(this, requestingBeanName));
		}
		
		lazyLookup默认false，表示是否懒加载
		
//CommonAnnotationBeanPostProcessor.java

    //根据给定名称或者类型获取资源对象
	protected Object getResource(LookupElement element, @Nullable String requestingBeanName) throws BeansException {
	//如果注解对象元素的mappedName属性不为空
	if (StringUtils.hasLength(element.mappedName)) {
		//根据JNDI名称和类型去Spring的JNDI容器中获取Bean
		return this.jndiFactory.getBean(element.mappedName, element.lookupType);
	}
	//如果该后置处理器的alwaysUseJndiLookup属性值为true
	if (this.alwaysUseJndiLookup) {
		//从Spring的JNDI容器中查找指定JDNI名称和类型的Bean
		return this.jndiFactory.getBean(element.name, element.lookupType);
	}
	if (this.resourceFactory == null) {
		throw new NoSuchBeanDefinitionException(element.lookupType,
				"No resource factory configured - specify the 'resourceFactory' property");
	}
	//使用autowiring自动依赖注入装配，通过给定的名称和类型从资源容器获取Bean对象
	//一般情况下，都是走这一步
	return autowireResource(this.resourceFactory, element, requestingBeanName);
	}	
	
~~~

- autowireResource代码：
~~~
	
	
	
	//CommonAnnotationBeanPostProcessor.java

    protected Object autowireResource(BeanFactory factory, LookupElement element, @Nullable String requestingBeanName)
		throws BeansException {

	Object resource;
	Set<String> autowiredBeanNames;
	String name = element.name;

	if (this.fallbackToDefaultTypeMatch && element.isDefaultName &&
			factory instanceof AutowireCapableBeanFactory && !factory.containsBean(name)) {
		autowiredBeanNames = new LinkedHashSet<>();
		//根据类型从Spring容器中查找资源
		//调用依赖解析器，跟@Autowired是同样的代码
		resource = ((AutowireCapableBeanFactory) factory).resolveDependency(
				element.getDependencyDescriptor(), requestingBeanName, autowiredBeanNames, null);
		if (resource == null) {
			throw new NoSuchBeanDefinitionException(element.getLookupType(), "No resolvable resource object");
		}
	}
	//根据名称从Spring容器中查找资源
	else {
		resource = factory.getBean(name, element.lookupType);
		autowiredBeanNames = Collections.singleton(name);
	}

	//注册Bean的依赖关系
	if (factory instanceof ConfigurableBeanFactory) {
		ConfigurableBeanFactory beanFactory = (ConfigurableBeanFactory) factory;
		for (String autowiredBeanName : autowiredBeanNames) {
			if (requestingBeanName != null && beanFactory.containsBean(autowiredBeanName)) {
				beanFactory.registerDependentBean(autowiredBeanName, requestingBeanName);
			}
		}
	}

	return resource;
	}	

~~~
- 这里的逻辑比较简单：
- 首先判断@Resource是按名称来查询还是类型，如果是类型，则调用依赖解析器根据类型从Spring容器中查找
- 如果是按名称，则直接调用BeanFactory的getBean()方法，根据BeanName从Spring容器中查找
- 最后由于发生了依赖注入，需要从新注册Bean的依赖关系
- 总结
- @Resource注解既可以按照名称来注入，也可以按类型来注入。


---

~~~
@Target({TYPE, FIELD, METHOD})
@Retention(RUNTIME)
public @interface Resource {
    /**
     * The JNDI name of the resource.  For field annotations,
     * the default is the field name.  For method annotations,
     * the default is the JavaBeans property name corresponding
     * to the method.  For class annotations, there is no default
     * and this must be specified.
     */
    String name() default "";

    /**
     * The name of the resource that the reference points to. It can
     * link to any compatible resource using the global JNDI names.
     *
     * @since Common Annotations 1.1
     */

    String lookup() default "";

    /**
     * The Java type of the resource.  For field annotations,
     * the default is the type of the field.  For method annotations,
     * the default is the type of the JavaBeans property.
     * For class annotations, there is no default and this must be
     * specified.
     */
    Class<?> type() default java.lang.Object.class;
~~~

## 1. 作用范围

### 1.1 字段

### 1.2 方法

### 1.3 类

</span>