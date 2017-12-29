package com.chen.rpc.rest.client.invoke;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author chen
 * 2017/11/10 16:04
 */
public class RemoteInvoker implements InvocationHandler {
    private final Invocation invocation = new DefaultInvocation();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return invocation.invoke(method, args);
    }
}