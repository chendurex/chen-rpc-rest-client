package com.chen.rpc.rest.client.spi;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * @author chen
 *         2017/11/13 15:00
 */
public interface RpcConfigProvider {
    /**
     * 定义域名
     * @return
     */
    String host();

    /**
     * 定义扫描的基类包路径
     * @return
     */
    String basePackage();

    /**
     * 字段前缀与请求方法映射
     * 在进行数据翻译时，会调用定义好的接口，获取响应的数据
     * @return
    */
    Map<String, Method> getUrlMapping();

    /**
     * 消费方使用的字段
     * 按照约定，消费方使用的字段规格为：prefix+fieldName
     * 如：提供方提供的字段为name，这个字段属于person表，那么提供给消费方的字段为：person_name
     * @return
     */
    Set<String> getFieldMapping();
}
