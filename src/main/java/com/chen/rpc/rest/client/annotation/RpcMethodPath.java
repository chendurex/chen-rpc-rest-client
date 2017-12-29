package com.chen.rpc.rest.client.annotation;

import java.lang.annotation.*;

/**
 * @author chen
 *         2017/11/10 17:22
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RpcMethodPath {
    /**
     * 请求接口的二级路径名
     * @return
     */
    String value();

    /**
     * 请求方法类型，默认是GET
     * @return
     */
    int method() default HttpRequestMethod.GET;
}