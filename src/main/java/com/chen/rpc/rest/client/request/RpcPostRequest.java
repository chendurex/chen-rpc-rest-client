package com.chen.rpc.rest.client.request;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;

import java.io.IOException;

/**
 * @author chen
 *         2017/11/13 21:32
 */
public class RpcPostRequest extends AbstractRequest {
    private final String value;
    private final String url;
    public RpcPostRequest(String url, Object[] params) {
        this.url = url;
        this.value = formatValue(params);
    }

    private String formatValue(Object[] params) {
        if (params == null || params.length != 1) {
            throw new IllegalArgumentException("传入参数有误，POST请求只能接收一个对象");
        }
        return JSON.toJSONString(params[0]);
    }

    @Override
    protected HttpRequestBase createRequest() throws IOException {
        HttpPost post = new HttpPost(url);
        byte[] requestStringBytes = value.getBytes("UTF-8");
        post.setEntity(new ByteArrayEntity(requestStringBytes, 0, requestStringBytes.length));
        return post;
    }
}
