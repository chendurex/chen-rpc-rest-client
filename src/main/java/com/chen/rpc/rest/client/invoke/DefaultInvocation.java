package com.chen.rpc.rest.client.invoke;

import com.alibaba.fastjson.JSONObject;
import com.chen.rpc.rest.client.annotation.RpcPath;
import com.chen.rpc.rest.client.request.RequestWrapper;
import com.chen.rpc.rest.client.result.RpcResult;
import com.chen.rpc.rest.client.spi.RpcServiceLoader;
import com.chen.rpc.rest.client.utl.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chen
 *         2017/11/22 19:20
 */
public class DefaultInvocation implements Invocation {
    private static final int RETRY_LIMIT = 3;
    private static final int MAX_REQUEST_TIME = 1000;
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Map<String, String> URL_MAPPING = new HashMap<>(16);
    @Override
    public Object invoke(Method method, Object []args) {
        long startTime = System.currentTimeMillis();
        final String logMethodName = method.getDeclaringClass().getSimpleName() + "." + method.getName();
        log.info("远程请求接口开始，方法名为：{}，开始时间为：{}，请求参数为：{}", logMethodName,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime), JSONObject.toJSON(args));
        String result = null;
        RpcResult errorResult = null;
        for (int i=0; i<RETRY_LIMIT; i++) {
            try {
                final String name = method.getName();
                String url = URL_MAPPING.get(name);
                if (url == null) {
                    url = RpcServiceLoader.getHost() + method.getDeclaringClass().getAnnotation(RpcPath.class).value();
                    URL_MAPPING.put(name, url);
                }
                result = Util.getRpcRequest(new RequestWrapper(method, args, url)).execute();
                break;
            } catch (Exception e) {
                if (errorResult == null) {
                    errorResult = Util.createBean(method.getReturnType());
                    errorResult.setResult(RpcResult.EXCEPTION);
                    errorResult.setResultDesc("远程接口请求失败");
                    errorResult.setException(e);
                }
                if (!(e instanceof ConnectException)) {
                    break;
                }
            }
        }
        if (result == null) {
            assert errorResult != null;
            if (errorResult.getException() instanceof ConnectException) {
                log.error("远程接口重试了三次还是请求失败，当前错误信息为：", errorResult.getException());
            } else {
                log.error("远程接口请求失败，当前错误信息为：", errorResult.getException());
            }
        }
        long elapsed = System.currentTimeMillis() - startTime;
        if (elapsed > MAX_REQUEST_TIME) {
            log.warn("远程请求接口结束，方法名为：{}，消耗时间为：{}", logMethodName, elapsed);
        } else {
            log.info("远程请求接口结束，方法名为：{}，消耗时间为：{}", logMethodName, elapsed);
        }
        return result == null ? errorResult : Util.parser(result, method.getGenericReturnType());
    }
}
