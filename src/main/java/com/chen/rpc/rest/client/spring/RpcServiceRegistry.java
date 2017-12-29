package com.chen.rpc.rest.client.spring;

import com.chen.rpc.rest.client.spi.RpcServiceLoader;
import com.chen.rpc.rest.client.utl.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author chen
 *         2017/11/11 11:36
 */
@Component
public class RpcServiceRegistry implements BeanDefinitionRegistryPostProcessor {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        long startTime = System.currentTimeMillis();
        Collection<Class<?>> classes = new ComponentClassScanner().getComponentClasses(RpcServiceLoader.getBasePackage());
        log.info("扫描客户端远程调用代码一共耗费：{}毫秒", (System.currentTimeMillis()-startTime));
        for (Class<?> clz : classes) {
            GenericBeanDefinition definition = new GenericBeanDefinition();
            definition.setAutowireCandidate(true);
            definition.setBeanClass(RpcClientFactoryBean.class);
            definition.getConstructorArgumentValues().addGenericArgumentValue(clz);
            registry.registerBeanDefinition(Util.generatorBeanName(clz), definition);
        }
        log.debug("当前一共扫描了{}个远程接口，分别是：{}，请检查是否有遗漏", classes.size(), Arrays.toString(classes.toArray()));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
