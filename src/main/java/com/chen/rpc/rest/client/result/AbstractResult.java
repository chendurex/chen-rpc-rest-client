package com.chen.rpc.rest.client.result;

/**
 * @author chen
 *         2017/11/13 18:49
 */
public abstract class AbstractResult<T> implements RpcResult {
    private T value;

    public AbstractResult(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
