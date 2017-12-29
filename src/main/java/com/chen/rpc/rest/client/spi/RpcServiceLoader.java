package com.chen.rpc.rest.client.spi;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author chen
 *         2017/11/13 15:07
 */
public class RpcServiceLoader {
    private static String host;
    private static Map<String, Method> urlMapping;
    private static Set<String> fieldMapping;
    public static String getHost() {
        if (host == null) {
            host = getFromSpi().host();
        }
        return host;
    }

    public static String getBasePackage() {
        return getFromSpi().basePackage();
    }

    public static Map<String, Method> getUrlMapping() {
        if (urlMapping == null) {
            urlMapping = Collections.unmodifiableMap(getFromSpi().getUrlMapping());
        }
        return urlMapping;
    }

    public static Set<String> getFieldMapping() {
        if (fieldMapping == null) {
            fieldMapping = Collections.unmodifiableSet(getFromSpi().getFieldMapping());
        }
        return fieldMapping;
    }

    private static RpcConfigProvider getFromSpi() {
        ServiceLoader<RpcConfigProvider> loaders = ServiceLoader.load(RpcConfigProvider.class);
        Iterator<RpcConfigProvider> iterator = loaders.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        throw new IllegalArgumentException("请实现:["+ RpcServiceLoader.class + "]，提供基本的域名和service扫描路径");
    }
}
