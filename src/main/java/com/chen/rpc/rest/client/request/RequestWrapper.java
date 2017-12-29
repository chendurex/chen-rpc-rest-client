package com.chen.rpc.rest.client.request;

import com.chen.rpc.rest.client.annotation.RpcMethodPath;
import com.chen.rpc.rest.client.result.RpcResult;

import java.lang.reflect.Method;

/**
 * @author chen
 *         2017/11/14 9:48
 */
public class RequestWrapper {
    private int requestMethod;
    private Object[] params;
    private String url;

    public RequestWrapper(Method method, Object[] params, String url) {
        //valid(method);
        this.requestMethod = getRequestMethod(method);
        this.url = url + getMethodPath(method);
        this.params = params;
    }

    private void valid(Method method) {
        if (!method.getReturnType().isAssignableFrom(RpcResult.class)) {
            throw new IllegalArgumentException("返回类型必须继承" + RpcResult.class);
        }
    }

    private String getMethodPath(Method method) {
        return getAnnotation(method).value();
    }

    private int getRequestMethod(Method method) {
        return getAnnotation(method).method();
    }

    private RpcMethodPath getAnnotation(Method method) {
        RpcMethodPath annotation = method.getAnnotation(RpcMethodPath.class);
        if (annotation == null) {
            throw new IllegalArgumentException("请在:"+ method.getName() +"方法添加["+ RpcMethodPath.class.toString() +"注解]");
        }
        return annotation;
    }

    public int getRequestMethod() {
        return requestMethod;
    }

    public Object[] getParams() {
        return params;
    }

    public String getUrl() {
        return url;
    }
}
