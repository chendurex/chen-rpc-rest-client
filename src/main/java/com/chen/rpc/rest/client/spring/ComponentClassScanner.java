package com.chen.rpc.rest.client.spring;

import com.chen.rpc.rest.client.annotation.RpcPath;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author chen
 * 2017/11/11 12:37
 * @see {@link org.springframework.context.annotation.ClassPathBeanDefinitionScanner}
 */
public class ComponentClassScanner extends ClassPathScanningCandidateComponentProvider {

    public ComponentClassScanner() {
        super(false);
        addIncludeFilter(new AnnotationTypeFilter(RpcPath.class, true, true));
    }

    @SuppressWarnings("unchecked")
    public final Collection<Class<?>> getComponentClasses(String basePackage) {
        if (basePackage == null) {
            throw new IllegalArgumentException("请传入扫描包地址");
        }
        List<Class<?>> classes = new ArrayList<>();
        for (BeanDefinition candidate : findCandidateComponents(basePackage)) {
            try {
                Class cls = ClassUtils.resolveClassName(candidate.getBeanClassName(), ClassUtils.getDefaultClassLoader());
                classes.add( cls);
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
        return classes;
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return true;
    }
}
