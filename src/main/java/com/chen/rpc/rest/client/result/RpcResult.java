package com.chen.rpc.rest.client.result;

/**
 * 所有的返回的数据都要实现当前接口，用于处理业务异常或者网络异常时返回的结果
 * 返回值：1，表示接口请求成功
 * 返回值：-1，则表达的是业务异常，需要业务方说明失败的原因
 * 返回值：-2，表示网络出现异常，或者是提供者未返回数据超时导致，请通过getException查看详细信息
 * 返回值：0，表示请求正在处理中
 * @author chen
 * date 2017/11/10 21:17
 */
public interface RpcResult {
    /**
     * 成功
     */
    int SUCCESS = 1;
    /**
     * 失败
     */
    int FAIL = -1;
    /**
     * 网络异常
     */
    int EXCEPTION = -2;
    /**
     * 正在处理中
     */
    int PROCESS = 0;

    int getResult();

    String getResultDesc();

    Throwable getException();

    void setResult(int result);

    void setResultDesc(String resultDesc);

    void setException(Throwable throwable);
}
