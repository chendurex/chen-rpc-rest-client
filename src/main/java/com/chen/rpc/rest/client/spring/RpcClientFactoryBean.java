package com.chen.rpc.rest.client.spring;

import com.chen.rpc.rest.client.invoke.RemoteInvoker;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * @author chen
 *         2017/11/11 11:44
 */
public class RpcClientFactoryBean<T> implements FactoryBean<T> {
    private Class<T> serviceInterface;

    public RpcClientFactoryBean(Class<T> clz) {
        Objects.requireNonNull(clz);
        this.serviceInterface = clz;
    }
    @Override
    @SuppressWarnings("unchecked")
    public T getObject() throws Exception {
        return (T)Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{serviceInterface}, new RemoteInvoker());
    }

    @Override
    public Class<?> getObjectType() {
        return serviceInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
