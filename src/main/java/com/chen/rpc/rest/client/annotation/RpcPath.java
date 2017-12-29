package com.chen.rpc.rest.client.annotation;

import java.lang.annotation.*;

/**
 * @author chen
 * 2017/11/10 17:22
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RpcPath {
    /**
     * 请求接口的根路径名
     * @return
     */
    String value();
}