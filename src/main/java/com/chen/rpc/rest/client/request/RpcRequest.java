package com.chen.rpc.rest.client.request;

/**
 * @author chen
 *         2017/11/13 21:34
 */
public interface RpcRequest {
    /**
     * 抽象远程请求动作
     * @return 远程请求返回的内容
     * @throws Exception
     */
    String execute() throws Exception;
}