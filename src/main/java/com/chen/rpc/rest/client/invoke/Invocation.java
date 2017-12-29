package com.chen.rpc.rest.client.invoke;

import java.lang.reflect.Method;

/**
 * @author chen
 *         2017/11/22 17:51
 */
public interface Invocation {
    /**
     * 真正发送远程请求的接口
     * @param method
     * @param args
     * @return
     */
    Object invoke(Method method, Object []args);
}
