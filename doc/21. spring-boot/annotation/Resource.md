<font face="Simsun" size=3>

- java定义
~~~
org.springframework.context.annotation.CommonAnnotationBeanPostProcessor

postProcessProperties(

element.inject(target, beanName, pvs);

        org.springframework.beans.factory.annotation.InjectionMetadata.InjectedElement.inject
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

        getResourceToInject() 有多个实现方法
        
        
~~~


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

</font>