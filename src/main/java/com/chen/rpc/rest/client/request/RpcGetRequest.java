package com.chen.rpc.rest.client.request;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * @author chen
 *         2017/11/13 21:32
 */
public class RpcGetRequest extends AbstractRequest {
    private final String url;
    public RpcGetRequest(String url, Object []args) {
        this.url = url + paramTransferUrl(args);
    }

    @Override
    protected HttpRequestBase createRequest() {
        return new HttpGet(url);
    }

    public static String paramTransferUrl(Object[] args) {
        if (args != null && args.length !=0) {
            StringBuilder sb = new StringBuilder(args.length * 10);
            for (Object obj : args) {
                validLength(obj);
                sb.append("/").append(obj);
            }
            return sb.toString();
        }
        return "";
    }

    private static void validLength(Object value) {
        if (value != null) {
            if (value.toString().length() > 1000) {
                throw new IllegalStateException("参数内容过长，请考虑缩减或者使用post请求");
            }
        }
    }
}