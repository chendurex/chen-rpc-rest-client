package com.chen.rpc.rest.client.utl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chen.rpc.rest.client.request.RequestWrapper;
import com.chen.rpc.rest.client.annotation.HttpRequestMethod;
import com.chen.rpc.rest.client.request.RpcGetRequest;
import com.chen.rpc.rest.client.request.RpcPostRequest;
import com.chen.rpc.rest.client.request.RpcRequest;
import com.chen.rpc.rest.client.result.RpcResult;
import org.springframework.util.ClassUtils;

import java.beans.Introspector;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author chen
 *         2017/11/10 16:38
 */
public final class Util {
    public static <T> T parser(String json, Type type) {
        return JSON.parseObject(json, type);
    }

    public static <T>T objectToMap(Object json, Type type) {
        return JSON.parseObject(JSON.toJSONString(json), type);
    }

    public static List<Map<String, Object>> listObjectToMap(Object json) {
        final Type type = new TypeReference<List<Map<String, Object>>>() {}.getType();
        return objectToMap(json, type);
    }


    /**
     * 驼峰式的字段转换为下划线类型字段
     * @param field
     * @return
     */
    public static String javaFieldToTableField(String field) {
        char [] chars = field.toCharArray();
        StringBuilder sb = new StringBuilder(chars.length);
        for (int i = 0, len = chars.length; i < len; i++) {
            char c = chars[i];
            if (i == 0 && Character.isUpperCase(c)) {
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(Character.isUpperCase(c) ? "_" + Character.toLowerCase(c) : c);
            }
        }
        return sb.toString();
    }

    public static String generatorBeanName(Class<?> clz) {
        String shortClassName = ClassUtils.getShortName(clz.getSimpleName());
        return "rpcRestClient"+Introspector.decapitalize(shortClassName);
    }

    public static  <T extends RpcResult> T createBean(Class<?> clz) {
        try {
            //noinspection unchecked
            return (T)clz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new IllegalStateException("创建对象失败，当前错误信息为：", e);
        }
    }

    public static RpcRequest getRpcRequest(RequestWrapper requestWrapper) {
        RpcRequest rpcRequest = null;
        switch (requestWrapper.getRequestMethod()) {
            case HttpRequestMethod.GET :
                rpcRequest = new RpcGetRequest(requestWrapper.getUrl(), requestWrapper.getParams());
                break;
            case HttpRequestMethod.POST :
                rpcRequest = new RpcPostRequest(requestWrapper.getUrl(), requestWrapper.getParams());
                break;
            default: break;

        }
        return rpcRequest;
    }
}
